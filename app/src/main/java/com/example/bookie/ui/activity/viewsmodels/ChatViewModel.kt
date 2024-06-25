package com.example.bookie.ui.activity.viewsmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bookie.data.model.UsuarioDTO
import com.example.bookie.data.model.contactos.RespuestaContactoItem
import com.example.bookie.data.model.idk.Chat
import com.example.bookie.data.model.idk.CrearChat
import com.example.bookie.data.model.idk.CrearMensaje
import com.example.bookie.data.model.idk.Mensaje
import com.example.bookie.data.model.idk.RespuestaCrearChat
import com.example.bookie.data.model.idk.RespuestaCrearMensaje
import com.example.bookie.data.model.idk.UsuarioEmisor
import com.example.bookie.data.model.idk.UsuarioReceptor
import com.example.bookie.data.model.idk.Usuario
import com.example.bookie.data.repository.Repositorio
import com.example.bookie.utils.sessionmanager.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * ViewModel para crear y listar mensajes, chats, conseguir usuarios por susu ids y obtener la id del suario propio
 * @author Lorena Villar
 * @property context Contexto de la aplicación.
 * @version 1/5/2024
 * @see UsuarioDTO
 * @see RespuestaContactoItem
 * @see Chat
 * @see CrearChat
 * @see CrearMensaje
 * @see Mensaje
 * @see RespuestaCrearChat
 * @see RespuestaCrearMensaje
 * @see UsuarioEmisor
 * @see UsuarioReceptor
 * @see Usuario
 * @see Repositorio
 * @see SessionManager
 * */


class ChatViewModel(val context: Context) : ViewModel() {

    private val repositorio = Repositorio(context)

    val liveDataCrearChat = MutableLiveData<RespuestaCrearChat>()
    val liveDataListaContactos = MutableLiveData<List<RespuestaContactoItem>?>()
    val contacto_seleccionado = MutableLiveData<RespuestaContactoItem>()


    fun postChat(idEmisor: Int, idReceptor: Int) {
        // Crea los objetos UsuarioReceptor y UsuarioEmisor con sus id
        val usuarioEmisor = UsuarioEmisor(idEmisor)
        val usuarioReceptor = UsuarioReceptor(idReceptor)
        val crearChat = CrearChat(usuarioEmisor, usuarioReceptor) // Crea el chat con los usuarios emisor y receptor

        viewModelScope.launch {
            val response = repositorio.postChat(crearChat) // Envia la solicitud POST al repositorio

            if (response.isSuccessful) {
                liveDataCrearChat.postValue(response.body()) // Actualiza liveDataCrearChat si la respuesta es exitosa
            }
        }
    }


    fun getChat(chatId: Long): MutableLiveData<List<Mensaje>?> {
        //se crea de manera interna el MutableLiveData para reducir errores
        val listado_chats = MutableLiveData<List<Mensaje>?>()
        CoroutineScope(Dispatchers.IO).launch {
            val response = repositorio.getChat(chatId)
            if(response.isSuccessful){
                val miRespuesta = response.body()
                listado_chats.postValue(miRespuesta)
            }
        }
        return listado_chats
    }

    //llama al listado  de contactos de un usuario según su id
    fun getContactos(userId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repositorio.GetContactos(userId)
            if (response.isSuccessful) {
                val miRespuesta = response.body()
                liveDataListaContactos.postValue(miRespuesta)
            }
        }
    }

    fun postMensaje(mensaje: String,
                    idUsuario : Int,
                    idChat : Int
    ) : MutableLiveData<RespuestaCrearMensaje> {
        //se crea de manera interna el MutableLiveData para reducir errores
        val liveDataCrearMensaje = MutableLiveData<RespuestaCrearMensaje>()
        val chatx = Chat(idChat)
        val usuariox = Usuario(idUsuario)
        //se crea la fecha de envío del mensaje
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val fecha = sdf.format(calendar.time)

        val crearMensaje = CrearMensaje(chatx, mensaje, usuariox, fecha)
        viewModelScope.launch {
            val response = repositorio.postMensaje(crearMensaje)

            if (response.isSuccessful) {
                liveDataCrearMensaje.postValue(response.body())
            }
        }
        return liveDataCrearMensaje
    }

    fun getUsuarios(userId: Long) : MutableLiveData<UsuarioDTO?> {
        val liveDataPersona = MutableLiveData<UsuarioDTO?>()
        CoroutineScope(Dispatchers.IO).launch {
            val response = repositorio.getUsuario(userId)
            if (response.isSuccessful) {
                val respuesta = response.body()
                liveDataPersona.postValue(respuesta)
            }
        }
        return liveDataPersona
    }

    suspend fun postId(): String? {
        return viewModelScope.async {
            val response = repositorio.PostId()

            if (response.isSuccessful) {
                val respuesta = response.body()
                return@async respuesta?.get("id")?.toString()
            }
            return@async null
        }.await()
    }


    class MyViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(Context::class.java).newInstance(context)
        }
    }

}