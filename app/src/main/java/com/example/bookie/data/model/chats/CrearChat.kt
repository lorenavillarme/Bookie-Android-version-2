package com.example.bookie.data.model.idk


import com.google.gson.annotations.SerializedName
/**
 * Modelo que crea un chat con clases de UsuarioEmisor y usuarioReceptor usando sus ids
 *@author Lorena Villar
 * @version 30/4/2024
 * @see UsuarioEmisor
 * @see UsuarioReceptor
 * */
data class CrearChat(
    @SerializedName("usuarioEmisor")
    val usuarioEmisor: UsuarioEmisor,
    @SerializedName("usuarioReceptor")
    val usuarioReceptor: UsuarioReceptor
)