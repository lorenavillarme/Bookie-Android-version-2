package com.example.bookie.ui.activity.viewsmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bookie.data.model.api.Book
import com.example.bookie.ui.activity.adapters.AdapterApiInicio
import com.example.bookie.ui.activity.fragments.api.FragmentApiInicio

/**
 * Esta clase define listados predefinidos de libros sin ausencia de datos para señalar el correcto uso de la api ya que no se puede asegurar que en las búsquedas los datos se devuelvan al completo
 * @author Lorena Villar
 * @property context Contexto de la aplicación.
 * @version 23/5/2024
 * @see Book
 * @see FragmentApiInicio
 * */
class ApiViewModel(private val context: Context) : ViewModel() {

    val libro_seleccionado = MutableLiveData<Book>()

    class MyViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(Context::class.java).newInstance(context)
        }
    }
}
