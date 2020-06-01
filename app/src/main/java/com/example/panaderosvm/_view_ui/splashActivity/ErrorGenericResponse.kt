package com.example.panaderosvm._view_ui.splashActivity

import android.util.Log
import com.example.panaderosvm.BuildConfig

open class ErrorGenericResponse : BaseGenericResponse(0) {
    private val TAG = ErrorGenericResponse::class.java.simpleName
    private var message: String? = null
    private var errorBodyResponse: ErrorBodyResponse? = ErrorBodyResponse()
    private var needToUpdateMatriculaConfig = false
    private var needToShowMultipleAttachments = false
    private var needToShowConnectionError = false

    open fun ErrorGenericResponse(requestType: Int) {
        super.requestType
        printLogError(requestType, message)
    }

    open fun ErrorGenericResponse(requestType: Int, message: String?) {
        super.requestType
        this.message = message
        this.requestType = requestType
        printLogError(requestType, message)
    }

    open fun ErrorGenericResponse(
        requestType: Int,
        errorBodyResponse: ErrorBodyResponse?
    ) {
        super.requestType
        this.errorBodyResponse = errorBodyResponse
        this.requestType = requestType
        printLogError(requestType, message)
    }

    open fun ErrorGenericResponse(
        requestType: Int,
        errorBodyResponse: ErrorBodyResponse?,
        message: String?
    ) {
        super.requestType
        this.message = message
        this.errorBodyResponse = errorBodyResponse
        this.requestType = requestType
        printLogError(requestType, message)
    }

    open fun ErrorGenericResponse(
        requestType: Int,
        errorBodyResponse: ErrorBodyResponse?,
        message: String?,
        needToUpdateMatriculaConfig: Boolean
    ) {
        super.requestType
        this.message = message
        this.errorBodyResponse = errorBodyResponse
        this.requestType = requestType
        this.needToUpdateMatriculaConfig = needToUpdateMatriculaConfig
        printLogError(requestType, message)
    }

    open fun ErrorGenericResponse(
        requestType: Int,
        errorBodyResponse: ErrorBodyResponse?,
        needToShowMultipleAttachments: Boolean
    ) {
        super.requestType
        this.errorBodyResponse = errorBodyResponse
        this.requestType = requestType
        this.needToShowMultipleAttachments = needToShowMultipleAttachments
        printLogError(requestType, message)
    }

    open fun ErrorGenericResponse(
        requestType: Int,
        message: String?,
        needToShowConnectionError: Boolean
    ) {
        super.requestType
        this.message = message
        this.requestType = requestType
        this.needToShowConnectionError = needToShowConnectionError
        printLogError(requestType, message)
    }

    private fun printLogError(requestType: Int, message: String?) {
        if (BuildConfig.ENABLE_LOG) {
            Log.e(TAG, "request type: $requestType message: $message")
        }
    }

    fun getMessage(): String? {
        return message
    }

    fun getErrorBodyResponse(): ErrorBodyResponse? {
        return errorBodyResponse
    }

    override fun getRequestType(): Int {
        return requestType
    }

    fun isNeedToUpdateMatriculaConfig(): Boolean {
        return needToUpdateMatriculaConfig
    }

    fun setNeedToUpdateMatriculaConfig(needToUpdateMatriculaConfig: Boolean) {
        this.needToUpdateMatriculaConfig = needToUpdateMatriculaConfig
    }

    fun isNeedToShowMultipleAttachments(): Boolean {
        return needToShowMultipleAttachments
    }

    fun setNeedToShowMultipleAttachments(needToShowMultipleAttachments: Boolean) {
        this.needToShowMultipleAttachments = needToShowMultipleAttachments
    }

    fun isNeedToShowConnectionError(): Boolean {
        return needToShowConnectionError
    }

    fun setNeedToShowConnectionError(needToShowConnectionError: Boolean) {
        this.needToShowConnectionError = needToShowConnectionError
    }
}