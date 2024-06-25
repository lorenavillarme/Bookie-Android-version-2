package com.example.bookie.data.model


/**
 * Modelo que aporta la respuesta del libro
 *@author Inigo Acosta, Liberty Tamayo y Lorena Villar
 * @version 15/5/2024
 * */

data class RespuestaLibro (
    var titulo: String,
    var autor: String,
    var numeroPaginas: Int,
    var genero: String,
    var foto: String,
    var sinopsis: String,
    var editorial: String,
    var usuario: String,
    var prestado : Boolean,
    var userId: Long,
    var libroId : Int
)