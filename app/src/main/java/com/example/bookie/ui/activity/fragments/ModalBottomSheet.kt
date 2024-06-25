package com.example.bookie.ui.activity.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.bookie.R
import com.example.bookie.databinding.BottomSheetBinding
import com.example.bookie.ui.activity.LoginActivity
import com.example.bookie.ui.activity.viewsmodels.perfilViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

/**
 * Clase para editar la información del perfil de un usuario
 * @author Lorena Villar
 * @version 28/5/2024
 * @see BottomSheetBinding
 * @see LoginActivity
 * @see perfilViewModel
 * @see BottomSheetBehavior
 * @see BottomSheetDialogFragment
 * */

class ModalBottomSheet : BottomSheetDialogFragment() {

    lateinit var alertDialog: AlertDialog
    private lateinit var binding: BottomSheetBinding
    private val myViewModel: perfilViewModel by activityViewModels()
    private lateinit var context: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCerrarSesion2.setOnClickListener {
            cerrarSesionDialog()
        }

        binding.btnBorrarCuenta.setOnClickListener {
            eliminarCuentaDialog()
        }

        binding.btnTerms.setOnClickListener {
            plegarBottomSheet()
            findNavController().navigate(R.id.action_usuarioFragment_to_terminosFragment)
        }

        binding.btnEditarPerfil.setOnClickListener {
            plegarBottomSheet()
            findNavController().navigate(R.id.action_usuarioFragment_to_editarPerfilFragment)
        }

    }

    private fun cerrarSesionDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog_cerrarsesion, null)
        val builder = requireContext().let { AlertDialog.Builder(it, R.style.AlertDialogTheme) }

        if (builder != null) {
            builder.setView(dialogView)
        }
        if (builder != null) {
            builder.setTitle("")
        }
        if (builder != null) {
            builder.setMessage("")
        }

        val positiveButton = dialogView.findViewById<ImageButton>(R.id.positiveButton)
        val negativeButton = dialogView.findViewById<ImageButton>(R.id.negativeButton)

        if (builder != null) {
            alertDialog = builder.create()
        }

        positiveButton.setOnClickListener {
            //borrar shared data
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

            //eliminar contra
            val sharedPreferencesContra = requireContext().getSharedPreferences("contra", Context.MODE_PRIVATE)
            val editorContra = sharedPreferencesContra.edit()
            editorContra.remove("contra")
            editorContra.apply()

            //salir de la app
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
            requireActivity().finishAffinity()
        }
        negativeButton.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }



    private fun eliminarCuentaDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog_borrar_cuenta, null)
        val builder = context.let { AlertDialog.Builder(it, R.style.AlertDialogTheme) }

        if (builder != null) {
            builder.setView(dialogView)
        }
        if (builder != null) {
            builder.setTitle("")
        }
        if (builder != null) {
            builder.setMessage("")
        }

        val positiveButton = dialogView.findViewById<ImageButton>(R.id.positiveButton)
        val negativeButton = dialogView.findViewById<ImageButton>(R.id.negativeButton)

        if (builder != null) {
            alertDialog = builder.create()
        }

        positiveButton.setOnClickListener {
            myViewModel.viewModelScope.launch {
                val idUser =  myViewModel.postId()
                if (idUser != null) {
                    myViewModel.deleteUsuario(idUser.toDouble().toLong())
                }
            }

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

            //eliminar contra
            val sharedPreferencesContra = requireContext().getSharedPreferences("contra", Context.MODE_PRIVATE)
            val editorContra = sharedPreferencesContra.edit()
            editorContra.remove("contra")
            editorContra.apply()


            alertDialog.dismiss()
            val dialogBorrada = cuentaBorradaDialog()
            Handler(Looper.getMainLooper()).postDelayed({
                dialogBorrada.dismiss()
                //salir de la app
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                requireActivity().finishAffinity()
            }, 1000)

        }
        negativeButton.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }


    private fun cuentaBorradaDialog() : AlertDialog{
        val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog_cuenta_borrada, null)
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

        return alertDialog
    }


    private fun plegarBottomSheet() {
        val bottomSheetBehavior = BottomSheetBehavior.from(requireView().parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

}

