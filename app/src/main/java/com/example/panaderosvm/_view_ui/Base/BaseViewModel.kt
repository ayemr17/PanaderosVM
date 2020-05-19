package com.example.panaderosvm._view_ui.Base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

abstract class BaseViewModel(application: Application) : AndroidViewModel(application)
{
    var ERROR_SOCKET_CLOSED =
        "socket closed" //este error tambien ocurre cuando el usuario cancela una solicitud de alguna api


    protected var loading = MutableLiveData<String>()
    protected var error = MutableLiveData<String>()

    /*open fun getLoading(): MutableLiveData<String>? {
        return loading
    }

    open fun setLoading(loading: String) {
        this.loading.postValue(loading)
    }*/

    /*open fun getError(): MutableLiveData<String>? {
        return error
    }

    open fun setError(msgError: String) {
        if (msgError.trim { it <= ' ' }.toLowerCase() == ERROR_SOCKET_CLOSED) return
        error.postValue(msgError)
    }*/

    open fun cancelRequestData() {}

}