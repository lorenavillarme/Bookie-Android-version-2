package com.example.bookie.data.model.idk


import com.google.gson.annotations.SerializedName

/**
 * Modelo de mensaje a partir del texto, la id del user y la fecha de envío
 *@author Lorena Villar
 * @version 30/4/2024
 * */


data class
Mensaje(
    @SerializedName("message")
    val message: String,
    @SerializedName("user")
    val user: Int,
    val fecha: String?
)