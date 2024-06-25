package com.example.bookie.ui.activity.fragments.registro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bookie.R
import com.example.bookie.databinding.Registro1Binding


class Registro1Fragment : Fragment() {

    private var _binding: Registro1Binding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = Registro1Binding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageView.setOnClickListener {
            val fragment = Registro1Fragment()
            val bundle = Bundle()

            if(binding.nombre.text.toString().isNotEmpty()){
                bundle.putString("nombre", binding.nombre.text.toString())
            }else{
                binding.textInputLayout.error = "Campo vacío"
            }

            if(binding.email.text.toString().isNotEmpty()){
                bundle.putString("email", binding.email.text.toString())
            }else{
                binding.textInputLayout2.error = "Campo vacío"
            }

            if(binding.contrasenia.text.toString().isNotEmpty()){
                if(binding.contrasenia.text.toString() == binding.repContrasenia.text.toString()){
                    bundle.putString("contrasenia", binding.contrasenia.text.toString())
                }else{
                    binding.textInputLayout4.error = "Las contraseñas no coinciden"
                }
            }else{
                binding.textInputLayout3.error = "Campo vacío"
            }


            fragment.arguments = bundle

            if (binding.contrasenia.text.toString() == binding.repContrasenia.text.toString() && (binding.nombre.text.toString().isNotEmpty()) && (binding.email.text.toString().isNotEmpty()) && (binding.contrasenia.text.toString().isNotEmpty())){
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}