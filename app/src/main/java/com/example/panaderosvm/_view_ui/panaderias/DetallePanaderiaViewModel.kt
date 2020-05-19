package com.example.panaderosvm._view_ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetallePanaderiaViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Aca ira el detalle de la panaderia seleccionada \n " +
                "Como por ejemplo Nombre, direccion, telefono, dueño/responsable \n " +
                "y la opcion de geolocalizar en el mapa para trasladarse al lugar."
    }
    val text: LiveData<String> = _text
}
