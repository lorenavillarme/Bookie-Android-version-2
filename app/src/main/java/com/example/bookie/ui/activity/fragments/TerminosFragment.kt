package com.example.bookie.ui.activity.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bookie.databinding.TerminosUsoBinding

/**
 * Clase para mostrar los términos de uso de Bookie
 * @author Lorena Villar
 * @version 28/5/2024
 * @see TerminosUsoBinding
 * */
class TerminosFragment : Fragment() {

    private lateinit var binding: TerminosUsoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TerminosUsoBinding.inflate(inflater, container, false)
        return binding.root
    }

}