package com.example.bookie.data.model

/**
 * Modelo que aporta la respuesta de autenticación del usuario al hacer login
 *@author  Inigo Acosta
 * @version 29/3/2024
 * */
data class AuthenticationResponse(
    var token: String,
    var username: String
)
