package com.example.panaderosvm.model.remote

import com.example.panaderosvm.model.local.pueblos.PueblosEntity
import com.example.panaderosvm.utils.encrypt
import com.google.gson.annotations.SerializedName


class PueblosPOJO (

    @SerializedName("id_pueblo") var id: String? = null,
    @SerializedName("nombre") var nombre: String,
    @SerializedName("departamento") var departamento: String,
    @SerializedName("latlong") var latlong: String

    ) {
    fun toEntity(): PueblosEntity {


        return PueblosEntity(
            ID = id?.toInt() ?: 0,
            nombre = encrypt(nombre) ?: "",
            departamento = encrypt(departamento) ?: "",
            latlong = encrypt(latlong) ?: ""
        )
    }
}