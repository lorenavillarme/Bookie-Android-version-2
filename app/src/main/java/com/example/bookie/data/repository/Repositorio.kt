package com.example.bookie.data.repository

import android.content.Context
import com.example.bookie.data.model.AuthenticationRequest
import com.example.bookie.data.model.AuthenticationResponse
import com.example.bookie.data.model.CrearLibro
import com.example.bookie.data.model.LibroMod
import com.example.bookie.data.model.LibroPrestado
import com.example.bookie.data.model.RegisterRequest
import com.example.bookie.data.model.ReseniaPersona.CrearReseniaPersona
import com.example.bookie.data.model.ReseniaPersona.RespuestaReseniaPersona
import com.example.bookie.data.model.RespuestaLibro
import com.example.bookie.data.model.UsuarioDTO
import com.example.bookie.data.model.api.BookSearchResponse
import com.example.bookie.data.model.contactos.RespuestaContactoItem
import com.example.bookie.data.model.favoritos.CrearFavorito
import com.example.bookie.data.model.favoritos.RespuestaFavorito
import com.example.bookie.data.model.idk.CrearChat
import com.example.bookie.data.model.idk.CrearMensaje
import com.example.bookie.data.model.idk.RespuestaCrearChat
import com.example.bookie.data.model.idk.RespuestaCrearMensaje
import com.example.bookie.data.network.ApiClient
import retrofit2.Response

/**
 * Esta clase define funciones para añadir una capa de abstracción a los métodos provenientes del apiService
 *@author Lorena Villar, Liberty Tamayo, Iñigo Acosta
 * @version 2/6/2024
 * @see ApiClient
 * LIBROS
 * @see RespuestaLibro
 * @see LibroPrestado
 * @see LibroMod
 * @see CrearLibro
 * CHAT
 * @see CrearMensaje
 * @see CrearChat
 * RESENIAS
 * @see CrearReseniaPersona
 * FAVORITOS
 * @see CrearFavorito
 * USUARIO
 * @see UsuarioDTO
 *
 * */

class Repositorio(val context: Context) {

    private val apiClient = ApiClient()

    val apiService = apiClient.getApiService(context)

    // funciones para libro
    suspend fun getLibro() = apiService.fetchLibros()
    suspend fun getLibroPropio(userId: Long): Response<List<RespuestaLibro>> {
        return apiService.fetchLibrosPropios(userId)
    }

    suspend fun marcarPrestado(id: Int, libro: LibroPrestado): Response<RespuestaLibro> {
        return apiService.esPrestado(id,libro)
    }

    suspend fun subirLibro(lib: CrearLibro) = apiService.subirLibro(lib)

    suspend fun deleteLibro(libroId: Int) = apiService.deleteLibro(libroId)

    suspend fun actualizarLibro(libroId: Int, libro: LibroMod?): Response<RespuestaLibro> {
        return apiService.actualizarLibro(libroId, libro)
    }
    //chat
    suspend fun postMensaje(mensaje : CrearMensaje) = apiService.PostMensaje(mensaje)
    suspend fun getChat(chatId : Long) = apiService.GetChat(chatId)
    suspend fun postChat(chat: CrearChat) = apiService.PostChat(chat)

    //sacar id
    suspend fun PostId() = apiService.GetIdXToken()

    //sacar nombre del usuario
    suspend fun nombreUsuario() = apiService.GetIdXToken()

    //sacar foto del usuario
    suspend fun fotoUsuario() = apiService.GetIdXToken()

    //contactos
    suspend fun GetContactos(userId : Long) = apiService.GetContacto(userId)

    //resenias persona
    suspend fun postReseniaPersona(resenia: CrearReseniaPersona) = apiService.PostReseniaPersona(resenia)

    suspend fun getReseniasPersona(userId: Long) = apiService.GetReseniasPersona(userId)


    //favoritos
    suspend fun postFavorito(favorito: CrearFavorito) = apiService.PostLibroFavorito(favorito)

    suspend fun getFavoritos(userId: Long) = apiService.GetFavoritos(userId)

    suspend fun deleteFavoritos(favId: Long) = apiService.DeleteFav(favId)


    //usuarios
    suspend fun deleteUsuario(userId: Long) = apiService.DeleteUser(userId)

    suspend fun getUsuario(userId: Long) = apiService.GetUser(userId)

    suspend fun actualizarUsuario(usuarioId: Int, usuario: UsuarioDTO?): Response<UsuarioDTO> {
        return apiService.actualizarUsuario(usuarioId, usuario)
    }
}