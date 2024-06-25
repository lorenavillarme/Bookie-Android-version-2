package com.example.bookie.data.model.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Modelo con todos los datos necesarios de un libro de la API Google Books
 *@author Lorena Villar
 * @version 12/4/2024
 * */

@Parcelize
data class Book(
    val id: String,
    val title: String,
    val author: String,
    val imageUrl: String,
    val genre: String,
    val editorial: String,
    val pages: Int,
    val sinopsis: String,
    val rating: Double
): Parcelable
