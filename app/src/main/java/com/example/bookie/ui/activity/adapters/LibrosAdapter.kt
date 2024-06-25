package com.example.bookie.ui.activity.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.bookie.data.model.RespuestaLibro
import com.example.bookie.databinding.VistaLibroBinding


class LibrosAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<LibrosAdapter.MiCelda>(),
    Filterable {

    private var libros = ArrayList<RespuestaLibro>()
    private var copiaLibros = ArrayList<RespuestaLibro>()


    interface OnItemClickListener {
        fun onItemClick(libro: RespuestaLibro)
    }

    inner class MiCelda(val binding: VistaLibroBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiCelda {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = VistaLibroBinding.inflate(layoutInflater, parent, false)
        return MiCelda(binding)
    }

    override fun getItemCount(): Int {
        return libros.count()
    }

    override fun onBindViewHolder(holder: MiCelda, position: Int) {
        val libroN: RespuestaLibro = libros[position]

        holder.binding.tituloLibro.text = libroN.titulo

        holder.itemView.setOnClickListener {
            listener.onItemClick(libroN)
        }

        val decodedBitmap = base64ToBitmap(libroN.foto)
        holder.binding.imagenLibroFav.setImageBitmap(decodedBitmap)
    }

    fun update(lista: List<RespuestaLibro>) {
        libros.clear()
        libros.addAll(lista)

        copiaLibros.clear()
        copiaLibros.addAll(lista)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val searchText = constraint.toString().lowercase().trim()
                var listadoFiltrado = ArrayList<RespuestaLibro>()
                if (searchText.isEmpty()) {
                    listadoFiltrado.addAll(libros)
                } else {
                    listadoFiltrado.addAll(copiaLibros.filter {
                        it.titulo.lowercase().contains(searchText)
                    })
                }
                val filterResults = FilterResults()
                filterResults.values = listadoFiltrado
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                libros = results?.values as ArrayList<RespuestaLibro>
                notifyDataSetChanged()
            }
        }
    }

    private fun base64ToBitmap(base64Str: String): Bitmap {
        val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }
}