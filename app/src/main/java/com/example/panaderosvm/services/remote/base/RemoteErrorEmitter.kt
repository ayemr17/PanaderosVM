package com.example.panaderosvm.services.remote.base

import com.example.panaderosvm.services.remote.base.ErrorType

interface RemoteErrorEmitter {
    fun onError(msg: String)
    fun onError(errorType: ErrorType)
}