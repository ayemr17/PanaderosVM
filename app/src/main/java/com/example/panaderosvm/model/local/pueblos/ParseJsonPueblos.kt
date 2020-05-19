package com.example.panaderosvm.model.local.pueblos

import android.content.Context
import android.util.Log
import com.example.panaderosvm.R
import org.json.JSONArray

class ParseJsonPueblos(val context: Context) {

    private val TAG: String = ParseJsonPueblos::class.java.simpleName
    private val errorParseJsonPueblos: String = context.getString(R.string.errorParseJsonPueblos)
    private val successParseJsonPueblos: String =
        context.getString(R.string.successParseJsonPueblos)

    fun parseJson(): MutableList<PueblosEntity>? {
        val listPueblos: MutableList<PueblosEntity> = mutableListOf()
        try {
            val file_name = context.getString(R.string.jsonPueblos)
            val json_String = context.assets.open(file_name).bufferedReader().use {
                it.readText()
            }
            if (!json_String.isNullOrBlank()) {
                val jsonArray = JSONArray(json_String)
                for (jsonIndex in 0..(jsonArray.length() - 1)) {
                    val puebloEntity = PueblosEntity(
                        jsonArray.getJSONObject(jsonIndex).getInt("id"),
                        jsonArray.getJSONObject(jsonIndex).getString("nombre"),
                        jsonArray.getJSONObject(jsonIndex).getString("departamento"),
                        jsonArray.getJSONObject(jsonIndex).getString("latlong")
                    )
                    listPueblos.add(puebloEntity)
                }
            }
            if (!listPueblos.isNullOrEmpty()) {
                Log.i(TAG, successParseJsonPueblos)
            }
            return listPueblos
        } catch (e: Exception) {
            Log.e(TAG, errorParseJsonPueblos + "//" + e.message)
            return null
        }
    }
}