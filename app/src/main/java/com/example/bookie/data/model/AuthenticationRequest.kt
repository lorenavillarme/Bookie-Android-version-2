package com.example.bookie.data.model

/**
 * Modelo que aporta la autenticación del usuario al hacer login
 *@author  Inigo Acosta
 * @version 29/3/2024
 * */
data class AuthenticationRequest(
    var username: String,
    var password: String
)
