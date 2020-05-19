package com.example.panaderosvm.services.remote.networking

import com.example.panaderosvm.model.remote.PueblosPOJO
import retrofit2.http.GET
import retrofit2.http.Header

interface PueblosAPI {

    @GET("listas/territorios")
    suspend fun getPueblos(@Header("Authorization") authToken: String): List<PueblosPOJO>

}