package com.example.bookie.ui.activity.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookie.R
import com.example.bookie.data.model.ReseniaPersona.RespuestaReseniaPersona
import com.example.bookie.data.model.api.Book
import com.example.bookie.data.model.favoritos.Libro
import com.example.bookie.data.model.favoritos.RespuestaFavorito
import com.example.bookie.databinding.HolderReseniaBinding
import com.example.bookie.databinding.VistaFavoritosBinding

class AdapterFavoritos (
    var listener: OnItemClickListener
): RecyclerView.Adapter<AdapterFavoritos.MiCelda>() {

    private var favoritos = ArrayList<RespuestaFavorito>()

    interface OnItemClickListener {
        fun onItemClick(dataItem: RespuestaFavorito)
    }

    inner class MiCelda(val binding: VistaFavoritosBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterFavoritos.MiCelda {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = VistaFavoritosBinding.inflate(layoutInflater, parent, false)
        return MiCelda(binding)
    }

    override fun onBindViewHolder(holder: AdapterFavoritos.MiCelda, position: Int) {
        val favorito = favoritos[position]
        holder.binding.tituloLibro.text = favorito.titulo
        val decodedBitmap = base64ToBitmap(favorito.imagen)
        holder.binding.imagenLibroFav.setImageBitmap(decodedBitmap)
        holder.itemView.setOnClickListener {
            listener.onItemClick(favorito)
        }
    }

    override fun getItemCount(): Int {
        return favoritos.count()
    }

    fun update(lista: List<RespuestaFavorito>) {
        favoritos.clear()
        favoritos.addAll(lista)

        notifyDataSetChanged()
    }

    private fun base64ToBitmap(base64Str: String): Bitmap {
        val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

}