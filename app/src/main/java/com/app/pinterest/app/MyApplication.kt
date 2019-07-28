package com.app.pinterest.app

import android.support.multidex.MultiDexApplication

class MyApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        val TAG = MyApplication::class.java.simpleName

        @get:Synchronized
        var instance: MyApplication? = null
            private set
    }

}