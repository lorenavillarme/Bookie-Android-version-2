package com.example.bookie.data.model.favoritos

/**
 * Modelo que contiene la respuesta a guarar en favoritos
 *@author Lorena Villar
 * @version 16/5/2024
 * */

data class RespuestaFavorito(
    val titulo: String,
    val imagen: String,
    val autor: String,
    val numeroPaginas: Int?,
    val genero: String,
    val sinopsis: String,
    val editorial: String,
    val prestado: Boolean,
    val libroId: Int?,
    val libroId2: Long?,
    val idFavorito: Long?,
    val usuarioId: Int,
)

