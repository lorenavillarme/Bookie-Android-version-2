package com.example.bookie.ui.activity.fragments.libro.propio

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.bookie.R
import com.example.bookie.data.model.LibroPrestado
import com.example.bookie.data.model.api.Book
import com.example.bookie.databinding.LibroPropioDetallesBinding
import com.example.bookie.ui.activity.viewsmodels.ChatViewModel
import com.example.bookie.ui.activity.viewsmodels.LibrosViewModel
import com.example.bookie.ui.activity.viewsmodels.SharedViewModel
import kotlinx.coroutines.launch

/**
 * Esta clase define funciones para mostrar datos sobre el libro que se ha pulsado desde UsuarioFragment. Además, sirve para eliminar o modificar el libro.
 *@author Liberty Tamayo
 * @version 20/5/2024
 * @see LibroPropioDetallesBinding
 * @see LibrosViewModel
 * */

class MiLibroDetalleFragment: Fragment() {

    private val librosV by activityViewModels<LibrosViewModel> {
        LibrosViewModel.MyViewModelFactory(requireContext())
    }
    private val myViewModelchat by activityViewModels<ChatViewModel> {
        ChatViewModel.MyViewModelFactory(requireContext())
    }


    private lateinit var binding: LibroPropioDetallesBinding
    lateinit var alertDialog: AlertDialog

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LibroPropioDetallesBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // se observa el libro seleccionado y muestra sus detalles
        librosV.libroSelected.observe(viewLifecycleOwner) { libroSeleccionado ->
            if (libroSeleccionado != null) {
                binding.titulo.text = libroSeleccionado.titulo
                binding.autor.text = libroSeleccionado.autor
                binding.genero.text = libroSeleccionado.genero
                binding.nPaginas.text = libroSeleccionado.numeroPaginas.toString()

                val decodedBitmap = base64ToBitmap(libroSeleccionado.foto)

                libroSeleccionado.foto.let { foto ->
                    Glide.with(requireContext())
                        .load(decodedBitmap)
                        .placeholder(R.drawable.generico)
                        .into(binding.imgVisto)
                }

                if (libroSeleccionado.prestado) {
                    binding.prestadoPregunta.text = getString(R.string.intercambioFinalizado)
                    binding.finalizar.visibility = View.VISIBLE
                    binding.iniciar.visibility = View.GONE
                } else {
                    binding.prestadoPregunta.text = getString(R.string.iniciarIntercambio)
                    binding.finalizar.visibility = View.GONE
                    binding.iniciar.visibility = View.VISIBLE
                }

                // obtener la id del libro seleccionado
                val idLibroInt = libroSeleccionado.libroId
                Log.e("idLibro", idLibroInt.toString())
                val dataIdLibro = idLibroInt.toString()
                sharedViewModel.setData2(dataIdLibro)
            }
            myViewModelchat.viewModelScope.launch {
                val userId = myViewModelchat.postId()
                if (userId != null) {
                    myViewModelchat.getUsuarios(userId.toDouble().toLong()).observe(viewLifecycleOwner) { usuario ->

                        if (usuario != null) {
                            binding.usuario.text = usuario.username
                        }

                        val decodedBitmap = usuario?.foto?.let { base64ToBitmap(it) }

                        if (decodedBitmap != null) {
                            binding.imageView3.setImageBitmap(decodedBitmap)
                        } else {
                            binding.imageView3.setImageResource(R.drawable.generico)
                        }
                    }
                }
            }

        }


        // configuración del botón para eliminar el libro
        binding.btnEliminar.setOnClickListener {
            // si libroId es nulo no se ejecuta deleteBook
            val libroId = librosV.libroSelected.value?.libroId ?: return@setOnClickListener
            eliminarLibroDialog(libroId)

        }


        // configuración del botón para volver atrás
        binding.backD.setOnClickListener {
            findNavController().navigateUp()
        }

        // configuración del botón para finalizar el intercambio
        binding.finalizar.setOnClickListener {
            binding.prestadoPregunta.text = getString(R.string.iniciarIntercambio) // string con pregunta correspondiente
            binding.finalizar.visibility = View.GONE // el botón de finalizar intercambio está en invisible
            binding.iniciar.visibility = View.VISIBLE // el botón de inicializar está visible

            /*
            * En caso de que el libro o id vuelvan en null por cualquier error return@setOnClickListener
            * se ejecuta y detiene la acción del setOnClickListener
            * */
            val idLibroInt = librosV.libroSelected.value?.libroId ?: return@setOnClickListener
            actualizarEstadoPrestamo(idLibroInt, false) // se actualiza el estado del libro
        }

        // configuración del botón para inicializar el intercambio
        binding.iniciar.setOnClickListener {
            binding.prestadoPregunta.text = getString(R.string.intercambioFinalizado) // string con pregunta correspondiente
            binding.finalizar.visibility = View.VISIBLE // el botón de finalizar intercambio está en visible
            binding.iniciar.visibility = View.GONE // el botón de inicializar está invisible

            val idLibroInt = librosV.libroSelected.value?.libroId ?: return@setOnClickListener
            actualizarEstadoPrestamo(idLibroInt, true)
        }

    }

    /**
     * Actualiza el estado de préstamo de un libro.
     *
     * @param id ID del libro.
     * @param prestado Estado de préstamo.
     */
    private fun actualizarEstadoPrestamo(id: Int, prestado: Boolean) {
        val libroPrestado = LibroPrestado(prestado)
        librosV.actualizarLibroPrestado(id, libroPrestado)
    }

    /**
     * Muestra un diálogo de confirmación para eliminar un libro.
     *
     * @param libroId ID del libro a eliminar.
     */
    private fun eliminarLibroDialog(libroId: Int) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog_eliminar_libro, null)
        val builder = context?.let { AlertDialog.Builder(it, R.style.AlertDialogTheme) }

        builder?.setView(dialogView)
        builder?.setTitle("")
        builder?.setMessage("")

        val positiveButton = dialogView.findViewById<ImageButton>(R.id.positiveButton)
        val negativeButton = dialogView.findViewById<ImageButton>(R.id.negativeButton)

        val alertDialog = builder?.create()

        positiveButton.setOnClickListener {
            alertDialog?.dismiss()
            librosV.deleteBook(libroId)

            if (alertDialog != null) {
                alertDialog.dismiss()
            }
            val dialogBorrada = libroEliminado()
            Handler(Looper.getMainLooper()).postDelayed({
                dialogBorrada.dismiss()
                findNavController().navigate(R.id.action_miLibroDetalleFragment_to_usuarioFragment)
            }, 1000)
        }

        negativeButton.setOnClickListener {
            alertDialog?.dismiss()
        }

        alertDialog?.show()
    }


    /**
     * Muestra un diálogo indicando que el libro ha sido eliminado.
     *
     * @return el alert.
     */
    private fun libroEliminado() : AlertDialog {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog_libro_eliminado, null)
        val builder = context?.let { AlertDialog.Builder(it, R.style.AlertDialogThemeSmall) }

        if (builder != null) {
            builder.setView(dialogView)
        }
        if (builder != null) {
            builder.setTitle("")
        }
        if (builder != null) {
            builder.setMessage("")
        }

        if (builder != null) {
            alertDialog = builder.create()
        }
        alertDialog.show()

        return alertDialog
    }


    /**
     * Convierte una cadena Base64 a un objeto Bitmap.
     *
     * @param base64Str La cadena Base64 a convertir.
     * @return El objeto Bitmap resultante.
     */
    private fun base64ToBitmap(base64Str: String): Bitmap {
        val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }
}