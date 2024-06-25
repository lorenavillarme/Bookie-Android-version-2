package com.example.bookie.ui.activity.fragments.resenias

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.bookie.R
import com.example.bookie.data.model.ReseniaPersona.CrearReseniaPersona
import com.example.bookie.data.model.ReseniaPersona.RespuestaReseniaPersona
import com.example.bookie.data.model.ReseniaPersona.UsuarioPuntuado
import com.example.bookie.data.model.ReseniaPersona.UsuarioPuntuador
import com.example.bookie.databinding.AddReseniaUserBinding
import com.example.bookie.ui.activity.viewsmodels.ChatViewModel
import com.example.bookie.ui.activity.viewsmodels.SharedViewModel
import com.example.bookie.ui.activity.viewsmodels.perfilViewModel
import com.example.bookie.utils.sessionmanager.SessionManager
import kotlinx.coroutines.launch

/**
 * Clase para subir valoración de una persona
 * @author Lorena Villar
 * @version 4/4/2024
 * @see CrearReseniaPersona
 * @see RespuestaReseniaPersona
 * @see UsuarioPuntuado
 * @see UsuarioPuntuador
 * @see AddReseniaUserBinding
 * @see ChatViewModel
 * @see SharedViewModel
 * @see perfilViewModel
 * @see SessionManager
 * */

class SubirReseniaFragment : Fragment() {

    private lateinit var binding: AddReseniaUserBinding

    private val myViewModel by activityViewModels<perfilViewModel> {
        perfilViewModel.MyViewModelFactory(requireContext())
    }
    private val myViewModelChat: ChatViewModel by activityViewModels {
        ChatViewModel.MyViewModelFactory(requireContext())
    }
    private val sharedViewModel: SharedViewModel by activityViewModels()

    lateinit var alertDialog: AlertDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddReseniaUserBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sessionManager = SessionManager(requireContext())


        binding.ratingBar.rating = 3f
        binding.ratingBar.stepSize = 1f

        binding.btnSig.setOnClickListener {
            sharedViewModel.data.observe(viewLifecycleOwner) { data ->
                myViewModel.viewModelScope.launch {
                    val postId = myViewModelChat.postId()

                    val idPuntuador = postId?.toDouble()?.toInt()
                    val idPuntuado = data.toInt()

                    val puntuador = idPuntuador?.let { it1 -> UsuarioPuntuador(it1) }
                    val puntuado = UsuarioPuntuado(idPuntuado)
                    var textoResenia = binding.tvEscribirResenia.text.toString()

                    val username = sessionManager.fetchUsername()


                    val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog_publicar_resenia, null)
                    val builder = context?.let { AlertDialog.Builder(it, R.style.AlertDialogTheme) }

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

                    val puntuacion = binding.ratingBar.rating

                    positiveButton.setOnClickListener {
                        if (puntuador != null) {
                            if (puntuado != null) {
                                if (username != null) {
                                    myViewModel.postReseniaPersona(
                                        textoResenia,
                                        "",
                                        puntuacion.toInt(),
                                        username,
                                        puntuado,
                                        puntuador
                                    )
                                }
                            }
                        }
                        alertDialog.dismiss()
                        val dialog =  reseniaPublicadaDialog()
                        Handler(Looper.getMainLooper()).postDelayed({
                            dialog.dismiss()
                            findNavController().navigate(R.id.action_subirReseniaFragment_to_usuarioAjenoFragment)
                        }, 1000)

                    }
                    negativeButton.setOnClickListener {
                        alertDialog.dismiss()
                    }

                    alertDialog.show()
                }

            }

        }

        binding.backRes.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    /**
     *
     * @return
     */
    private fun reseniaPublicadaDialog() : AlertDialog{
        val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog_resenia_publicada, null)
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
}


