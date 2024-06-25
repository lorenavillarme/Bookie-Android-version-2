package com.example.bookie.data.model.favoritos


/**
 * Modelo necesario para crear favoritos, pasando los datos relevantes de un libro
 *@author Lorena Villar
 * @version 16/5/2024
 * @see Libro
 * @see Usuario
 * */

data class CrearFavorito(
    val titulo: String,
    val autor: String,
    val numeroPaginas: Int?,
    val genero: String,
    val sinopsis: String,
    val editorial: String,
    val prestado: Boolean,
    val libroId: Int?,
    val imagen: String,
    val libro: Libro,
    val usuario: Usuario
)