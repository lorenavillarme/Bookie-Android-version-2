package com.example.bookie.data.model.idk


import com.google.gson.annotations.SerializedName

/**
 * Modelo de respuesta del json al crear un chat
 *@author Lorena Villar
 * @version 30/4/2024
 * */

data class RespuestaCrearChat(
    @SerializedName("chatId")
    val chatId: Int,
    @SerializedName("usuarioEmisorId")
    val usuarioEmisorId: Int,
    @SerializedName("usuarioReceptorId")
    val usuarioReceptorId: Int,
    @SerializedName("usuarioReceptorUsername")
    val usuarioReceptorUsername: Any
)