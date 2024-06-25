package com.example.bookie.data.network

import com.example.bookie.data.model.AuthenticationRequest
import com.example.bookie.data.model.AuthenticationResponse
import com.example.bookie.data.model.CrearLibro
import com.example.bookie.data.model.api.BookSearchResponse
import com.example.bookie.data.model.Libro
import com.example.bookie.data.model.LibroMod
import com.example.bookie.data.model.LibroPrestado
import com.example.bookie.data.model.RegisterRequest
import com.example.bookie.data.model.ReseniaPersona.CrearReseniaPersona
import com.example.bookie.data.model.ReseniaPersona.RespuestaReseniaPersona
import com.example.bookie.data.model.RespuestaLibro
import com.example.bookie.data.model.UsuarioDTO
import com.example.bookie.data.model.contactos.RespuestaContactoItem
import com.example.bookie.data.model.favoritos.CrearFavorito
import com.example.bookie.data.model.favoritos.RespuestaFavorito
import com.example.bookie.data.model.idk.CrearChat
import com.example.bookie.data.model.idk.CrearMensaje
import com.example.bookie.data.model.idk.Mensaje
import com.example.bookie.data.model.idk.RespuestaCrearChat
import com.example.bookie.data.model.idk.RespuestaCrearMensaje
import com.example.bookie.utils.constants.Constants
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
/**
 * Esta interfaz define las peticiones a la API del Backend y Google Books
 *@author Lorena Villar, Liberty Tamayo, Iñigo Acosta
 * @version 29/5/2024
 * COMUNES
 * @see Repositorio
 * @see Constants
 * LOGIN Y REGISTRO
 * @see RegisterRequest
 * @see AuthenticationRequest
 * @see AuthenticationResponse
 * LIBROS
 * @see RespuestaLibro
 * @see LibroPrestado
 * @see LibroMod
 * @see CrearLibro
 * GOOGLE API BOOKS
 * @see BookSearchResponse
 * CHAT
 * @see RespuestaCrearMensaje
 * @see RespuestaCrearChat
 * @see Mensaje
 * @see CrearChat
 * @see CrearMensaje
 * @see RespuestaContactoItem
 * RESENIAS
 * @see RespuestaReseniaPersona
 * @see CrearReseniaPersona
 * FAVORITOS
 * @see CrearFavorito
 * @see RespuestaFavorito
 * USUARIO
 * @see UsuarioDTO
 *
 * */
interface ApiService {

    @POST(Constants.LOGIN_URL)
    fun login(@Body request: AuthenticationRequest): Call<AuthenticationResponse>

    @POST(Constants.REGISTER_URL)
    fun register(@Body request: RegisterRequest): Call<AuthenticationResponse>

    @GET(Constants.LIBROS_URL)
    suspend fun fetchLibros(): Response<List<RespuestaLibro>>

    // para libros propios
    @GET(Constants.LIBROS_PERFIL_URL)
    suspend fun fetchLibrosPropios(@Path("id") userId: Long): Response<List<RespuestaLibro>>

    @PUT(Constants.LIBRO_PRESTADO)
    suspend fun esPrestado( @Path("id") id: Int, @Body libro: LibroPrestado): Response<RespuestaLibro>


    @POST(Constants.LIBROS_URL)
    suspend fun subirLibro(@Body libro: CrearLibro): Response<RespuestaLibro>

    @DELETE(Constants.ELIMINAR_LIBRO)
    suspend fun deleteLibro(@Path("id") libroId: Int)

    @PUT(Constants.MODIFICAR_LIBRO)
    suspend fun actualizarLibro(@Path("id") libroId: Int, @Body libro: LibroMod?): Response<RespuestaLibro>


    //api
    @GET("volumes")
    suspend fun searchBooksByTitle(
        @Query("q") title: String
    ): Response<BookSearchResponse>


    //chat
    @POST(Constants.MENSAJE_URL)
    suspend fun PostMensaje(
        @Body mensaje: CrearMensaje
    ) : Response<RespuestaCrearMensaje>

    @POST(Constants.CHAT_URL)
    suspend fun PostChat(
        @Body chat: CrearChat
    ): Response<RespuestaCrearChat>

    @GET(Constants.CHAT_URL + "/{id}")
    suspend fun GetChat(
        @Path("id") id : Long
    ): Response<List<Mensaje>>


    @GET(Constants.CONTACTOS_URL + "/{id}")
    suspend fun GetContacto(
        @Path("id") id : Long
    ) : Response<List<RespuestaContactoItem>>


    //sacar id del user
    @POST(Constants.CREDENTIALS_URL)
    suspend fun GetIdXToken(): Response<Map<String,Any>>


    //resenia persona
    @POST(Constants.RESENIA_URL)
    suspend fun PostReseniaPersona(
        @Body resenia : CrearReseniaPersona
    ): Response<RespuestaReseniaPersona>


    @GET(Constants.RESENIA_URL + "/usuario/{id}")
    suspend fun GetReseniasPersona(
        @Path("id") id : Long
    ): Response<List<RespuestaReseniaPersona>>

    //favoritos
    @POST(Constants.FAVORITOS_URL)
    suspend fun PostLibroFavorito(
        @Body favorito: CrearFavorito
    ): Response<RespuestaFavorito>

    @GET(Constants.FAVORITOS_URL + "/usuario/{id}")
    suspend fun GetFavoritos(
        @Path("id") id: Long
    ): Response<List<RespuestaFavorito>>


    @DELETE(Constants.FAVORITOS_URL + "/{id}")
    suspend fun DeleteFav(
        @Path("id") favId: Long
    ): Response<Unit>


    //usuarios
    @DELETE(Constants.USUARIOS_URL + "/{id}")
    suspend fun DeleteUser(
        @Path("id") idIser: Long
    ): Response<Unit>


    @GET(Constants.USUARIOS_URL+ "/{id}")
    suspend fun GetUser(
        @Path("id") idUser: Long
    ): Response<UsuarioDTO>


    @PUT(Constants.USUARIOS_URL+ "/{id}")
    suspend fun actualizarUsuario(
        @Path("id") usuarioId: Int,
        @Body usuario: UsuarioDTO?
    ): Response<UsuarioDTO>


}