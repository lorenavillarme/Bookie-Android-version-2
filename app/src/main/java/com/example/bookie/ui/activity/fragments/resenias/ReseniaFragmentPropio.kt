package com.example.bookie.ui.activity.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.bookie.databinding.FragmentReseniasPropiasBinding
import com.example.bookie.ui.activity.adapters.ReseniaAdapter
import com.example.bookie.ui.activity.viewsmodels.ChatViewModel
import com.example.bookie.ui.activity.viewsmodels.SharedViewModel
import com.example.bookie.ui.activity.viewsmodels.perfilViewModel
import kotlinx.coroutines.launch

class ReseniaFragmentPropio : Fragment() {

    private val myViewModel by activityViewModels<perfilViewModel> {
        perfilViewModel.MyViewModelFactory(requireContext())
    }

    private val myViewModelChat by activityViewModels<ChatViewModel> {
        ChatViewModel.MyViewModelFactory(requireContext())
    }

    private val sharedViewModel: SharedViewModel by activityViewModels() //usar en caso de media de notas

    private lateinit var binding: FragmentReseniasPropiasBinding
    private lateinit var reseniasAdapter: ReseniaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReseniasPropiasBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recy.layoutManager = StaggeredGridLayoutManager(1, RecyclerView.VERTICAL)
        reseniasAdapter = ReseniaAdapter()
        binding.recy.adapter = reseniasAdapter

        myViewModel.liveDataResenias.observe(viewLifecycleOwner) { resenias ->
            if (resenias != null) {
                if (!resenias.isEmpty()) {
                    reseniasAdapter.update(resenias)
                    binding.sinRes.visibility = View.GONE
                } else {
                    binding.sinRes.visibility = View.VISIBLE
                }
                var notaAcumulada = 0
                Log.e("test", notaAcumulada.toString())
                if (resenias.count() != 0) {
                    var contador = resenias.count()
                    for (resenia in resenias) {
                        notaAcumulada += resenia.puntuacion

                    }
                    var notaFinal: Double = (notaAcumulada / contador).toDouble()
                    sharedViewModel.setData3(notaFinal.toString())
                }
            }
        }

        myViewModelChat.viewModelScope.launch {
            val idUser = myViewModelChat.postId()?.toDouble()?.toLong()
            if (idUser != null) {
                myViewModel.getReseniasPersonas(idUser)
            }
        }

    }

}

