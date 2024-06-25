package com.example.bookie.data.model.ReseniaPersona


import com.google.gson.annotations.SerializedName

/**
 * Modelo de respuesta para crear la resenia a un usuario
 *@author Lorena Villar
 * @version 15/5/2024
 * */

data class RespuestaReseniaPersona(
    @SerializedName("comentario")
    val comentario: String,
    @SerializedName("fechaReseniaPersona")
    val fechaReseniaPersona: String,
    @SerializedName("puntuacion")
    val puntuacion: Int,
    @SerializedName("username")
    val username: String
)