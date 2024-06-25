package com.example.bookie.ui.activity.viewsmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bookie.data.model.CrearLibro
import com.example.bookie.data.model.LibroMod
import com.example.bookie.data.model.LibroPrestado
import com.example.bookie.data.model.RespuestaLibro
import com.example.bookie.data.model.idk.Usuario
import com.example.bookie.data.repository.Repositorio
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * ViewModel para la gestión de libros en la aplicación.
 * @property context Contexto de la aplicación.
 * @author Lorena Villar, Liberty Tamayo
 * @version 4/4/2024
 * @see CrearLibro
 * @see LibroMod
 * @see LibroPrestado
 * @see RespuestaLibro
 * @see Usuario
 * @see Repositorio
 *
 * */

class LibrosViewModel(val context: Context) : ViewModel() {

    private val repositorio = Repositorio(context)


    // LiveDatas para la gestión de libros
    val libroLiveData = MutableLiveData<List<RespuestaLibro>?>()
    val libroAjenoLiveData = MutableLiveData<List<RespuestaLibro>?>()
    val libroSelected = MutableLiveData<RespuestaLibro>()
    val libroSelectedAjeno = MutableLiveData<RespuestaLibro>()
    val libroActualizadoLiveData = MutableLiveData<RespuestaLibro?>()
    val liveDataCrearLibro = MutableLiveData<RespuestaLibro>()
    val liveDataLibrosTotales  = MutableLiveData<List<RespuestaLibro>?>()


    /**
     * Obtiene la lista de libros desde la base de datos
     */
    fun getLibro() {
        var ejecutada = false

        viewModelScope.launch {
            val usuarioId = postId()
            usuarioId?.let { id ->
                try {
                    val response = repositorio.getLibro()
                    ejecutada = true

                    if (response.isSuccessful) {
                        val miRespuesta = response.body()
                        val lista = miRespuesta
                        libroLiveData.postValue(lista)
                    } else {
                        Log.e("getLibro", "Error: ${response.code()} - ${response.message()}")
                    }
                } catch (e: IOException) {
                    Log.e("getLibro", "Excepción de E/S: ${e.message}")
                } catch (e: Exception) {
                    Log.e("getLibro", "Excepción: ${e.message}")
                }
            } ?: Log.e("LibrosViewModel", "ID de usuario no obtenida")
        }
    }

    /**
     * Obtiene la lista de libros propios del usuario
     *
     * @param userId ID del usuario
     * @return MutableLiveData con la lista de libros propios
     */
    fun getLibroPropio(userId: Long) : MutableLiveData<List<RespuestaLibro>?> {
        val libroPropioLiveData = MutableLiveData<List<RespuestaLibro>?>()
        viewModelScope.launch {
            try {
                val response = repositorio.getLibroPropio(userId)

                if (response.isSuccessful) {
                    val miRespuesta = response.body()
                    libroPropioLiveData.postValue(miRespuesta)
                } else {
                    Log.e("getLibroPropio", "Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: IOException) {
                Log.e("getLibroPropio", "Excepción de E/S: ${e.message}")
            } catch (e: Exception) {
                Log.e("getLibroPropio", "Excepción: ${e.message}")
            }
        }
        return libroPropioLiveData
    }

    /**
     * Obtiene la lista de libros ajenos del usuario.
     *
     * @param userId ID del usuario.
     */
    fun getLibroAjeno(userId: Long) {
        viewModelScope.launch {
            try {
                val response = repositorio.getLibroPropio(userId)

                if (response.isSuccessful) {
                    val miRespuesta = response.body()
                    libroAjenoLiveData.postValue(miRespuesta)
                    liveDataLibrosTotales.postValue(miRespuesta)
                } else {
                    Log.e("getLibroPropio", "Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: IOException) {
                Log.e("getLibroPropio", "Excepción de E/S: ${e.message}")
            } catch (e: Exception) {
                Log.e("getLibroPropio", "Excepción: ${e.message}")
            }
        }
    }


    // modificar libro
    fun actualizarLibro(id: Int, libro: LibroMod?) {
        viewModelScope.launch {
            try {
                Log.e("LibrosViewModel", "Actualizando libro con id: $id y datos: $libro")

                val response = repositorio.actualizarLibro(id, libro)
                if (response.isSuccessful) {
                    libroActualizadoLiveData.postValue(response.body())
                } else {
                    Log.e("actualizarLibro", "Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: IOException) {
                Log.e("actualizarLibro", "Excepción de E/S: ${e.message}")
            } catch (e: Exception) {
                Log.e("actualizarLibro", "Excepción: ${e.message}")
            }
        }
    }



    /**
     * Marca un libro como prestado o no prestado
     *
     * @param id ID del libro a marcar como prestado
     * @param libro Datos del libro prestado
     */
    fun actualizarLibroPrestado(id: Int, libro: LibroPrestado) {
        viewModelScope.launch {
            try {
                val response = repositorio.marcarPrestado(id, libro)
                if (response.isSuccessful) {
                    libroActualizadoLiveData.postValue(response.body())
                } else {
                    Log.e("actualizarLibroPrestado", "Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: IOException) {
                Log.e("actualizarLibroPrestado", "Excepción de E/S: ${e.message}")
            } catch (e: Exception) {
                Log.e("actualizarLibroPrestado", "Excepción: ${e.message}")
            }
        }
    }


    /**
     * Sube un nuevo libro al repositorio.
     *
     * @param titulo Título del libro.
     * @param autor Autor del libro.
     * @param numeroPaginas Número de páginas del libro.
     * @param genero Género del libro.
     * @param foto URL de la foto del libro.
     * @param sinopsis Sinopsis del libro.
     * @param editorial Editorial del libro.
     * @param prestado Indica si el libro está prestado.
     * @param idUsuario ID del usuario que sube el libro.
     */
    fun subirLibro(
        titulo: String,
        autor: String,
        numeroPaginas: Int,
        genero: String,
        foto: String,
        sinopsis: String,
        editorial: String,
        prestado: Boolean,
        idUsuario: Int?

    ) {
        val usuario = idUsuario?.let { Usuario(it) }

        val crearLibro = usuario?.let {
            CrearLibro(titulo,autor,numeroPaginas,genero,foto,sinopsis,editorial,prestado,
                it
            )
        }

        viewModelScope.launch {
            val response = crearLibro?.let { repositorio.subirLibro(it) }

            if (response != null) {
                if (response.isSuccessful){
                    liveDataCrearLibro.postValue(response.body())
                }
            }
        }

    }

    /**
     * Elimina un libro del repositorio.
     *
     * @param libroId ID del libro a eliminar.
     */
    fun deleteBook(libroId: Int) {
        viewModelScope.launch {
            try {
                repositorio.deleteLibro(libroId)
            } catch (e: IOException) {
                Log.e("deleteBook", "Excepción de E/S: ${e.message}")
            } catch (e: Exception) {
                Log.e("deleteBook", "Excepción: ${e.message}")
            }
        }
    }


    /**
     * Obtiene el ID del usuario actual.
     *
     * @return ID del usuario como String.
     */
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