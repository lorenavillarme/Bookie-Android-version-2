package com.example.bookie.utils.sessionmanager

import android.content.Context
import android.content.SharedPreferences
import com.example.bookie.R

class SessionManager(context: Context) {

    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    private var prefs2: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
        const val USERNAME = "username"
    }

    /**
     * Función para guardar auth token
     */
    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    /**
     * Función para recuperar auth token
     */
    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    /**
     * Función para guardar username
     */
    fun saveUsername(username: String) {
        val editor = prefs2.edit()
        editor.putString(USERNAME, username)
        editor.apply()
    }

    /**
     * Función para recuperar username
     */
    fun fetchUsername(): String? {
        return prefs2.getString(USERNAME, null)
    }
}