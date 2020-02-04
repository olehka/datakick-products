/*
 * Copyright (c) 2020 OlehKapustianov.
 */

package com.olehka.datakick.utilities

import android.content.Context
import android.net.ConnectivityManager


class ConnectivityAgent(private val context: Context) {

    fun isDeviceConnectedToInternet(): Boolean {
        val service = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = service.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }
}