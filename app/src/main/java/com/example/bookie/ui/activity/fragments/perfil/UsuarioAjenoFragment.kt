package com.example.bookie.ui.activity.fragments.perfil

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.bookie.R
import com.example.bookie.databinding.FragmentPerfilAjenoBinding
import com.example.bookie.ui.activity.adapters.PerfilAdapterAjeno
import com.example.bookie.ui.activity.adapters.ReseniaAdapter
import com.example.bookie.ui.activity.viewsmodels.ChatViewModel
import com.example.bookie.ui.activity.viewsmodels.SharedViewModel
import com.example.bookie.ui.activity.viewsmodels.perfilViewModel
import com.google.android.material.tabs.TabLayoutMediator

/**
 * Clase para mostrar la información del perfil de un usuario ajeno
 * @author Lorena Villar, Liberty Tamayo
 * @version 4/4/2024
 * @see FragmentPerfilAjenoBinding
 * @see PerfilAdapterAjeno
 * @see ReseniaAdapter
 * @see ChatViewModel
 * @see SharedViewModel
 * @see perfilViewModel
 * @see TabLayoutMediator
 * */

class UsuarioAjenoFragment: Fragment() {

    private lateinit var binding: FragmentPerfilAjenoBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val myViewModelchat by activityViewModels<ChatViewModel> {
        ChatViewModel.MyViewModelFactory(requireContext())
    }
    private val myViewModel by activityViewModels<perfilViewModel> {
        perfilViewModel.MyViewModelFactory(requireContext())
    }
    private lateinit var reseniasAdapter: ReseniaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerfilAjenoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PerfilAdapterAjeno(requireActivity())
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Mis libros"
                1 -> tab.text = "Reseñas"
                else -> tab.text = "Favoritos"
            }
        }.attach()


        //conseguir reseñas ajenas
        reseniasAdapter = ReseniaAdapter()
        sharedViewModel.dataIdUser.observe(viewLifecycleOwner) { dataId ->
            myViewModel.getReseniasPersonasAjenas(dataId.toLong()).observe(viewLifecycleOwner){  resenias ->
                if (resenias != null) {
                    reseniasAdapter.update(resenias)
                    var notaAcumulada = 0
                    Log.e("test", notaAcumulada.toString())
                    if (resenias.isNotEmpty()) {
                        val contador = resenias.count()
                        for (resenia in resenias) {
                            notaAcumulada += resenia.puntuacion
                        }
                        val notaFinal: Double = notaAcumulada.toDouble() / contador.toDouble()
                        binding.nota.text = notaFinal.toString()
                    }
                }
            }
        }

        //conseguir datos del usuario ajeno
        sharedViewModel.dataIdUser.observe(viewLifecycleOwner) { dataId ->
            myViewModelchat.getUsuarios(dataId.toLong())
            myViewModelchat.getUsuarios(dataId.toLong()).observe(viewLifecycleOwner) {usuario ->
                if (usuario != null) {
                    binding.ciudadPerfil.text = usuario.ciudad
                    binding.provinciaPerfil.text = usuario.provincia
                    binding.usuarioPerfil.text = usuario.username
                }

                val decodedBitmap = usuario?.foto?.let {base64ToBitmap(it)}
                if (decodedBitmap != null){
                    binding.imageView6.setImageBitmap(decodedBitmap)
                }else{
                    binding.imageView6.setImageResource(R.drawable.generico)
                }

            }
        }

    }

    //función de conversión de imágenes
    private fun base64ToBitmap(base64Str: String): Bitmap {
        val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

}