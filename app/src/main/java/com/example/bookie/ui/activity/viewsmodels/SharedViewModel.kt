package com.example.bookie.ui.activity.viewsmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel para guardar y transportar datos a otras vistas
 * @author Lorena Villar
 * @version 4/4/2024
 * */

class SharedViewModel : ViewModel() {
    private val _data = MutableLiveData<String>()
    val data: LiveData<String> get() = _data

    private val _data2 = MutableLiveData<String>()
    val data2: LiveData<String> get() = _data3
    private val _data3 = MutableLiveData<String>()
    val data3: LiveData<String> get() = _data3

    private val _dataNotaAjena = MutableLiveData<String>()
    val dataNotaAjena: LiveData<String> get() = _dataNotaAjena

    //guarda id usuario ajeno
    private val _dataIdUser = MutableLiveData<String>()
    val dataIdUser: LiveData<String> get() = _dataIdUser

    fun setData(data: String) {
        _data.value = data
    }

    fun setData2(data2: String) {
        _data2.value = data2
    }

    fun setData3(data3: String) {
        _data3.value = data3
    }

    fun setDataNotaAjena(dataNotaAjena: String) {
        _dataNotaAjena.value = dataNotaAjena
    }

    fun setDataIdUser(dataIdUser: String) {
        _dataIdUser.value = dataIdUser
    }

}