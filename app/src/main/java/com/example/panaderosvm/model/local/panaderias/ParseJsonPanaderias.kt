package com.example.panaderosvm.model.local.panaderias

import android.content.Context
import android.util.Log
import com.example.panaderosvm.R
import org.json.JSONArray

class ParseJsonPanaderias(val context: Context) {

    private val TAG: String = ParseJsonPanaderias::class.java.simpleName
    private val errorParseJsonPanaderias: String = context.getString(R.string.errorParseJsonPanaderias)
    private val successParseJsonPanaderias: String =
        context.getString(R.string.successParseJsonPanaderias)

    fun parseJson(): MutableList<PanaderiasEntity>? {

        val listPanaderias: MutableList<PanaderiasEntity> = mutableListOf()

        try {
            val file_name = context.getString(R.string.jsonPanaderias)

            val json_String = context.assets.open(file_name).bufferedReader().use {
                it.readText()
            }

            if (!json_String.isNullOrBlank()) {
                val jsonArray = JSONArray(json_String)
                for (jsonIndex in 0..(jsonArray.length() - 1)) {
                    val panaderiaEntity = PanaderiasEntity(
                        jsonArray.getJSONObject(jsonIndex).getInt("id"),
                        jsonArray.getJSONObject(jsonIndex).getString("nombre"),
                        jsonArray.getJSONObject(jsonIndex).getString("direccion"),
                        jsonArray.getJSONObject(jsonIndex).getString("telefono"),
                        jsonArray.getJSONObject(jsonIndex).getInt("id_empleador"),
                        jsonArray.getJSONObject(jsonIndex).getInt("cantidad_empleados"),
                        jsonArray.getJSONObject(jsonIndex).getInt("id_pueblo"),
                        jsonArray.getJSONObject(jsonIndex).getString("latlong")
                    )
                    listPanaderias.add(panaderiaEntity)
                }
            }
            if (!listPanaderias.isNullOrEmpty()) {
                Log.i(TAG, successParseJsonPanaderias)
            }
            return listPanaderias
        } catch (e: Exception) {
            Log.e(TAG, errorParseJsonPanaderias + "//" + e.message)
            return null
        }
    }
}