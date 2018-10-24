package com.example.george.movieguide.utils

import android.content.Context
import android.net.ConnectivityManager

import android.widget.Toast


fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun checkConnectivity(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = cm.activeNetworkInfo
    val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting

    return isConnected
}