package com.example.panaderosvm.utils

interface ResponseObjetBasic<T> {
    fun onSuccess(entity: T)

    fun onError(message: String)
}