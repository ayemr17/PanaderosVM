package com.indra.applicability.utils.connectivity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

class WifiReceiver : BroadcastReceiver() {


    private var connectionListener: ConnectionListener? = null

    override fun onReceive(context: Context, intent: Intent?) {
        val conMan =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = conMan.activeNetworkInfo
        if (netInfo != null && netInfo.type == ConnectivityManager.TYPE_WIFI) {
            connectionListener?.onWifiTurnedOn()
        } else {
            connectionListener?.onWifiTurnedOff()
        }
    }

    fun setConnectionListener(connectionListener: ConnectionListener?) {
        this.connectionListener = connectionListener
    }


}