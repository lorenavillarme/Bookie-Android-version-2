package com.example.bookie.ui.activity.fragments.favoritos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.bookie.R
import com.example.bookie.data.model.favoritos.RespuestaFavorito
import com.example.bookie.databinding.FragmentFavoritosAjenosBinding
import com.example.bookie.ui.activity.adapters.AdapterFavoritos
import com.example.bookie.ui.activity.viewsmodels.SharedViewModel
import com.example.bookie.ui.activity.viewsmodels.perfilViewModel
import kotlinx.coroutines.launch

/**
 * Clase para mostrar los libros favoritos del usuario ajeno
 *@author Lorena Villar
 * @version 16/5/2024
 * @see RespuestaFavorito
 * @see FragmentFavoritosAjenosBinding
 * @see AdapterFavoritos
 * @see SharedViewModel
 * @see perfilViewModel
 *
 * */

class FavoritosFragmentAjenos : Fragment() {

    private val perfilViewModel: perfilViewModel by activityViewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var binding: FragmentFavoritosAjenosBinding
    private lateinit var favoritosAdapter: AdapterFavoritos

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritosAjenosBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        binding.recyclerFavs.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        binding.recyclerFavs.adapter = favoritosAdapter

        //conseguir favoritos del usuario ajeno
        sharedViewModel.dataIdUser.observe(viewLifecycleOwner) { dataId ->
            val idUser = dataId
            perfilViewModel.getFavoritosAjenos(idUser.toLong())
        }

        //actualizar listado favs
        perfilViewModel.liveDataFavoritosAjenos.observe(viewLifecycleOwner) { favoritos ->
            if (favoritos != null) {
                favoritosAdapter.update(favoritos)
            }
        }
    }

    private fun setupRecyclerView() {
        favoritosAdapter = AdapterFavoritos(object : AdapterFavoritos.OnItemClickListener{
            override fun onItemClick(dataItem: RespuestaFavorito) {
                perfilViewModel.liveDataCrearFavorito.value = dataItem
                findNavController().navigate(R.id.action_usuarioAjenoFragment_to_favoritosAjenosDetallesLibroFragment)
            }
        })
    }

}