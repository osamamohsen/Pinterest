package com.app.pinterest.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.app.pinterest.app.MyApplication
import android.telephony.TelephonyManager



class Helper {
    companion object {
        /*
            check connection wifi is connected or not
            by CONNECTIVITY_SERVICE
         */
        fun hasNetwork(): Boolean? {
            var isConnected: Boolean? = false // Initial Value
            val connectivityManager = MyApplication.instance?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            if (activeNetwork != null && activeNetwork.isConnected)
                isConnected = true
            return isConnected
        }


        fun getNetworkClass(context: Context): String {
            val mTelephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val networkType = mTelephonyManager.networkType
            when (networkType) {
                TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN -> return "2G"
                TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_HSPAP -> return "3G"
                TelephonyManager.NETWORK_TYPE_LTE -> return "4G"
                else -> return "Unknown"
            }
        }
    }
}