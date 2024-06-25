package com.example.bookie.data.model



/**
 * Modelo necesario
 *@author Lorena Villar
 * @version 27/5/2024
 * */
data class UsuarioDTO (
    var nombre: String,
    var username: String,
    var email: String,
    var ciudad: String,
    var provincia: String,
    var codigoPostal: Int,
    var foto : String,
    var bookieFavoritaId: Int

)