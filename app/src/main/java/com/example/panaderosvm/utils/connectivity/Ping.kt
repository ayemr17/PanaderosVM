package com.indra.applicability.utils.connectivity

import android.content.Context
import android.os.AsyncTask

class Ping : AsyncTask<Context, Void, Boolean>() {

    private var connectionCallback: ConnectionCallback? = null
    private var noInternetUtils = NoInternetUtils()

    override fun doInBackground(vararg ctxs: Context?): Boolean? {
        return noInternetUtils.isConnectedToInternet(ctxs[0]!!) && noInternetUtils.hasActiveInternetConnection(
            ctxs[0]
        )
    }

    override fun onPostExecute(aBoolean: Boolean?) {
        super.onPostExecute(aBoolean)
        if (connectionCallback != null) {
            connectionCallback!!.hasActiveConnection(aBoolean!!)
        }
    }

    fun setConnectionCallback(connectionCallback: ConnectionCallback?) {
        this.connectionCallback = connectionCallback
    }

}