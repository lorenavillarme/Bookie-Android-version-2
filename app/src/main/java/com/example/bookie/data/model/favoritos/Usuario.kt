package com.example.bookie.data.model.favoritos


import com.google.gson.annotations.SerializedName

/**
 * Modelo que contiene el id del usuario a guardar en favoritos
 *@author Lorena Villar
 * @version 16/5/2024
 * @see CrearFavorito
 * */

data class Usuario(
    @SerializedName("id")
    val id: Int
)