package com.example.bookie.data.model.ReseniaPersona


import com.google.gson.annotations.SerializedName

/**
 * Modelo del usuario que recibe la resenia
 *@author Lorena Villar
 * @version 15/5/2024
 * */
data class UsuarioPuntuador(
    @SerializedName("id")
    val id: Int
)