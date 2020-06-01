package com.example.panaderosvm._view_ui.splashActivity

class AuthorizationResponse : BaseGenericResponse(0) {
    private var RETRIES = 2

    fun AuthorizationResponse(requestType: Int) {
        super.requestType
        RETRIES--
    }

    fun isAvailableToTyAgain(): Boolean {
        return if (RETRIES > 0) {
            true
        } else {
            RETRIES = 2
            false
        }
    }

    fun initRetries() {
        RETRIES = 3
    }

}