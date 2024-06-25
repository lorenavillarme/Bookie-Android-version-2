package com.example.bookie.ui.activity.fragments

import android.os.Bundle
import android.util.Log
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
import com.example.bookie.databinding.FragmentReseniasAjenasBinding
import com.example.bookie.ui.activity.adapters.ReseniaAdapter
import com.example.bookie.ui.activity.viewsmodels.ChatViewModel
import com.example.bookie.ui.activity.viewsmodels.SharedViewModel
import com.example.bookie.ui.activity.viewsmodels.perfilViewModel
import kotlinx.coroutines.launch

class ReseniaFragmentAjena : Fragment() {

    private val myViewModel by activityViewModels<perfilViewModel> {
        perfilViewModel.MyViewModelFactory(requireContext())
    }

    private lateinit var binding: FragmentReseniasAjenasBinding
    private lateinit var reseniasAdapter: ReseniaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReseniasAjenasBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recy.layoutManager = StaggeredGridLayoutManager(1, RecyclerView.VERTICAL)
        reseniasAdapter = ReseniaAdapter()
        binding.recy.adapter = reseniasAdapter



        binding.btnEscribirResenia.setOnClickListener {
            findNavController().navigate(R.id.action_usuarioAjenoFragment_to_subirReseniaFragment)
        }
    }

}

