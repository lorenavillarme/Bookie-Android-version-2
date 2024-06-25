package com.example.bookie.ui.activity.fragments.libro

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import kotlinx.coroutines.*
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.bookie.R
import com.example.bookie.data.repository.Repositorio
import com.example.bookie.databinding.FiltroListadoGalletasBinding
import com.example.bookie.databinding.LibroPropioDetallesBinding
import com.example.bookie.databinding.SubirLibroBinding
import com.example.bookie.ui.activity.viewsmodels.LibrosViewModel
import java.io.ByteArrayOutputStream

/**
 * Esta clase define funciones para guardar datos en la base de datos
 *@author Liberty Tamayo
 * @version 8/5/2024
 * @see SubirLibroBinding
 * @see Repositorio
 * @see LibrosViewModel
 * */

class SubirLibroFragment : Fragment()  {



    private lateinit var binding: SubirLibroBinding
    private lateinit var repository: Repositorio
    private val myViewModel: LibrosViewModel by activityViewModels { LibrosViewModel.MyViewModelFactory(requireContext()) }
    private var selectedImageUri: Uri? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SubirLibroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = Repositorio(requireContext())

        // configuración del spinner para seleccionar el género del libro
        val spinnerGenero: Spinner = binding.spinner
        ArrayAdapter.createFromResource(
            requireContext(),
            // array de strings creada para mostrar todos los géneros
            R.array.generos_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerGenero.adapter = adapter
        }

        // listener para seleccionar una imagen desde la galería
        binding.imgLibro.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)

        }

        // listener para subir el libro
        binding.subirLibro.setOnClickListener {

            val titulo = binding.tituloSubir.text.toString()
            val autor = binding.autorSubir.text.toString()
            val genero = spinnerGenero.selectedItem.toString()
            val paginas = binding.paginasSubir.text.toString().toInt()

            // conversión de la imagen seleccionada a base64
            val imagenBase64 = selectedImageUri?.let { uri -> // si selectedImageUri es null el código no se ejecuta
                convertirImagenABase64(uri)
            } ?: run {
                Toast.makeText(requireContext(), "No se seleccionó ninguna imagen", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
                /*
                * return@setOnClickListener es un labeled return que indica específicamente que se debe salir del bloque de código del setOnClickListener.
                * No afecta al método que contiene el setOnClickListener, solo finaliza la ejecución de este bloque de lambda.
                * */
            }

            // donde se realiza la función de subir libro
            myViewModel.viewModelScope.launch {
                // se obtiene la id del usuario, se pasa a double y posteriormente a int
                val postId = myViewModel.postId()
                val idUser = postId?.toDouble()?.toInt()
                if (postId != null) {


                    myViewModel.subirLibro(titulo,autor,paginas,genero,imagenBase64,"","",false,idUser)
                    findNavController().navigate(R.id.action_subirLibroFragment_to_usuarioFragment)
                } else {
                    Toast.makeText(requireContext(), "Error obteniendo la ID", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // para volver al fragment anterior
        binding.backSub.setOnClickListener {
            findNavController().navigateUp()
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            binding.imgLibro.setImageURI(selectedImageUri)
        }
    }
    private fun convertirImagenABase64(uri: Uri): String {
        /*
        * se usa contentResolver para abrir un flujo de entrara para el URI proporcionado. Esto
        * permite leer los datos de la URI
        * */
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val bytes = ByteArrayOutputStream() // objeto que se usa para almacenar los bytes de la imagen
        inputStream?.use { input ->
            input.copyTo(bytes) // se copian los datos de InputStream al ByteArrayOutputStream
        }
        return android.util.Base64.encodeToString(bytes.toByteArray(), android.util.Base64.DEFAULT)
        /*
        * Se convierte el condtenido de ByteArrayOutputStream a un array de bytes
        * Se codifica el array de bytes a una cadena en formato base64
        * Se usa el modo de codificación predeterminado de base64
        * Finalmente se devuelve en formato base64
        * */
    }

    // objeto companion (singleton) que contiene una constante para identificar la solicitud de imagen
    companion object {
        const val PICK_IMAGE_REQUEST = 1
    }


}
