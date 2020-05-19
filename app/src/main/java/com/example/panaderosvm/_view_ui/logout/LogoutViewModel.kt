package com.example.panaderosvm._view_ui.logout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LogoutViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Esto es para desloguearte"
    }
    val text: LiveData<String> = _text
}