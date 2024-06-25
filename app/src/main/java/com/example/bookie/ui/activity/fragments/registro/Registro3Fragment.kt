package com.example.bookie.ui.activity.fragments.registro

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.bookie.R
import com.example.bookie.data.model.AuthenticationResponse
import com.example.bookie.data.model.RegisterRequest
import com.example.bookie.data.network.ApiClient
import com.example.bookie.databinding.Registro3Binding
import com.example.bookie.ui.activity.LoginActivity
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import java.io.ByteArrayOutputStream


class Registro3Fragment : Fragment() {

    private lateinit var apiClient: ApiClient
    private var selectedImageUri: Uri? = null

    lateinit var alertDialog: AlertDialog
    private var _binding: Registro3Binding ? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = Registro3Binding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        apiClient = ApiClient()

        binding.btnEditar.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
        }

        binding.btnContinuar.setOnClickListener {
            crearUserAlert()
            Handler(Looper.getMainLooper()).postDelayed({
                register()
            }, 1000)
        }

    }

    private fun register(){
        val nombre = arguments?.getString("nombre")
        val email = arguments?.getString("email")
        val contrasenia = arguments?.getString("contrasenia")
        val provincia = arguments?.getString("provincia")
        val ciudad = arguments?.getString("ciudad")
        val codPostal = arguments?.getInt("codPostal")

        val imagenBase64 = selectedImageUri?.let { uri ->
            convertirImagenABase64(uri)
        }

        apiClient.getApiService(requireContext()).register(RegisterRequest("ROLE_USER", nombre, (binding.etUsername.text.toString().lowercase()), contrasenia, email, ciudad, provincia, codPostal, imagenBase64, false, ""))
            .enqueue(object : Callback<AuthenticationResponse>{
                override fun onResponse(
                    call: Call<AuthenticationResponse>,
                    response: Response<AuthenticationResponse>
                ) {
                    val loginResponse = response.body()

                    if (loginResponse != null) {
                        if(binding.etUsername.text.toString().isNotEmpty()){
                            val intent = Intent(this@Registro3Fragment.requireContext(), LoginActivity::class.java)
                            startActivity(intent)
                        }else{
                            binding.itUsername.error = "Campo vacío"
                        }
                    }else{
                        Toast.makeText(requireContext(), "Usuario ya registrado", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AuthenticationResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "Usuario ya registrado", Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun convertirImagenABase64(uri: Uri): String {
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val bytes = ByteArrayOutputStream()
        inputStream?.use { input ->
            input.copyTo(bytes)
        }
        return android.util.Base64.encodeToString(bytes.toByteArray(), android.util.Base64.DEFAULT)
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            binding.imgPerfil.setImageURI(selectedImageUri)
        }
    }

    private fun crearUserAlert() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog_usuario_creado, null)
        val builder = context?.let { AlertDialog.Builder(it, R.style.AlertDialogThemeSmall) }

        if (builder != null) {
            builder.setView(dialogView)
        }
        if (builder != null) {
            builder.setTitle("")
        }
        if (builder != null) {
            builder.setMessage("")
        }

        if (builder != null) {
            alertDialog = builder.create()
        }
        alertDialog.show()
    }
}