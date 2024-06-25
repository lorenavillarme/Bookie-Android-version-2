package com.example.bookie.data.model.idk


import com.google.gson.annotations.SerializedName

/**
 * Modelo que crea un mensaje a partir de la clase Chat, el texto del mensaje, la fecha y un user(su id)
 *@author Lorena Villar
 * @version 30/4/2024
 * @see Usuario
 * @see Chat
 * */

data class CrearMensaje(
    @SerializedName("chat")
    val chat: Chat,
    @SerializedName("texto")
    val texto: String,
    @SerializedName("usuario")
    val usuario: Usuario,
    val fechaMensaje: String
)