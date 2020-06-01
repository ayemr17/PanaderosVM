package com.example.panaderosvm._view_ui.splashActivity

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody
import java.io.IOException

class ErrorBodyResponse {

    private val TAG = ErrorBodyResponse::class.java.simpleName
    @SerializedName("code")
    private var code: String? = null
    @SerializedName("message")
    private var message: String? = null

    fun ErrorBodyResponse() {

    }

    fun ErrorBodyResponse(code: String?, message: String?) {
        this.code = code
        this.message = message
    }

    fun getCode(): String? {
        return code
    }

    fun setCode(code: String?) {
        this.code = code
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun getErrorBodyResponse(responseBody: ResponseBody): ErrorBodyResponse? {
        try {
            val errorBody = responseBody.string()
            Log.e(TAG, errorBody)
            val gson = Gson()
            return gson.fromJson(errorBody, ErrorBodyResponse::class.java)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
        }
        return null
    }
}