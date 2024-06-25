package com.example.bookie.ui.activity.fragments.libro.propio

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.bookie.R
import com.example.bookie.data.model.RespuestaLibro
import com.example.bookie.databinding.FragmentMisLibrosBinding
import com.example.bookie.databinding.LibroPropioDetallesBinding
import com.example.bookie.ui.activity.adapters.LibrosAdapter
import com.example.bookie.ui.activity.viewsmodels.LibrosViewModel
import kotlinx.coroutines.launch

/**
 * Esta clase define funciones para mostrar los libros que el usuario ha subido a la base de datos, los cuales se muestran en su perfil
 *@author Liberty Tamayo
 * @version 17/5/2024
 * @see FragmentMisLibrosBinding
 * @see LibrosViewModel
 * */

class MisLibrosFragment : Fragment() {

    private val libroV by activityViewModels<LibrosViewModel> {
        LibrosViewModel.MyViewModelFactory(requireContext())
    }

    private lateinit var binding: FragmentMisLibrosBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMisLibrosBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        * Configuración del recyclerView para que los libros se muestren en una rejilla de 2 columnas
        * */
        binding.re.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)

        /*
        * Configuración del adapter para detectar qué libro pulsa el usuario para así
        * poder navegar al fragment de detalles con la información correspondiente
        * */
        val listAdapter = LibrosAdapter(object : LibrosAdapter.OnItemClickListener {
            override fun onItemClick(libro: RespuestaLibro) {
                libroV.libroSelected.value = libro
                findNavController().navigate(R.id.action_usuarioFragment_to_miLibroDetalleFragment)
            }
        })


        /*
        * Se obtiene tantl la id del usuario como la id de los libros para así poder mostrar
        * únicamente los libros que el usuario ha subido a la base de datos
        * */

        libroV.viewModelScope.launch {
            // se obtiene la id del libro
            val postId = libroV.postId()
            Log.d("MisLibrosFragment", "postId: $postId") // Log para verificar el valor de postId

            // se convierte la id del libro a touble para posteriormente convertirla a long
            val idDouble = postId?.toDouble()
            val idUser = idDouble?.toLong()
            Log.d("MisLibrosFragment", "idUser: $idUser") // Log para verificar el valor convertido

            if (idUser != null) {
                binding.re.adapter = listAdapter
                libroV.getLibroPropio(idUser).observe(viewLifecycleOwner) {
                    if (it != null) {
                        if (!it.isEmpty()) {
                                listAdapter.update(it)
                        } else {
                            binding.sinLibros.visibility = View.VISIBLE
                        }

                    }
                }
            } else {
                binding.sinLibros.visibility = View.VISIBLE
                Toast.makeText(requireContext(), "Error obteniendo la ID", Toast.LENGTH_SHORT).show()
            }
        }


    }

}