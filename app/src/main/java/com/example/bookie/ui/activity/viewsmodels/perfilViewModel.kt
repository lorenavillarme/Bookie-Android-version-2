package com.example.bookie.ui.activity.viewsmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bookie.data.model.ReseniaPersona.CrearReseniaPersona
import com.example.bookie.data.model.ReseniaPersona.RespuestaReseniaPersona
import com.example.bookie.data.model.ReseniaPersona.UsuarioPuntuado
import com.example.bookie.data.model.ReseniaPersona.UsuarioPuntuador
import com.example.bookie.data.model.favoritos.CrearFavorito
import com.example.bookie.data.model.favoritos.Libro
import com.example.bookie.data.model.favoritos.RespuestaFavorito
import com.example.bookie.data.model.favoritos.Usuario
import com.example.bookie.data.model.UsuarioDTO
import com.example.bookie.data.repository.Repositorio
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * ViewModel para crear y listar resenias, favoritos (y eliminarlos) y conseguir datos del usuario
 * @author Lorena Villar, Inigo ACosta
 * @property context Contexto de la aplicación.
 * @version 4/4/2024
 * @see CrearReseniaPersona
 * @see RespuestaReseniaPersona
 * @see UsuarioPuntuado
 * @see UsuarioPuntuador
 * @see CrearFavorito
 * @see Libro
 * @see RespuestaFavorito
 * @see UsuarioDTO
 * @see Repositorio
 * */

class perfilViewModel(val context: Context) : ViewModel() {

    private val repositorio = Repositorio(context)

    val liveDataCrearReseniaPersona = MutableLiveData<RespuestaReseniaPersona>()
    val liveDataResenias = MutableLiveData<List<RespuestaReseniaPersona>?>()



    val liveDataFavoritos = MutableLiveData<List<RespuestaFavorito>?>()
    val liveDataFavoritosAjenos = MutableLiveData<List<RespuestaFavorito>?>()

    val liveDataCrearFavorito = MutableLiveData<RespuestaFavorito>()

    val usuarioActualizadoLiveData = MutableLiveData<UsuarioDTO?>()

    suspend fun nombreUsuario(): String?{
        return viewModelScope.async {
            val response = repositorio.nombreUsuario()

            if(response.isSuccessful){
                val respuesta = response.body()
                return@async respuesta?.get("nombre")?.toString()
            }
            return@async null
        }.await()
    }

    suspend fun fotoUsuario(): String?{
        return viewModelScope.async {
            val response = repositorio.fotoUsuario()

            if(response.isSuccessful){
                val respuesta = response.body()
                return@async respuesta?.get("foto")?.toString()
            }
            return@async null
        }.await()
    }

    //resenias
    fun postReseniaPersona(
        comentario : String,
        fechaReseniaPersona: String,
        puntuacion: Int,
        username: String,
        usuarioPuntuado: UsuarioPuntuado,
        usuarioPuntuador: UsuarioPuntuador) {

        //conseguir la fecha en la que se publicó la resenia
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd/MM/YYYY", Locale.getDefault())
        val fecha = sdf.format(calendar.time)

        val crearResenia = CrearReseniaPersona(comentario,fecha, puntuacion, username, usuarioPuntuado, usuarioPuntuador)

        viewModelScope.launch {
            val response = repositorio.postReseniaPersona(crearResenia)
            if (response.isSuccessful) {
                liveDataCrearReseniaPersona.postValue(response.body())
            }
        }
    }

    //conseguir resenias propias
    fun getReseniasPersonas(userId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repositorio.getReseniasPersona(userId)
            if (response.isSuccessful) {
                val miRespuestaReseniaPersona = response.body()
                liveDataResenias.postValue(miRespuestaReseniaPersona)
            }
        }
    }

    //conseguir resenias ajenas
    fun getReseniasPersonasAjenas(userId: Long) : MutableLiveData<List<RespuestaReseniaPersona>?> {
        val liveDataReseniasAjenas = MutableLiveData<List<RespuestaReseniaPersona>?>()
        CoroutineScope(Dispatchers.IO).launch {
            val response = repositorio.getReseniasPersona(userId)
            if (response.isSuccessful) {
                val miRespuestaReseniaPersona = response.body()
                liveDataReseniasAjenas.postValue(miRespuestaReseniaPersona)
            }
        }
        return liveDataReseniasAjenas
    }

    //favoritos
    fun postFavorito(
        titulo: String,
        autor: String,
        numeroPaginas: Int?,
        genero: String,
        sinopsis: String,
        editorial: String,
        prestado: Boolean,
        libroId: Int?,
        imagen: String,
        libro: Libro,
        usuario: Usuario) {
        val crearFavorito = CrearFavorito(titulo, autor, numeroPaginas, genero, sinopsis, editorial, prestado,
            libroId, imagen, libro, usuario)
        viewModelScope.launch {
            val response = repositorio.postFavorito(crearFavorito)

            if (response.isSuccessful) {
                liveDataCrearFavorito.postValue(response.body())
            }
        }
    }


    fun getFavoritos(userId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repositorio.getFavoritos(userId)
            if (response.isSuccessful) {
                val miRespuestaReseniaPersona = response.body()
                liveDataFavoritos.postValue(miRespuestaReseniaPersona)
            }
        }
    }

    fun getFavoritosAjenos(userId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repositorio.getFavoritos(userId)
            if (response.isSuccessful) {
                val miRespuestaReseniaPersona = response.body()
                liveDataFavoritosAjenos.postValue(miRespuestaReseniaPersona)
            }
        }
    }



    fun deletefavorito(favId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repositorio.deleteFavoritos(favId)
            if (response.isSuccessful)
                postId()?.let { getFavoritos(it.toDouble().toLong()) }
        }
    }

    fun deleteUsuario(userId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repositorio.deleteUsuario(userId)
            if (response.isSuccessful)
                postId()?.let { getFavoritos(it.toDouble().toLong()) }
        }
    }


    // modificar usuario
    fun actualizarUsuario(id: Int, usuario: UsuarioDTO?) {
        viewModelScope.launch {
            try {
                Log.e("PerfilVM", "Actualizando user con id: $id")

                val response = repositorio.actualizarUsuario(id, usuario)
                if (response.isSuccessful) {
                    usuarioActualizadoLiveData.postValue(response.body())
                } else {
                    Log.e("actualizarUsuario", "Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: IOException) {
                Log.e("actualizarUsuario", "Excepción de E/S: ${e.message}")
            } catch (e: Exception) {
                Log.e("actualizarUsuario", "Excepción: ${e.message}")
            }
        }
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