package com.example.bookie.data.model

/**
 * Modelo necesario para el registro del usuario
 *@author Inigo Acosta
 * @version 5/5/2024
 * */
data class RegisterRequest(
    var rol: String?,
    var nombre: String?,
    var username: String?,
    var password: String?,
    var email: String?,
    var ciudad: String?,
    var provincia: String?,
    var codigoPostal: Int?,
    var foto: String?,
    var reportado: Boolean?,
    var token: String?
)
