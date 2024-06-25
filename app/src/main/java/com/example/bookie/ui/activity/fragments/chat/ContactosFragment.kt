package com.example.bookie.ui.activity.fragments.chat


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookie.R
import com.example.bookie.data.model.contactos.RespuestaContactoItem
import com.example.bookie.databinding.BuzonBinding
import com.example.bookie.ui.activity.adapters.contactos.AdapterContacto
import com.example.bookie.ui.activity.viewsmodels.ChatViewModel
import com.example.bookie.ui.activity.viewsmodels.SharedViewModel

import kotlinx.coroutines.launch

/**
 * Clase para mostrar los contactos de un usuario y al clicar en el holder acceder al chat
 *@author Lorena Villar
 * @version 8/5/2024
 * @see RespuestaContactoItem
 * @see BuzonBinding
 * @see AdapterContacto
 * @see ChatViewModel
 * @see SharedViewModel
 *
 * */

class ContactosFragment: Fragment() {

    private var _binding: BuzonBinding? = null
    private val binding get() = _binding!!

    private val myViewModelchat by activityViewModels<ChatViewModel> {
        ChatViewModel.MyViewModelFactory(requireContext())
    }

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val contactos = mutableListOf<RespuestaContactoItem>()
    private lateinit var adapterContacto: AdapterContacto


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BuzonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterContacto = AdapterContacto(requireContext(),
            contactos,
            myViewModelchat,
            sharedViewModel,
            object : AdapterContacto.OnItemClickListener{
            override fun onItemClick(dataItem: RespuestaContactoItem) {
                myViewModelchat.contacto_seleccionado.value = dataItem
                findNavController().navigate(R.id.action_contactosFragment_to_chatFragment)
            }
        })

        binding.rvContactos.layoutManager = LinearLayoutManager(requireContext())
        binding.rvContactos.adapter = adapterContacto

        myViewModelchat.liveDataListaContactos.observe(viewLifecycleOwner) { listaContactos ->
            if (listaContactos != null) {
                if (!listaContactos.isEmpty()) {
                    adapterContacto.actualizarListado(listaContactos)
                } else {
                    binding.sinIntercambios.visibility = View.VISIBLE
                }


            } else {
                binding.sinIntercambios.visibility = View.VISIBLE
                Log.e("errorContactos", "Fallo al conseguir contactos")
            }
        }

        myViewModelchat.viewModelScope.launch {
            //llama a la id del usuario propio
            val postId = myViewModelchat.postId()

            val userIdPedido = postId?.toDouble()?.toLong()
            if (userIdPedido != null) {

                myViewModelchat.getContactos(userIdPedido)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
