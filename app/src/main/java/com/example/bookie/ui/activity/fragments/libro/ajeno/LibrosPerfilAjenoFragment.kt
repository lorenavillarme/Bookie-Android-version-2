package com.example.bookie.ui.activity.fragments.libro.ajeno

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.bookie.R
import com.example.bookie.data.model.RespuestaLibro
import com.example.bookie.databinding.FragmentMisLibrosAjenosBinding
import com.example.bookie.ui.activity.adapters.LibrosAdapter
import com.example.bookie.ui.activity.viewsmodels.LibrosViewModel
import com.example.bookie.ui.activity.viewsmodels.SharedViewModel
import kotlinx.coroutines.launch

/**
 * Clase para mostrar los libros propios del usuario ajeno, agregar el favorito y pedir un intercambio
 *@author Lorena Villar
 * @version 13/5/2024
 * @see SharedViewModel
 * @see LibrosAdapter
 * @see FragmentMisLibrosAjenosBinding
 * @see LibrosViewModel
 * @see RespuestaLibro
 * */

class LibrosPerfilAjenoFragment : Fragment() {

    private lateinit var binding: FragmentMisLibrosAjenosBinding

    private val libroV by activityViewModels<LibrosViewModel> {
        LibrosViewModel.MyViewModelFactory(requireContext())
    }
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMisLibrosAjenosBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.re.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)

        val listAdapter = LibrosAdapter(object : LibrosAdapter.OnItemClickListener {
            override fun onItemClick(libro: RespuestaLibro) {
                libroV.libroSelectedAjeno.value = libro
                findNavController().navigate(R.id.action_usuarioAjenoFragment_to_detallesLibroFragment)
            }
        })

        libroV.libroAjenoLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                listAdapter.update(it)
            }
        }

        //conseguir libros ajenos
        libroV.viewModelScope.launch {
            //conseguir id ajeno
            sharedViewModel.dataIdUser.observe(viewLifecycleOwner) { dataId ->
                val idUser = dataId
                if (idUser != null)  {
                    binding.re.adapter = listAdapter
                    libroV.getLibroAjeno(idUser.toDouble().toLong())
                    libroV.libroAjenoLiveData.observe(viewLifecycleOwner) { libro ->
                        if (libro != null) {
                            listAdapter.update(libro)
                        }
                    }
                }
            }
        }
    }
}