package com.example.bookie.data.model

import com.google.gson.annotations.SerializedName

/**
 * Modelo usado para las busquedas de libros (no se aplica al buscador de google)
 *@author Lorena Villar, Liberty Tamayo
 * @version 8/5/2024
 * */

data class Libro(
    @SerializedName("autor")
    val autor: String,

    @SerializedName("genero")
    val genero: String,
    @SerializedName("editorial")
    val editorial: String,
    @SerializedName("numeroPaginas")
    val numeroPaginas: Int,

    @SerializedName("titulo")
    val titulo: String,
    @SerializedName("userId")
    val userId: Long,
    @SerializedName("prestado")
    val prestado: Boolean,

)