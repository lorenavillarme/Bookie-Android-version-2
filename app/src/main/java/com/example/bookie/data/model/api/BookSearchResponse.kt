package com.example.bookie.data.model.api

import com.example.bookie.data.model.api.BookItem
/**
 * Modelo conseguido directamente gracias al paso de la query, este aporta un listado de todos los BookItem que coinciden con la query
 *@author Lorena Villar
 * @version 12/4/2024
 * @see BookItem
 * */
data class BookSearchResponse(
    val items: List<BookItem>
)
