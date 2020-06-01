package com.indra.applicability.utils.connectivity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log

class NetworkStatusReceiver : BroadcastReceiver() {


    private var connectionCallback: ConnectionCallback? = null

    override fun onReceive(context: Context?, intent: Intent) {
        if (intent.extras != null) {
            val ni =
                intent.extras!![ConnectivityManager.EXTRA_NETWORK_INFO] as NetworkInfo?
            if (ni != null && ni.state == NetworkInfo.State.CONNECTED) {
                Log.i("app", "Network " + ni.typeName + " connected")
                if (connectionCallback != null) {
                    connectionCallback!!.hasActiveConnection(true)
                }
            }
        }
        if (intent.extras!!.getBoolean(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)) {
            Log.d("app", "There's no network connectivity")
            if (connectionCallback != null) {
                connectionCallback!!.hasActiveConnection(false)
            }
        }
    }

    fun setConnectionCallback(connectionCallback: ConnectionCallback?) {
        this.connectionCallback = connectionCallback
    }

}