package com.example.bookie.ui.activity.fragments.perfil

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.bookie.R
import com.example.bookie.data.model.UsuarioDTO
import com.example.bookie.databinding.FragmentEditarPerfilBinding
import com.example.bookie.ui.activity.LoginActivity
import com.example.bookie.ui.activity.fragments.libro.SubirLibroFragment
import com.example.bookie.ui.activity.viewsmodels.ChatViewModel
import com.example.bookie.ui.activity.viewsmodels.perfilViewModel
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

/**
 * Clase para editar la información del perfil de un usuario
 * @author Lorena Villar
 * @version 28/5/2024
 * @see UsuarioDTO
 * @see FragmentEditarPerfilBinding
 * @see SubirLibroFragment
 * @see ChatViewModel
 * @see perfilViewModel
 * */

class EditarPerfilFragment : Fragment() {

    private lateinit var binding: FragmentEditarPerfilBinding
    private val myViewModel: perfilViewModel by activityViewModels()
    private val myChatViewModel: ChatViewModel by activityViewModels()
    private var selectedImageUri: Uri? = null
    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditarPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //cargar datos antiguos al update
        myViewModel.viewModelScope.launch {
            val decodedBitmap = myViewModel.fotoUsuario()?.let { base64ToBitmap(it) }
            binding.imgPerfil.setImageBitmap(decodedBitmap)

            val idUser = myChatViewModel.postId()
            if (idUser != null) {
                myChatViewModel.getUsuarios(idUser.toDouble().toLong()).observe(viewLifecycleOwner){ user ->
                    if (user != null) {
                        val usernamePasado = user.username
                        binding.etUsername.setText(user.username)
                        binding.etCiudad.setText(user.ciudad)
                        binding.etProvinciadit.setText(user.provincia)
                        binding.etCpedit.setText(user.codigoPostal.toString())

                        binding.btnEditar.setOnClickListener {
                            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                            startActivityForResult(galleryIntent,PICK_IMAGE_REQUEST
                            )
                        }

                        binding.btnContinuar.setOnClickListener {

                            //recoger datos nuevos
                            var username = binding.etUsername.text.toString()
                            var ciudad = binding.etCiudad.text.toString()
                            var provincia = binding.etProvinciadit.text.toString()
                            var cp = binding.etCpedit.text.toString().toInt()


                            val imagenBase64 = selectedImageUri?.let { uri ->
                                convertirImagenABase64(uri)
                            }

                            var fotoDefinitiva =user.foto
                            if (imagenBase64 != null){
                                fotoDefinitiva = imagenBase64
                            }

                            val usuarioCambiado =  UsuarioDTO(
                                user.nombre,
                                username,
                                user.email,
                                ciudad,
                                provincia,
                                cp,
                                fotoDefinitiva,
                                user.bookieFavoritaId
                            )
                            myViewModel.actualizarUsuario(idUser.toDouble().toInt(), usuarioCambiado)
                            showUpdateUserDialog()
                            if (username != usernamePasado) {

                                //eliminar token
                                val sharedPreferencesToken = requireContext().getSharedPreferences("Bookie", Context.MODE_PRIVATE)
                                val editorToken = sharedPreferencesToken.edit()
                                editorToken.remove("user_token")
                                editorToken.apply()

                                //eliminar username
                                val sharedPreferencesUsername = requireContext().getSharedPreferences("username", Context.MODE_PRIVATE)
                                val editorUsername = sharedPreferencesUsername.edit()
                                editorUsername.remove("username")
                                editorUsername.apply()
                                //salir de la app
                                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                                requireActivity().finishAffinity()
                            } else {
                                findNavController().navigate(R.id.action_editarPerfilFragment_to_usuarioFragment)
                            }

                        }
                    }
                }
            }
        }

        binding.backE.setOnClickListener {
            findNavController().navigateUp()
        }

    }


    private fun showUpdateUserDialog(): AlertDialog {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.alert_usuario_editado, null)
        val builder = AlertDialog.Builder(requireContext(), R.style.AlertDialogThemeSmall)

        builder.setView(dialogView)
        builder.setTitle("")
        builder.setMessage("")

        val dialog = builder.create()
        dialog.show()
        return dialog
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SubirLibroFragment.PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            binding.imgPerfil.setImageURI(selectedImageUri)
        }
    }

    //función de conversión de imágenes
    private fun base64ToBitmap(base64Str: String): Bitmap {
        val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

    private fun convertirImagenABase64(uri: Uri): String {
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val bytes = ByteArrayOutputStream()
        inputStream?.use { input ->
            input.copyTo(bytes)
        }
        return Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT)
    }

}