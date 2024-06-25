package com.example.bookie.data.model

import com.example.bookie.data.model.idk.Usuario

/**
 * Modelo necesario para crear un libro
 *@author  Inigo Acosta
 * @version 10/5/2024
 * */

data class CrearLibro(
    var titulo: String,
    var autor: String,
    var numeroPaginas: Int,
    var genero: String,
    var foto: String,
    var sinopsis: String,
    var editorial: String,
    var prestado: Boolean,
    var usuario: Usuario
)
