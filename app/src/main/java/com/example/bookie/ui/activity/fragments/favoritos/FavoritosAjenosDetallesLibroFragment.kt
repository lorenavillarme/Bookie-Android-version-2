package com.example.bookie.ui.activity.fragments.favoritos

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
import com.example.bookie.R
import com.example.bookie.data.model.favoritos.Libro
import com.example.bookie.data.model.favoritos.Usuario
import com.example.bookie.databinding.FragmentFavoritosdetallesAjenosBinding
import com.example.bookie.ui.activity.viewsmodels.ChatViewModel
import com.example.bookie.ui.activity.viewsmodels.LibrosViewModel
import com.example.bookie.ui.activity.viewsmodels.perfilViewModel
import kotlinx.coroutines.launch

/**
 * Clase para mostrar los libros favoritos del usuario ajeno, agregar el favorito y pedir un intercambio
 *@author Lorena Villar
 * @version 13/5/2024
 * @see LibrosViewModel
 * @see ChatViewModel
 * @see Libro
 * @see Usuario
 * @see FragmentFavoritosdetallesAjenosBinding
 * @see perfilViewModel
 * */
class FavoritosAjenosDetallesLibroFragment: Fragment() {

    private val librosV by activityViewModels<LibrosViewModel> {
        LibrosViewModel.MyViewModelFactory(requireContext())
    }
    private val myViewModelChat: ChatViewModel by activityViewModels{
        ChatViewModel.MyViewModelFactory(requireContext())
    }

    private val perfilViewModel: perfilViewModel by activityViewModels()
    private lateinit var binding: FragmentFavoritosdetallesAjenosBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritosdetallesAjenosBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //conseguir datos de selecciona do favorito
        perfilViewModel.liveDataCrearFavorito.observe(viewLifecycleOwner) { favorito ->
            favorito.libroId?.let { librosV.getLibroAjeno(it.toLong()) }
            binding.titulo.text = favorito.titulo
            binding.autor.text = favorito.autor
            binding.nPaginas.text = favorito.numeroPaginas.toString()
            binding.genero.text = favorito.genero
            val estado = if (favorito.prestado) "Prestado" else "Disponible"
            binding.disponible.text = estado

            val decodedBitmap = favorito.imagen?.let { base64ToBitmap(it) }
            binding.imgVisto.setImageBitmap(decodedBitmap)


            //favoritos

            binding.btnMg.setOnClickListener {


                val imagen = favorito.imagen
                val titulo = favorito.titulo
                val autor = favorito.autor
                val nPag = favorito.numeroPaginas
                val genero = favorito.genero
                val sinopsis = favorito.sinopsis
                val editorial = favorito.editorial
                val prestado = favorito.prestado
                val libroid = favorito.libroId
                val idLibroFav = favorito.libroId
                val libro = idLibroFav?.let { it1 -> Libro(it1) }
                myViewModelChat.viewModelScope.launch {
                    val userID = myViewModelChat.postId()
                    Log.e("titulo", titulo)
                    Log.e("imagen", imagen)
                    Log.e("idLibroFav", idLibroFav.toString())
                    Log.e("userID", userID.toString())
                    val usuario = userID?.toDouble()?.let { it1 -> Usuario(it1.toInt()) }
                    if (usuario != null) {
                        if (libro != null) {
                            perfilViewModel.postFavorito(
                                titulo,
                                autor,
                                nPag,
                                genero,
                                sinopsis,
                                editorial,
                                prestado,
                                libroid,
                                imagen,
                                libro,
                                usuario
                            )
                        }
                        showAddFavDialog()
                    }
                }
            }
        }

        //chat
        binding.btnIntercambio.setOnClickListener {
            myViewModelChat.viewModelScope.launch {
                val postId = myViewModelChat.postId()

                val userIdPedido = postId
                val receptorId = librosV.libroSelected.value?.userId
                if (userIdPedido != null) {
                    if (receptorId != null) {
                        myViewModelChat.postChat(
                            userIdPedido.toDouble().toInt(),
                            receptorId.toInt()
                        )


                        val dialogF = showIntercambioDialog()
                        android.os.Handler(Looper.getMainLooper()).postDelayed({
                            dialogF.dismiss()
                            findNavController().navigate(R.id.action_usuarioAjenoFragment_to_contactosFragment)
                        }, 1000)
                    }
                }

            }
        }
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


    private fun base64ToBitmap(base64Str: String): Bitmap {
        val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

}