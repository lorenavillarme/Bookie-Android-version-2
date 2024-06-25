package com.example.bookie.data.network

import android.content.Context
import com.example.bookie.data.model.CrearLibro
import com.example.bookie.data.model.LibroMod
import com.example.bookie.data.model.LibroPrestado
import com.example.bookie.data.model.ReseniaPersona.CrearReseniaPersona
import com.example.bookie.data.model.RespuestaLibro
import com.example.bookie.data.model.UsuarioDTO
import com.example.bookie.data.model.favoritos.CrearFavorito
import com.example.bookie.data.model.idk.CrearChat
import com.example.bookie.data.model.idk.CrearMensaje
import com.example.bookie.utils.constants.Constants
import com.example.bookie.utils.interceptor.AuthInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
/**
 * Esta clase define funciones para añadir una capa de abstracción a los métodos provenientes del apiService
 *@author Iñigo Acosta
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
class ApiClient {

    private lateinit var apiService: ApiService

    fun getApiService(context: Context): ApiService {

        if (!::apiService.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClient(context))
                .build()

            apiService = retrofit.create(ApiService::class.java)
        }

        return apiService
    }

    private fun okhttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .build()
    }
}