package com.example.bookie.data.model.idk


import com.google.gson.annotations.SerializedName

/**
 * Modelo de usuario usado a la hora de crear un mensaje y un libro
 *@author Lorena Villar
 * @version 30/4/2024
 * */

data class Usuario(
    @SerializedName("id")
    val id: Int
)