package com.example.bookie.ui.activity.fragments.chat

import AdapterMensaje
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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookie.R
import com.example.bookie.data.network.ApiClient
import com.example.bookie.databinding.ChatBinding
import com.example.bookie.ui.activity.viewsmodels.ChatViewModel
import kotlinx.coroutines.launch

/**
 * Clase para enviar y ver mensajes con la persona que se está realizando un intercambio
 * @author Lorena Villar
 * @version 8/5/2024
 * @see ChatBinding
 * @see ApiClient
 * @see ChatViewModel
 * */

class ChatFragment : Fragment() {

    private var _binding: ChatBinding? = null
    private val binding get() = _binding!!

    private lateinit var apiClient: ApiClient

    private val myViewModelchat by activityViewModels<ChatViewModel> {
        ChatViewModel.MyViewModelFactory(requireContext())
    }

    private lateinit var adapterMensaje: AdapterMensaje


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ChatBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apiClient = ApiClient()

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //navegar hacia atrás
        binding.backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_chatFragment_to_contactosFragment)
        }

        //sacar datos del contacto seleccionado
        myViewModelchat.contacto_seleccionado.observe(viewLifecycleOwner) { data ->
            val chat_id = data.chatId.toLong()
            //sacar id propia
            myViewModelchat.viewModelScope.launch {
                val postId = myViewModelchat.postId()
                if (postId != null) {
                    //pasa el id propio a el adaptador de mensajes
                    adapterMensaje = AdapterMensaje(postId)
                    binding.recyclerView.adapter = adapterMensaje
                    //consigue el chat deseado a partir de la id de este
                    myViewModelchat.getChat(chat_id).observe(viewLifecycleOwner){
                        if (it != null) {
                            adapterMensaje.actualizarMensajes(it)
                        }
                    }
                } else {
                    Log.e("ErrorChatFragment", "Error obteniendo la ID")
                }
                val userIdPedido = postId
                val chatIdPedido = chat_id
                if (userIdPedido != null) {
                    myViewModelchat.getContactos(userIdPedido.toDouble().toLong())
                }

                //sacar username ajeno
                myViewModelchat.liveDataListaContactos.observe(viewLifecycleOwner) { listaContactos ->
                    if (listaContactos != null && listaContactos.isNotEmpty()) {
                        var encontrado = false
                        var username = ""
                        for (contacto in listaContactos) {
                            if (contacto.chatId.toLong() == chatIdPedido) {
                                encontrado = true
                                if (userIdPedido != null) {
                                    //comprobación para asegurar que el usuario es el contrario a el usuario propio (es decir, es el ajeno)
                                    if (userIdPedido.toDouble().toInt() != contacto.usuarioEmisorId) {
                                        username = contacto.usuarioEmisorUsername
                                        myViewModelchat.getUsuarios(contacto.usuarioEmisorId.toLong()).observe(viewLifecycleOwner) { usuario ->
                                            val decodedBitmap2 = usuario?.foto?.let {base64ToBitmap(it)}

                                            if (decodedBitmap2 != null){
                                                binding.imgUserChat.setImageBitmap(decodedBitmap2)
                                            }else{
                                                binding.imgUserChat.setImageResource(R.drawable.generico)
                                            }
                                        }
                                        binding.tvUsernameChat.text = username
                                    } else {
                                        username = contacto.usuarioReceptorUsername
                                        myViewModelchat.getUsuarios(contacto.usuarioReceptorId.toLong()).observe(viewLifecycleOwner) { usuario ->
                                            val decodedBitmap2 = usuario?.foto?.let {base64ToBitmap(it)}

                                            if (decodedBitmap2 != null){
                                                binding.imgUserChat.setImageBitmap(decodedBitmap2)
                                            }else{
                                                binding.imgUserChat.setImageResource(R.drawable.generico)
                                            }
                                        }

                                        binding.tvUsernameChat.text = username

                                    }
                                }
                                break
                            }
                        }
                        if (!encontrado) {
                            Log.e("error", "no se encuentra el chatid")
                        }
                    } else {
                        Log.e("error", "no se encuentran contactos")
                    }
                }
            }

            binding.btnEnviar.setOnClickListener {
                val textoMensaje = binding.chatMensajeInput.text.toString().trim()
                if (textoMensaje.isNotEmpty()) {

                    myViewModelchat.viewModelScope.launch {
                        val postId = myViewModelchat.postId()
                        if (postId != null) {
                            myViewModelchat.postMensaje(
                                textoMensaje,
                                postId.toDouble().toInt(),
                                chat_id.toInt()
                            ).observe(viewLifecycleOwner) {

                                myViewModelchat.getChat(chat_id).observe(viewLifecycleOwner) {
                                    if (it != null) {
                                        adapterMensaje.actualizarMensajes(it)
                                        binding.chatMensajeInput.text.clear()
                                        binding.recyclerView.scrollToPosition(adapterMensaje.itemCount - 1)
                                    }
                                }
                            }}else {
                            Log.e("ErrorChatFragment", "Error obteniendo la ID")
                        }
                    }
                }
            }
        }
    }

    //función de conversión de imágenes
    private fun base64ToBitmap(base64Str: String): Bitmap {
        val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
