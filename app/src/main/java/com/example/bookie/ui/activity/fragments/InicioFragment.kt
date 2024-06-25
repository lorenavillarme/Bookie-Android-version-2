package com.example.bookie.ui.activity.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.bookie.R
import com.example.bookie.databinding.FragmentMisLibrosBinding
import com.example.bookie.databinding.InicioBinding
import com.example.bookie.ui.activity.viewsmodels.LibrosViewModel
import com.example.bookie.ui.activity.viewsmodels.perfilViewModel
import kotlinx.coroutines.launch

/**
 * Esta clase define funciones para mostrar los diversos géneros de libros para posteriormente navegar a una de ellas y ver qué libros hay con el género seleccionado
 *@author Liberty Tamayo
 * @version 1/5/2024
 * @see InicioFragment
 * @see LibrosViewModel
 * */

class InicioFragment: Fragment() {


    private lateinit var binding: InicioBinding
    private lateinit var alertDialog: androidx.appcompat.app.AlertDialog

    private val myViewModel by activityViewModels<perfilViewModel> {
        perfilViewModel.MyViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = InicioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        * Dependiendo de qué botón se pulse, llevará a un género u otro, lo que funcionará como filtro
        * para posteriormente mostrar únicamente los libros que tienen dicho género
        *  */

        binding.toRomance.setOnClickListener {
            abrirLibrosPorCategoria("Romance")
        }
        binding.toThriller.setOnClickListener {
            abrirLibrosPorCategoria("Thriller")
        }
        binding.toFanta.setOnClickListener {
            abrirLibrosPorCategoria("Fantasía")
        }
        binding.toTerror.setOnClickListener {
            abrirLibrosPorCategoria("Terror")
        }
        binding.toAventuras.setOnClickListener {
            abrirLibrosPorCategoria("Aventura")
        }

        binding.toAccion.setOnClickListener {
            abrirLibrosPorCategoria("Acción")
        }

        binding.toBelico.setOnClickListener {
            abrirLibrosPorCategoria("Bélico")
        }

        binding.toHistoria.setOnClickListener {
            abrirLibrosPorCategoria("Historia")
        }

        binding.toCiencia.setOnClickListener {
            abrirLibrosPorCategoria("Ciencia")
        }

        binding.toCienciaFic.setOnClickListener {
            abrirLibrosPorCategoria("Ciencia Ficción")
        }

        binding.toInfantil.setOnClickListener {
            abrirLibrosPorCategoria("Infantil")
        }

        binding.toMisterio.setOnClickListener {
            abrirLibrosPorCategoria("Misterio")
        }

        binding.toFilo.setOnClickListener {
            abrirLibrosPorCategoria("Filosofía")
        }

        binding.toPoesia.setOnClickListener {
            abrirLibrosPorCategoria("Poesía")
        }

        binding.toClasico.setOnClickListener {
            abrirLibrosPorCategoria("Clásico")
        }

        binding.toPsicologia.setOnClickListener {
            abrirLibrosPorCategoria("Psicología")
        }

        binding.toAficion.setOnClickListener {
            abrirLibrosPorCategoria("Aficiones")
        }

        binding.toTragedia.setOnClickListener {
            abrirLibrosPorCategoria("Tragedia")
        }

        binding.toExtranjeros.setOnClickListener {
            abrirLibrosPorCategoria("Extranjeros")
        }

        binding.toEducativo.setOnClickListener {
            abrirLibrosPorCategoria("Educativos")
        }

        binding.toBio.setOnClickListener {
            abrirLibrosPorCategoria("Biografía")
        }


        /*
        * Muestra el nombre del usuario que ha hecho login en la aplicación
        * */
        myViewModel.viewModelScope.launch {
            binding.textView20.text = buildString {
                append(" ")
                append(myViewModel.nombreUsuario())
            }
        }

        /*
        * Estando en la pantalla de inicio, si se pulsa el botón del móvil para ir hacia atrás,
        * saldrá de la aplicación
        * */
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                salirAplicacionDialog()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

    }

    /*
    * Función que permite ir hacia la categoría deseada
    * */
    /**
     *
     * @param categoria
     */
    private fun abrirLibrosPorCategoria(categoria: String) {
        val bundle = bundleOf("categoria" to categoria)

        bundle.putString("categoria", categoria)
        findNavController().navigate(R.id.action_inicioFragment_to_listadoFragment, bundle)
    }

    /*
    * Dialog que se muestra antes de salir de la aplicación
    * */
    private fun salirAplicacionDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog_cerrarsesion, null)
        val builder = context?.let { androidx.appcompat.app.AlertDialog.Builder(it, R.style.AlertDialogTheme) }

        builder?.setView(dialogView)
        builder?.setTitle("")
        builder?.setMessage("")

        val positiveButton = dialogView.findViewById<ImageButton>(R.id.positiveButton)
        val negativeButton = dialogView.findViewById<ImageButton>(R.id.negativeButton)
        val text = dialogView.findViewById<TextView>(R.id.messageTextView2)

        if (builder != null) {
            alertDialog = builder.create()
        }

        positiveButton.setOnClickListener {
            requireActivity().finishAffinity()
        }
        negativeButton.setOnClickListener {
            alertDialog.dismiss()
        }
        text.text = buildString {
            append("¿Salir de la aplicación?")
        }

        alertDialog.show()
    }

}