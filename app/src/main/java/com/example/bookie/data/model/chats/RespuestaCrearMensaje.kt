package com.example.bookie.data.model.idk


import com.google.gson.annotations.SerializedName

/**
 * Modelo de respuesta del json al crear un mensaje
 *@author Lorena Villar
 * @version 30/4/2024
 * */

data class RespuestaCrearMensaje(
    @SerializedName("texto")
    val texto: String,
    @SerializedName("userId")
    val userId: Int
)