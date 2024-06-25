package com.example.bookie.utils.constants



/**
 * Este objeto define todos los endPoints de la API del Backend
 *@author Lorena Villar, Liberty Tamayo, Iñigo Acosta
 * @version 2/6/2024
 *
 * */
object Constants {
    const val BASE_URL = "https://bookie.escuelaestech.com.es/api/"

    //const val BASE_URL = "http://192.168.1.130:8080/api/" //ip local

    const val LOGIN_URL = "auth/login"
    const val REGISTER_URL = "auth/register"

    // libro
    const val LIBROS_URL = "libro"
    const val ELIMINAR_LIBRO = "libro/{id}"
    const val MODIFICAR_LIBRO = "libro/{id}"
    const val LIBROS_PERFIL_URL = "libro/usuario/{id}"
    // para marcar un libro como prestado/no prestado
    const val LIBRO_PRESTADO = "libro/prestado/{id}"

    //chat
    const val MENSAJE_URL = "mensaje"
    const val CHAT_URL = "chats"

    const val CREDENTIALS_URL = "credentials/get-user-from-token"
    const val CONTACTOS_URL = "chats/usuario"

    //resenias persona
    const val RESENIA_URL = "reseniapersona"

    //favoritos
    const val FAVORITOS_URL  = "favoritos"

    //usuarios
    const val USUARIOS_URL = "usuario"
}