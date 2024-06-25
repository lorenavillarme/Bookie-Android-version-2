package com.example.bookie.ui.activity.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.bookie.data.model.ReseniaPersona.RespuestaReseniaPersona
import com.example.bookie.databinding.HolderReseniaBinding
import com.example.bookie.ui.activity.viewsmodels.ChatViewModel
import com.example.bookie.ui.activity.viewsmodels.perfilViewModel

class ReseniaAdapter(
) : RecyclerView.Adapter<ReseniaAdapter.MiCelda>() {

    private var resenias = ArrayList<RespuestaReseniaPersona>()

    inner class MiCelda(val binding: HolderReseniaBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiCelda {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HolderReseniaBinding.inflate(layoutInflater, parent, false)
        return MiCelda(binding)
    }

    override fun onBindViewHolder(holder: MiCelda, position: Int) {


        val resenia = resenias[position]

        holder.binding.descripcionResenia.text = resenia.comentario
        holder.binding.fechaResenia.text = resenia.fechaReseniaPersona
        holder.binding.ratingResenia.text = resenia.puntuacion.toString()
        holder.binding.userResenia.text = resenia.username

    }

    override fun getItemCount(): Int {
        return resenias.count()
    }

    fun update(lista: List<RespuestaReseniaPersona>) {
        resenias.clear()
        resenias.addAll(lista)

        notifyDataSetChanged()
    }
}