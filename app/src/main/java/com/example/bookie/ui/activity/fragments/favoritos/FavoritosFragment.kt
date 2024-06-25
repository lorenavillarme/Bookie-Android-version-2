package com.example.bookie.ui.activity.fragments.favoritos

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
import com.example.bookie.data.model.favoritos.RespuestaFavorito
import com.example.bookie.databinding.FragmentFavoritosBinding
import com.example.bookie.ui.activity.adapters.AdapterFavoritos
import com.example.bookie.ui.activity.viewsmodels.ChatViewModel
import com.example.bookie.ui.activity.viewsmodels.LibrosViewModel
import com.example.bookie.ui.activity.viewsmodels.perfilViewModel
import kotlinx.coroutines.launch

/**
 * Clase para mostrar los libros favoritos del usuario propio
 *@author Lorena Villar
 * @version 16/5/2024
 * @see RespuestaFavorito
 * @see FragmentFavoritosBinding
 * @see AdapterFavoritos
 * @see ChatViewModel
 * @see LibrosViewModel
 * @see perfilViewModel
 *
 * */
class FavoritosFragment : Fragment() {

    private val perfilViewModel: perfilViewModel by activityViewModels()

    private val myViewModelChat by activityViewModels<ChatViewModel> {
        ChatViewModel.MyViewModelFactory(requireContext())
    }


    private lateinit var binding: FragmentFavoritosBinding
    private lateinit var favoritosAdapter: AdapterFavoritos

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritosBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        binding.recyclerFavs.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)

        binding.recyclerFavs.adapter = favoritosAdapter

        perfilViewModel.liveDataFavoritos.observe(viewLifecycleOwner) { favoritos ->
            if (favoritos != null) {
                if (!favoritos.isEmpty()) {
                    favoritosAdapter.update(favoritos)
                    binding.sinfav.visibility = View.GONE
                } else {
                    binding.sinfav.visibility = View.VISIBLE
                }
            }
        }
        myViewModelChat.viewModelScope.launch {
            val idUser = myViewModelChat.postId()?.toDouble()?.toLong()
            if (idUser != null) {
                perfilViewModel.getFavoritos(idUser)
            }
        }

    }
    private fun setupRecyclerView() {
        favoritosAdapter = AdapterFavoritos(object : AdapterFavoritos.OnItemClickListener{
            override fun onItemClick(dataItem: RespuestaFavorito) {
                perfilViewModel.liveDataCrearFavorito.value = dataItem
                findNavController().navigate(R.id.action_usuarioFragment_to_favoritosPropiosDetallesLibroFragment)
            }
        })
    }

}