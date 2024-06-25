package com.example.bookie.ui.activity.fragments.api

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.bookie.data.model.api.Book
import com.example.bookie.databinding.LibroApiBinding
import com.example.bookie.ui.activity.viewsmodels.ApiViewModel
/**
 * Esta clase define funciones para mostrar datos sobre libros concretos conseguidos gracias a Google API Books
 *@author Lorena Villar
 * @version 23/5/2024
 * @see Book
 * @see ApiViewModel
 * @see LibroApiBinding
 * */
class FragmentLibroApi : Fragment() {

    private lateinit var binding: LibroApiBinding
    private val myViewModel: ApiViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LibroApiBinding.inflate(inflater, container, false)
        return binding.root  }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //verifica los argumentos y obtiene Book a partir de ellos
        if (arguments != null && requireArguments().containsKey("book")) {
            //dependiendo de la versión de Android se puede obtener Book de 2 formas
            //1si la versión del SDK es Android 13 (TIRAMISU) o superior, se utiliza
            // getParcelable con dos params: la clave y la clase Book

            //2 si la versión es anterior se pasa solo la clave a getParcelable
            val book = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelable("book", Book::class.java)
            } else {
                arguments?.getParcelable("book")
            }//llama a la fución que se encarga de cargar datos si book no es null
            book?.let { loadData(it) }
        } else {
            //Si los argumentos no tienen la clave o son nulos se observa libro_seleccionado en el ViewModel
            myViewModel.libro_seleccionado.observe(viewLifecycleOwner) {
                loadData(it)
            }
        }

        //navegar atrás
        binding.backAp.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    //carga todos los datos al xml a partir de los datos que recibe de la dataClass Book
    private fun loadData(it: Book){
        binding.tvTitulo.text = it.title
        binding.tvAutor.text = it.author
        binding.tvEditorial.text = it.editorial
        binding.tvGenero.text = it.genre
        binding.tvPaginas.text = it.pages.toString()
        binding.tvPuntuacion.text = it.rating.toString()
        binding.tvSinopsis.text = it.sinopsis

        Glide.with(this)
            .load("https://books.google.com/books/content?id=${it.id}&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api")
            .into(binding.imgLibro)
    }
}