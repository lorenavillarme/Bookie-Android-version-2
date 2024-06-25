package com.example.bookie.data.model.api

import com.example.bookie.data.model.api.VolumeInfo

/**
 * Modelo devuelto en un array de items por BookSearchResponse, está creada por un identificador único y VolumeInfo, siendo este último un array con todos los datos registrados del libro en la API
 *@author Lorena Villar
 * @version 12/4/2024
 * @see BookSearchResponse
 * @see VolumeInfo
 * */
data class BookItem(
    val id: String,
    val volumeInfo: VolumeInfo
)
