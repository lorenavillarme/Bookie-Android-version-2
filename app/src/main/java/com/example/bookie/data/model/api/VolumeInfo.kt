package com.example.bookie.data.model.api

/**
 * Modelo procedente de BookItem con los datos registrados de un libro en la API (tiene muchos más campos de los que se mencionan en este modelo)
 *@author Lorena Villar
 * @version 12/4/2024
 * @see BookItem
 * */
data class VolumeInfo(
    val title: String,
    val authors: List<String>?,
    val imageLinks: ImageLinks?
)
