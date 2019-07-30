package com.app.pinterest.app

import android.support.multidex.MultiDexApplication
import com.irozon.miva.Miva

/*
    is consider an application instance for all application
    could be used to retrieve resources like strings, drawable , .....
 */
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