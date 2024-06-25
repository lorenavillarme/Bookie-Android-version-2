package com.example.bookie.ui.activity.fragments.registro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bookie.R
import com.example.bookie.databinding.Registro2Binding


class Registro2Fragment : Fragment() {

    private var _binding: Registro2Binding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = Registro2Binding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nombre = arguments?.getString("nombre")
        val email = arguments?.getString("email")
        val contrasenia = arguments?.getString("contrasenia")

        binding.imageView.setOnClickListener {
            val fragment = Registro2Fragment()
            val bundle = Bundle()
            bundle.putString("nombre", nombre)
            bundle.putString("email", email)
            bundle.putString("contrasenia", contrasenia)

            if(binding.etProvincia.text.toString().isNotEmpty()){
                bundle.putString("provincia", binding.etProvincia.text.toString())
            }else{
                binding.itProvincia.error = "Campo vacío"
            }

            if(binding.etCiudad.text.toString().isNotEmpty()){
                bundle.putString("ciudad", binding.etCiudad.text.toString())
            }else{
                binding.itCiudad.error = "Campo vacío"
            }

            if(binding.etCp.text.toString().isNotEmpty()){
                bundle.putInt("codPostal", binding.etCp.text.toString().toInt())
            }else{
                binding.itCp.error = "Campo vacío"
            }

            fragment.arguments = bundle

            if((binding.etProvincia.text.toString().isNotEmpty()) && (binding.etCiudad.text.toString().isNotEmpty()) && (binding.etCp.text.toString().isNotEmpty())){
                findNavController().navigate(R.id.action_SecondFragment_to_registro3Fragment,bundle)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}