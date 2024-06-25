package com.example.bookie.data.model

import com.google.gson.annotations.SerializedName

/**
 * Modelo que aporta los datos necesarios para modificar un libro
 *@author  Liberty Tamayo, Lorena Villar
 * @version 1/6/2024
 * */

data class LibroMod(
    @SerializedName("libroId")
    val libroId: Int,

    @SerializedName("titulo")
    val titulo: String,
    @SerializedName("autor")
    val autor: String,
    @SerializedName("genero")
    val genero: String,
    @SerializedName("imagenBase64")
    val imagenBase64: String,
    @SerializedName("paginas")
    val paginas: Int,
    @SerializedName("prestado")
    val prestado: Boolean,
)
