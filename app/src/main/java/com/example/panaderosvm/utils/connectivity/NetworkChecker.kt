package com.example.panaderosvm.utils.connectivity

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.example.panaderosvm.utils.connectivity.NetworkStatusReceiver
import com.example.panaderosvm.utils.connectivity.WifiReceiver

class NetworkChecker {
    private var wifiReceiver: WifiReceiver? = null
    private var networkStatusReceiver: NetworkStatusReceiver? = null
    private var connectionCallback: ConnectionCallback? = null
    private val isWifiOn = false

    fun init(
        context: Context,
        connectionListener: ConnectionListener?,
        connectionCallback: ConnectionCallback?
    ) {
        wifiReceiver = WifiReceiver()
        context.registerReceiver(
            wifiReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        networkStatusReceiver = NetworkStatusReceiver()
        context.registerReceiver(
            networkStatusReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        wifiReceiver!!.setConnectionListener(connectionListener)
        networkStatusReceiver!!.setConnectionCallback(connectionCallback)
        this.connectionCallback = connectionCallback
    }

    fun destroy(context: Context) {
        context.unregisterReceiver(wifiReceiver)
        context.unregisterReceiver(networkStatusReceiver)
    }

    fun checkNetworkConnection(
        context: Context?,
        callback: ConnectionCallback?
    ) {
        val ping = Ping()
        ping.setConnectionCallback(callback)
        ping.execute(context)
    }
}