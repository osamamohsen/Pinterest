package com.app.pinterest.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.app.pinterest.app.MyApplication

class Helper {
    companion object {
        fun checkConnection():Boolean{
            val cm = MyApplication.instance?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activityNetwork : NetworkInfo? = cm.activeNetworkInfo
            val isConnected : Boolean = activityNetwork?.isConnected == true
            return isConnected
        }
    }
}