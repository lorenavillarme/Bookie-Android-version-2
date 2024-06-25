package com.example.bookie.data.model.idk


import com.google.gson.annotations.SerializedName
/**
 * Modelo con id del chat
 *@author Lorena Villar
 * @version 30/4/2024
 * @see CrearMensaje
 * */

data class Chat(
    @SerializedName("id")
    val id: Int
)