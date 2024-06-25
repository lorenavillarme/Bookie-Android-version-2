package com.example.bookie.data.model.ReseniaPersona


import com.google.gson.annotations.SerializedName

/**
 * Modelo necesario para crear la resenia a un usuario
 *@author Lorena Villar
 * @version 15/5/2024
 * @see UsuarioPuntuado
 * @see UsuarioPuntuador
 * */

data class CrearReseniaPersona(
    @SerializedName("comentario")
    val comentario: String,
    @SerializedName("fechaReseniaPersona")
    val fechaReseniaPersona: String,
    @SerializedName("puntuacion")
    val puntuacion: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("usuarioPuntuado")
    val usuarioPuntuado: UsuarioPuntuado,
    @SerializedName("usuarioPuntuador")
    val usuarioPuntuador: UsuarioPuntuador
)