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
import androidx.lifecycle.viewModelScope
import com.example.bookie.R
import com.example.bookie.databinding.FragmentPerfilAjenoBinding
import com.example.bookie.databinding.FragmentPerfilBinding
import com.example.bookie.ui.activity.adapters.PerfilAdapterAjeno
import com.example.bookie.ui.activity.adapters.PerfilAdapterPropio
import com.example.bookie.ui.activity.adapters.ReseniaAdapter
import com.example.bookie.ui.activity.fragments.ModalBottomSheet
import com.example.bookie.ui.activity.viewsmodels.ChatViewModel
import com.example.bookie.ui.activity.viewsmodels.SharedViewModel
import com.example.bookie.ui.activity.viewsmodels.perfilViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

/**
 * Clase para mostrar la información del perfil de un usuario
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

class UsuarioFragment : Fragment() {


    private lateinit var binding: FragmentPerfilBinding
    private val myViewModel: perfilViewModel by activityViewModels()

    //bottom sheet
    val bottomSheet = ModalBottomSheet()

    private val myViewModelchat by activityViewModels<ChatViewModel> {
        ChatViewModel.MyViewModelFactory(requireContext())
    }

    private lateinit var reseniasAdapter: ReseniaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // configuración el adaptador del ViewPager
        val adapter = PerfilAdapterPropio(requireActivity())
        binding.viewPager.adapter = adapter

        // configuración los tabs del TabLayout
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Mis libros"
                1 -> tab.text = "Reseñas"
                else -> tab.text = "Favoritos"
            }
        }.attach()


        myViewModel.viewModelScope.launch {
            //conseguir reseñas propias
            reseniasAdapter = ReseniaAdapter()
            val idPropio = myViewModel.postId()

            if (idPropio != null) {
                myViewModel.getReseniasPersonasAjenas(idPropio.toDouble().toLong()).observe(viewLifecycleOwner){  resenias ->
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

            val decodedBitmap = myViewModel.fotoUsuario()?.let { base64ToBitmap(it) }

            if (decodedBitmap != null){
                binding.imageView6.setImageBitmap(decodedBitmap)
            }else{
                binding.imageView6.setImageResource(R.drawable.generico)
            }

        }

        binding.btnAjustes.setOnClickListener {
            bottomSheet.show(parentFragmentManager, "MyBottomSheet")
        }

        myViewModelchat.viewModelScope.launch {
            val idUser = myViewModelchat.postId()
            if (idUser != null) {
                myViewModelchat.getUsuarios(idUser.toDouble().toLong())
                myViewModelchat.getUsuarios(idUser.toDouble().toLong()).observe(viewLifecycleOwner) { usuario ->
                    if (usuario != null) {
                        binding.ciudadPerfil.text = usuario.ciudad
                        binding.provinciaPerfil.text = usuario.provincia
                        binding.usuarioPerfil.text = usuario.username
                    }

                }
            }
        }

    }



    private fun base64ToBitmap(base64Str: String): Bitmap {
        val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

}