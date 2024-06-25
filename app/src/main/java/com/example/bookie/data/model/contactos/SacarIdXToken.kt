package com.example.bookie.data.model.contactos


import com.google.gson.annotations.SerializedName
/**
 * Modelo que obtiene id y username del usuario que utiliza la aplicación
 *@author Lorena Villar
 * @version 8/5/2024
 * */
data class SacarIdXToken(
    @SerializedName("id")
    val id: Int,
    @SerializedName("username")
    val username: String
)