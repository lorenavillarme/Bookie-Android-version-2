package com.example.bookie.ui.activity.fragments.libro.ajeno

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.bookie.R
import com.example.bookie.data.model.RespuestaLibro
import com.example.bookie.databinding.FragmentLibrosNoApiBinding
import com.example.bookie.databinding.FragmentMisLibrosBinding
import com.example.bookie.ui.activity.adapters.LibrosAdapter
import com.example.bookie.ui.activity.viewsmodels.LibrosViewModel

/**
 * Esta clase define funciones para mostrar los libros que se encuentran en la base de datos. Los libros se
 * encuentran filtrados según su género, cosa que se ha hecho anteriormente en el fragment InicioFragment
 *@author Liberty Tamayo
 * @version 13/5/2024
 * @see FragmentLibrosNoApiBinding
 * @see LibrosViewModel
 * */

class ListadoFragment : Fragment() {

    private val librosV by activityViewModels<LibrosViewModel> {
        LibrosViewModel.MyViewModelFactory(requireContext())
    }

    private lateinit var binding: FragmentLibrosNoApiBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLibrosNoApiBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // se crea una variable para posteriormente poder filtrar por géneros dependiendo de qué genero se ha pulsado en la pantalla de inicio
        val categoria = arguments?.getString("categoria") ?: ""

        binding.recycler.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)

        // configuración del adapter para que al pulsar en un libro lleve a otro fragment con sus detalles
        val listAdapter = LibrosAdapter(object : LibrosAdapter.OnItemClickListener {
            override fun onItemClick(libro: RespuestaLibro) {
                librosV.libroSelected.value = libro
                findNavController().navigate(R.id.action_listadoFragment_to_detallesLibroFragment)
            }
        })

        // configuración para recibir el listado de libros al recargar la vista
        binding.recycler.adapter = listAdapter
        binding.swipe.setColorSchemeColors(Color.YELLOW, Color.GRAY)
        binding.swipe.setOnRefreshListener {
            librosV.getLibro()
        }


        // con observe se suscribre a los cambios de libroLiveData para que cada vez que el valor de dicho livedata cambie el código que está dentro del observe se ejecute
        librosV.libroLiveData.observe(viewLifecycleOwner) { libros ->
            binding.swipe.isRefreshing = false
            // se verifica que libros no venga a null
            if (libros != null) {
                // se filtran los libros por categoría
                val librosFiltrados = if (categoria.isNotEmpty()) {
                    libros.filter { it.genero == categoria }
                } else {
                    libros
                }
                // finalmente se actualiza el adaptador con la lista filtrada
                listAdapter.update(librosFiltrados)
            }
        }

        librosV.getLibro()

        // isIconified = false permite iniciar la búsqueda de libros pulsando cualquier parte del searchview sin que se tenga que pulsar la lupa
        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // método que se llama cuando el usuario empieza a buscar.
            override fun onQueryTextSubmit(query: String?): Boolean {
                listAdapter.filter.filter(query)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                // cuando el usuario borra el texto en el buscador, devuelve el listado
                if (newText.isNullOrEmpty()) {
                    librosV.getLibro()
                } else {
                    listAdapter.filter.filter(newText)
                }
                return true

            }
        })

        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}