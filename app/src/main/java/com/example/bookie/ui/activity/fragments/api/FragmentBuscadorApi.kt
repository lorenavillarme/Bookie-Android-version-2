package com.example.bookie.ui.activity.fragments.api

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.api.ui.Adapters.AdapterBusquedaApi
import com.example.bookie.R
import com.example.bookie.data.model.api.Book
import com.example.bookie.databinding.BuscadorApiBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

/**
 * Esta clase permite buscar libros utilizando la API de Google Books, mostrar los resultados
 * en una lista y navegar a una vista de detalles del libro seleccionado
 *@author Lorena Villar
 * @version 23/5/2024
 * @see Book
 * @see BuscadorApiBinding
 * @see AdapterBusquedaApi
 * */

class FragmentBuscadorApi : Fragment() {

    private lateinit var binding: BuscadorApiBinding
    private lateinit var bookAdapter: AdapterBusquedaApi
    private val booksList = mutableListOf<JSONObject>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BuscadorApiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookAdapter = AdapterBusquedaApi(booksList, object : AdapterBusquedaApi.OnItemClickListener{
            override fun onItemClick(dataItem: Book) {
                val bundle = bundleOf("book" to dataItem)
                findNavController().navigate(R.id.action_fragmentBuscadorApi_to_fragmentLibroApi, bundle)
            }

        })
        binding.rvBuscadorApi.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = bookAdapter
        }

        binding.btnBusca.setOnClickListener {
            searchBook()
        }

        binding.backE.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentBuscadorApi_to_fragmentApiInicio)
        }
    }

    //función que gracias a un query determina una busqueda pasándole este a una url
    /**
     *
     */
    private fun searchBook() {
        val query = binding.searchView.text.toString()
        //crea una instancia de AsyncHttpClient para manejar las solicitudes HTTP
        val client = AsyncHttpClient()
        val url = "https://www.googleapis.com/books/v1/volumes?q=${query}"

        //en esta primera parte de la función se consiguen los resultados de búsqueda por la respuesta del JSON
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)

                //en esta parte se declara como constante el JSONObject y se crea con él un array de "items"
                try {
                    val jsonObject = JSONObject(result)
                    val itemsArray = jsonObject.optJSONArray("items")

                    //aquí se borra el array bookList y después se van añadiendo uno por uno al recorrer el array de "items"
                    booksList.clear()

                    if (itemsArray != null) {
                        for (i in 0 until itemsArray.length()) {
                            val bookObject = itemsArray.getJSONObject(i)
                            booksList.add(bookObject)
                        }
                    }
                    //finalmente se notifica del cambio
                    bookAdapter.notifyDataSetChanged()
                    //la imagen de decoración de búsqueda desaparece una vez que deben aparecer resultados
                    binding.imageView8.visibility = View.GONE
                } catch (e: Exception) {
                    Log.e("errorApiBuscador", e.message.toString())
                }
            }
            //estas últimas líneas de código son empleadas para poder reconocer errores con facilidad
            override fun onFailure(
                statusCode: Int,
                p1: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "StatusCode: Bad Request"
                    403 -> "StatusCode: Forbidden"
                    404 -> "StatusCode: Not Found"
                    else -> "StatusCode: ${error?.message}"
                }
                Log.e("errorApiBuscador", errorMessage)
            }
        })
    }

}


