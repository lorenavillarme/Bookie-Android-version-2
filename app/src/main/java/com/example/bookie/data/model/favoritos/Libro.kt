package com.example.bookie.data.model.favoritos


import com.google.gson.annotations.SerializedName
/**
 * Modelo que contiene el id del libro a guarar en favoritos
 *@author Lorena Villar
 * @version 16/5/2024
 * @see CrearFavorito
 * */

data class Libro(
    @SerializedName("idLibro")
    val idLibro: Int
)