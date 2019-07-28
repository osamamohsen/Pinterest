package net.gahfy.mvvmposts.utils

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.support.v7.widget.RecyclerView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.app.pinterest.R
import com.app.pinterest.app.MyApplication
import net.gahfy.mvvmposts.utils.extension.getParentActivity

@BindingAdapter("adapter")
fun setAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter
}

@BindingAdapter("mutableVisibility")
fun setMutableVisibility(view: View,  visibility: MutableLiveData<Int>?) {
    val parentActivity:AppCompatActivity? = view.getParentActivity()
    if(parentActivity != null && visibility != null) {
        visibility.observe(parentActivity, Observer { value -> view.visibility = value?:View.VISIBLE})
    }
}

@BindingAdapter("mutableText")
fun setMutableText(view: TextView,  text: MutableLiveData<String>?) {
    val parentActivity:AppCompatActivity? = view.getParentActivity()
    if(parentActivity != null && text != null) {
        text.observe(parentActivity, Observer { value -> view.text = value?:""})
    }
}


object Bindings {
    @BindingAdapter("customOnClick")
    @JvmStatic
    fun setCustomClick(view: View,  anim: (Animation) -> Any?) {
        Log.e("click","done")
        val animation:Animation = AnimationUtils.loadAnimation(MyApplication.instance, R.anim.bounce)
        view.animation = animation
        animation.start()
    }
}


