package com.example.bookie.data.model.idk


import com.google.gson.annotations.SerializedName

/**
 * Modelo de id del usuario receptor a la hora de crear un chat
 *@author Lorena Villar
 * @version 30/4/2024
 * @see CrearChat
 * */

data class UsuarioReceptor(
    @SerializedName("id")
    val id: Int
)