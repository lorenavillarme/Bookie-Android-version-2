package com.example.bookie.ui.activity.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookie.R
import com.example.bookie.data.model.Libro

class AdapterBusquedaNoApi(private val libros: List<Libro>) : RecyclerView.Adapter<AdapterBusquedaNoApi.LibroViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.holder_buscador_api, parent, false)
        return LibroViewHolder(view)
    }

    override fun onBindViewHolder(holder: LibroViewHolder, position: Int) {
        val libro = libros[position]
        holder.bind(libro)
    }

    override fun getItemCount(): Int {
        return libros.size
    }

    class LibroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_titulo)
        private val tvAuthor: TextView = itemView.findViewById(R.id.tv_autor)

        fun bind(libro: Libro) {
            tvTitle.text = libro.titulo
            tvAuthor.text = libro.autor
        }
    }
}