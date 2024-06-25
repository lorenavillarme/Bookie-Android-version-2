package com.example.bookie.data.model

import com.google.gson.annotations.SerializedName

/**
 * Modelo que aporta el booleano del esta prestado de un libro
 *@author  Liberty Tamayo
 * @version 2/6/2024
 * */

data class LibroPrestado (
    @SerializedName("prestado")
    val prestado: Boolean
)