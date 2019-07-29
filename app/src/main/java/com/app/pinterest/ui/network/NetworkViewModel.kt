package com.app.pinterest.ui.network

import android.arch.lifecycle.MutableLiveData
import com.app.pinterest.base.BaseViewModel
import com.app.pinterest.utils.Helper
import android.R
import android.R.interpolator.bounce
import android.util.Log
import android.view.animation.AnimationUtils.loadAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.app.pinterest.app.MyApplication


class NetworkViewModel:BaseViewModel() {
    val checkSubmit: MutableLiveData<String> = MutableLiveData()

    val animation:Animation = loadAnimation(MyApplication.instance, com.app.pinterest.R.anim.bounce)

    fun onClick(){
        //check connection is ready to connect
        if(Helper.hasNetwork()!!)
            checkSubmit.value = "Done"
    }

    fun onClickVoid(): Void? {
        onClick()
        return null
    }
}