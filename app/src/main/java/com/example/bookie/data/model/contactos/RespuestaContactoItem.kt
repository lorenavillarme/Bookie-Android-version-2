package com.example.bookie.data.model.contactos


import com.google.gson.annotations.SerializedName
/**
 * Modelo con id del chat, el usuario emisor, receptor y lso username de ambos
 *@author Lorena Villar
 * @version 8/5/2024
 * */
data class RespuestaContactoItem(
    @SerializedName("chatId")
    val chatId: Int,
    @SerializedName("usuarioEmisorId")
    val usuarioEmisorId: Int,
    @SerializedName("usuarioReceptorId")
    val usuarioReceptorId: Int,
    @SerializedName("usuarioReceptorUsername")
    val usuarioReceptorUsername: String,
    @SerializedName("usuarioEmisorUsername")
    val usuarioEmisorUsername: String
)