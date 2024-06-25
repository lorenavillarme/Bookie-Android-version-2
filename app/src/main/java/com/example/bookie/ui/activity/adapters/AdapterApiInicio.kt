package com.example.bookie.ui.activity.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookie.R
import com.example.bookie.data.model.api.Book
import com.example.bookie.databinding.HolderApiInicioBinding
import com.example.bookie.ui.activity.adapters.AdapterApiInicio.OnItemClickListener


/**
 * Este adaptador
 *@author Lorena Villar
 * @version 16/5/2024
 * @param OnItemClickListener
 * @see Book
 * @see HolderApiInicioBinding
 * */
class AdapterApiInicio (
    var listener: OnItemClickListener
) : RecyclerView.Adapter<AdapterApiInicio.MiCelda>() {

    private var libros = ArrayList<Book>()

    interface OnItemClickListener {
        fun onItemClick(dataItem: Book)
    }

    inner class MiCelda(val binding: HolderApiInicioBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MiCelda {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HolderApiInicioBinding.inflate(layoutInflater, parent, false)
        return MiCelda(binding)
    }

    /*a partir de los datos de dataItem siendo un item del array books se rellenan campos de la pantalla
     y se obtiene la imagen del libro a partir de una url a la que se le pasa la query del título del libro a buscar*/
    override fun onBindViewHolder(holder: MiCelda, position: Int) {
        val dataItem: Book = libros.get(position)
        holder.binding.tvTituloApiInicio.text = dataItem.title
        holder.binding.tvAutorApiInicio.text = dataItem.author

        val url = "https://books.google.com/books/content?id=${dataItem.id}&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"

        /*comprobación de imágenes: en el caso de venir null se hace uso de una foto predefinida
         dando así un aviso al usuario de que la imagen no está disponible*/
        if (!url.isNullOrBlank()) {
            Glide.with(holder.itemView.context)
                .load(url)
                .into(holder.binding.imgBookApiInicio)
        } else {
            Glide.with(holder.itemView.context)
                .load(R.drawable.img_no_disp)
                .into(holder.binding.imgBookApiInicio)
        }

        holder.binding.cardviewApiInicio.setOnClickListener {
            listener.onItemClick(dataItem)
        }
    }

    override fun getItemCount(): Int {
        return libros.size
    }

    fun updateList(lista: List<Book>) {
        libros.clear()
        libros.addAll(lista)

        notifyDataSetChanged()
    }

}
