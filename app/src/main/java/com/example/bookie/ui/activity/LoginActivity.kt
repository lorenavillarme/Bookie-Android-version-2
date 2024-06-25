package com.example.bookie.ui.activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.bookie.R
import com.example.bookie.data.model.AuthenticationRequest
import com.example.bookie.data.model.AuthenticationResponse
import com.example.bookie.data.network.ApiClient
import com.example.bookie.databinding.LoginBinding
import com.example.bookie.utils.sessionmanager.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient
    private lateinit var binding: LoginBinding
    lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiClient = ApiClient()
        sessionManager = SessionManager(this)


        binding.imageView4.setOnClickListener {
            login()
        }

        //recordar datos
        val pref = getSharedPreferences("contra", Context.MODE_PRIVATE)
        val contra = pref.getString("contra","")
        binding.etContrasena.setText(contra)
        /////
        val prefUsername = getSharedPreferences("username", Context.MODE_PRIVATE)
        val username = prefUsername.getString("username","")
        binding.etEmail.setText(username)



        binding.recordarContra.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                guardarContra()
            } else {
                eliminarContraGuardada()
            }
        }

        binding.button3.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            //showConfirmationDialog()
        }

    }

    private fun guardarContra() {
        val pref = getSharedPreferences("contra",Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("contra",binding.etContrasena.text.toString())
        editor.apply()
    }

    private fun guardarUsername() {
        val pref = getSharedPreferences("username",Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("username",binding.etEmail.text.toString())
        editor.apply()
    }

    private fun eliminarContraGuardada() {
        val sharedPreferences = getSharedPreferences("contra", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.remove("contra")
        editor.apply()

    }



    private fun showConfirmationDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.custom_alert_dialog, null)
        val builder = AlertDialog.Builder(this, R.style.AlertDialogTheme)

        builder.setView(dialogView)
        builder.setTitle("")
        builder.setMessage("")

        val positiveButton = dialogView.findViewById<ImageButton>(R.id.positiveButton)
        val negativeButton = dialogView.findViewById<ImageButton>(R.id.negativeButton)

        alertDialog = builder.create()

        positiveButton.setOnClickListener {
            alertDialog.dismiss()
        }
        negativeButton.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }



    private fun login(){
        apiClient.getApiService(this).login(AuthenticationRequest(username = binding.etEmail.text.toString(), password = binding.etContrasena.text.toString()))
            .enqueue(object : Callback<AuthenticationResponse> {

                override fun onFailure(call: Call<AuthenticationResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Servidor no disponible", Toast.LENGTH_SHORT).show()
                }



                override fun onResponse(
                    call: Call<AuthenticationResponse>,
                    response: Response<AuthenticationResponse>
                ) {
                    val loginResponse = response.body()

                    if (loginResponse != null) {
                        sessionManager.saveAuthToken(loginResponse.token)
                        Log.e("token", loginResponse.token)
                        sessionManager.saveUsername(loginResponse.username)
                        guardarUsername()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        binding.tiEmail.error = "Usuario incorrecto"
                    }
                }

            })
    }


}