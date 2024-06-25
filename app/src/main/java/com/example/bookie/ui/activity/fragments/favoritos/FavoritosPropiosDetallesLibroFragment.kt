package com.example.bookie.ui.activity.fragments.favoritos

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Looper
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.bookie.R
import com.example.bookie.databinding.FragmentFavoritosdetallesBinding
import com.example.bookie.ui.activity.viewsmodels.ChatViewModel
import com.example.bookie.ui.activity.viewsmodels.LibrosViewModel
import com.example.bookie.ui.activity.viewsmodels.perfilViewModel
import kotlinx.coroutines.launch

/**
 * Clase para mostrar los libros favoritos del usuario propio, borrar el favorito y pedir un intercambio
 *@author Lorena Villar
 * @version 13/5/2024
 * @see LibrosViewModel
 * @see ChatViewModel
 * @see FragmentFavoritosdetallesBinding
 * @see perfilViewModel
 * */

class FavoritosPropiosDetallesLibroFragment: Fragment() {

    private val librosV by activityViewModels<LibrosViewModel> {
        LibrosViewModel.MyViewModelFactory(requireContext())
    }
    private val myViewModelChat: ChatViewModel by activityViewModels{
        ChatViewModel.MyViewModelFactory(requireContext())
    }

    private val perfilViewModel: perfilViewModel by activityViewModels()
    private lateinit var binding: FragmentFavoritosdetallesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritosdetallesBinding.inflate(inflater, container, false)
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

            val decodedBitmap = favorito.imagen?.let {base64ToBitmap(it)}
            binding.imgVisto.setImageBitmap(decodedBitmap)

            binding.btnMg.setOnClickListener {
                showFavoritoBorradoDialog()
                favorito.idFavorito?.let { it1 -> perfilViewModel.deletefavorito(it1) }
                findNavController().navigate(R.id.action_favoritosPropiosDetallesLibroFragment_to_usuarioFragment)
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
                            findNavController().navigate(R.id.action_usuarioFragment_to_contactosFragment)
                        }, 1000)
                    }
                }

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

    private fun showFavoritoBorradoDialog(): AlertDialog {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.alert_favorito_eliminado, null)
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