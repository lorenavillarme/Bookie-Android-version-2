package com.example.bookie.ui.activity.fragments.libro.ajeno

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.bookie.R
import com.example.bookie.data.model.favoritos.Libro
import com.example.bookie.data.model.favoritos.Usuario
import com.example.bookie.databinding.FragmentDetallesBinding
import com.example.bookie.databinding.LibroPropioDetallesBinding
import com.example.bookie.ui.activity.viewsmodels.ChatViewModel
import com.example.bookie.ui.activity.viewsmodels.LibrosViewModel
import com.example.bookie.ui.activity.viewsmodels.SharedViewModel
import com.example.bookie.ui.activity.viewsmodels.perfilViewModel
import kotlinx.coroutines.launch

/**
 * Esta clase define funciones para mostrar datos sobre el libro que se ha pulsado en ListadoFragment. Además, sirve para iniciar intercambios
 *@author Liberty Tamayo, Lorena Villar
 * @version 23/5/2024
 * @see LibroPropioDetallesBinding
 * @see LibrosViewModel
 * @see LibrosViewModel
 * */

class DetallesLibroFragment: Fragment() {

    private val librosV by activityViewModels<LibrosViewModel> {
        LibrosViewModel.MyViewModelFactory(requireContext())
    }

    private val myViewModelChat: ChatViewModel by activityViewModels{
        ChatViewModel.MyViewModelFactory(requireContext())
    }

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val perfilViewModel: perfilViewModel by activityViewModels()
    private lateinit var binding: FragmentDetallesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetallesBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        librosV.libroSelected.observe(viewLifecycleOwner) { libroSeleccionado ->
            if (libroSeleccionado != null) {
                val estado = if (libroSeleccionado.prestado) "Prestado" else "Disponible"
                val color = if (libroSeleccionado.prestado) R.color.my_primary else R.color.marron
                // Actualiza la interfaz de usuario con los detalles del libro seleccionado
                binding.titulo.text = libroSeleccionado.titulo
                binding.autor.text = libroSeleccionado.autor
                binding.disponible.text = estado
                binding.disponible.setTextColor(resources.getColor(color, null))
                binding.genero.text = libroSeleccionado.genero
                binding.nPaginas.text = libroSeleccionado.numeroPaginas.toString()

                val decodedBitmap = base64ToBitmap(libroSeleccionado.foto)

                libroSeleccionado.foto.let { foto ->
                    Glide.with(requireContext())
                        .load(decodedBitmap)
                        .placeholder(R.drawable.generico)
                        .into(binding.imgVisto)
                }


                //sacar username y foto del dueño del libro
                val idDuenio = libroSeleccionado.userId
                //   myViewModelChat.getUsuarios(idDuenio)
                myViewModelChat.getUsuarios(idDuenio).observe(viewLifecycleOwner){ usuario ->
                    if (usuario != null) {
                        binding.usuario.text = usuario.username
                    }

                    val decodedBitmap2 = usuario?.foto?.let {base64ToBitmap(it)}

                    if (decodedBitmap2 != null){
                        binding.imgUserAjeno.setImageBitmap(decodedBitmap2)
                    }else{
                        binding.imgUserAjeno.setImageResource(R.drawable.generico)
                    }
                }

                //guardar en sharedVM el id del dueño
                sharedViewModel.setDataIdUser(libroSeleccionado.userId.toString())


                val idLibroFav = libroSeleccionado.libroId
                Log.e("idLibroFav", idLibroFav.toString())
                val dataIdLibro = idLibroFav.toString()
                sharedViewModel.setData2(dataIdLibro)

                //resenias
                val idUserPuntuado = libroSeleccionado.userId
                val dataToSend = idUserPuntuado.toString()
                sharedViewModel.setData(dataToSend)

                //favoritos
                binding.btnMg.setOnClickListener {
                    perfilViewModel.viewModelScope.launch {
                        val imagen = libroSeleccionado.foto
                        val titulo = libroSeleccionado.titulo
                        val autor = libroSeleccionado.autor
                        val nPag = libroSeleccionado.numeroPaginas
                        val genero = libroSeleccionado.genero
                        val sinopsis = libroSeleccionado.sinopsis
                        val editorial = libroSeleccionado.editorial
                        val prestado = libroSeleccionado.prestado
                        val libroid = libroSeleccionado.libroId
                        val idLibroFav = libroSeleccionado.libroId
                        val libro = Libro(idLibroFav)

                        val userID = myViewModelChat.postId()
                        val usuario = userID?.toDouble()?.let { it1 -> Usuario(it1.toInt()) }
                        if (usuario != null) {
                            perfilViewModel.postFavorito(
                                titulo, autor, nPag, genero, sinopsis, editorial,
                                prestado, libroid, imagen, libro, usuario
                            )
                            showAddFavDialog()
                        }
                    }
                }
            }


            //chat
            binding.btnIntercambio.setOnClickListener {
                myViewModelChat.viewModelScope.launch {
                    val postId = myViewModelChat.postId() //obtiene el ID del usuario actual

                    val userIdPedido = postId
                    val receptorId = librosV.libroSelected.value?.userId //obtiene el ID del receptor del libro seleccionado
                    if (userIdPedido != null) {
                        if (receptorId != null) {
                            myViewModelChat.postChat(
                                userIdPedido.toDouble().toInt(),
                                receptorId.toInt()
                            )

                            val dialogF = showIntercambioDialog() //alert de intercambio
                            android.os.Handler(Looper.getMainLooper()).postDelayed({
                                dialogF.dismiss() // cierra el alert después de esperar un sec
                                findNavController().navigate(R.id.action_detallesLibroFragment_to_contactosFragment) //va al fragmento de contactos
                            }, 1000)
                        }
                    }
                }
            }



            binding.backL.setOnClickListener {
                findNavController().navigateUp()
            }


            binding.usuario.setOnClickListener {
                findNavController().navigate(R.id.action_detallesLibroFragment_to_usuarioAjenoFragment)
            }

            binding.imgUserAjeno.setOnClickListener {
                findNavController().navigate(R.id.action_detallesLibroFragment_to_usuarioAjenoFragment)
            }

        }

    }

    private fun showIntercambioDialog(): AlertDialog {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.alert_intercambio_iniciado, null)
        val builder = AlertDialog.Builder(requireContext(), R.style.AlertDialogThemeSmall)

        builder.setView(dialogView)
        builder.setTitle("")
        builder.setMessage("")

        val dialog = builder.create()
        dialog.show()
        return dialog
    }

    private fun showAddFavDialog(): AlertDialog {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.custom_alert_dialog_favorito_agregado, null)
        val builder = AlertDialog.Builder(requireContext(), R.style.AlertDialogThemeSmall)

        builder.setView(dialogView)
        builder.setTitle("")
        builder.setMessage("")

        val dialog = builder.create()
        dialog.show()
        return dialog
    }


    private fun base64ToBitmap(base64Str: String): Bitmap {
        val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

}