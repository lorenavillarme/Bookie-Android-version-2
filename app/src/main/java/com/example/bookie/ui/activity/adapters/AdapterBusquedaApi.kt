package com.example.api.ui.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookie.R
import com.example.bookie.data.model.api.Book
import com.example.bookie.databinding.HolderBibliotecaBinding
import org.json.JSONObject

/**
 * Esta clase define funciones para mostrar datos sobre libros concretos conseguidos gracias a Google API Books
 *@author Lorena Villar
 * @version 23/5/2024
 * @see Book
 * @see HolderBibliotecaBinding
 * */

class AdapterBusquedaApi(
    private val books: MutableList<JSONObject>,
    var listener: AdapterBusquedaApi.OnItemClickListener,
) : RecyclerView.Adapter<AdapterBusquedaApi.BookViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(dataItem: Book)
    }

    inner class BookViewHolder(val binding: HolderBibliotecaBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HolderBibliotecaBinding.inflate(layoutInflater, parent, false)
        return BookViewHolder(binding)
    }


    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val volumeInfo = books[position].getJSONObject("volumeInfo")
        val title = volumeInfo.getString("title")

        if (title.length > 45) {
            val shortTitle = title.substring(0,40) + "..."
            holder.binding.tvTittle.text = shortTitle
        }else {
            holder.binding.tvTittle.text = title
        }

        val authorsArray = volumeInfo.optJSONArray("authors")
        val authorsList = mutableListOf<String>()
        if( authorsArray != null) {
            for (i in 0 until authorsArray.length()) {
                authorsList.add(authorsArray.getString(i))
            }
            val bookAuthor = authorsList.joinToString(", ")
            if(bookAuthor.length > 20) {
                val shortAuthor = bookAuthor.substring(0,20) + "..."
                holder.binding.tvAuthor.text = shortAuthor
            }else {
                holder.binding.tvAuthor.text = bookAuthor
            }
        }


        val url = volumeInfo.optJSONObject("imageLinks")?.optString("thumbnail")

        if (!url.isNullOrBlank()) {
            Glide.with(holder.itemView.context)
                .load(url.toString().replace("http", "https"))
                .into(holder.binding.imgBook)
        } else {
            Glide.with(holder.itemView.context)
                .load(R.drawable.img_no_disp)
                .into(holder.binding.imgBook)
        }

        holder.binding.btnVermas.setOnClickListener {
            val esteLibro = books[position]
            val id = esteLibro.getString("id")
            val title = volumeInfo.getString("title")
            val authors = volumeInfo.getJSONArray("authors")
            val author = authors[0].toString()
//            val imageUrl = esteLibro.getString("thumbnail")
            val genres = volumeInfo.getJSONArray("categories")
            val genre = genres[0].toString()
            val editorial = volumeInfo.getString("publisher")
            val pages = volumeInfo.getInt("pageCount")
            val sinopsis = volumeInfo.getString("description")
            val formattedSinopsis = formatSinopsis(sinopsis)
            val rating = volumeInfo.optDouble("averageRating", 3.0)



            val book = Book(id,title, author, "imageUrl", genre, editorial, pages, formattedSinopsis, rating)
            listener.onItemClick(book)
        }

    }

    private fun formatSinopsis(sinopsis: String): String {
        val frases = sinopsis.split(".")
        val stringBuilder = StringBuilder()

        for (i in frases.indices) {
            if (frases[i].isNotBlank()) {
                stringBuilder.append(frases[i].trim())
                stringBuilder.append(".")
                if ((i + 1) % 2 == 0) {
                    stringBuilder.append("\n")
                } else   if ((i + 1) % 3 == 0) {
                    stringBuilder.append("\n")
                    stringBuilder.append("\n")
                } else {
                    stringBuilder.append(" ")
                }
            }
        }

        return stringBuilder.toString().trim()
    }

    override fun getItemCount(): Int {
        return books.size
    }
}
