package com.example.feedme.network

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckNetworkConnexion
@Inject
constructor(
    private val application: Application,
) {
    fun isConnectedToInternet(): Boolean {
        val connectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        if (network != null) {
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            if (networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            ) {
                return true
            }
        } else {
            val processBuilder = ProcessBuilder("/system/bin/ping", "-c", "1", "8.8.8.8")
            try {
                val process = processBuilder.start()
                val exitValue = process.waitFor()
                if (exitValue == 0) {
                    return true
                }
            } catch (e: Exception) {
                // handle exception
            }
        }
        return false
    }
}