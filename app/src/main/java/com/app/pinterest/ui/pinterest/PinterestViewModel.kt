package com.app.pinterest.ui.pinterest

import android.util.Log
import com.app.pinterest.base.BaseViewModel
import com.app.pinterest.model.PinterestResponse

class PinterestViewModel : BaseViewModel() {
    private var pinterest: PinterestResponse? = null
    private var net: String = ""

    fun bind(pinterest: PinterestResponse,net:String) {
        this.pinterest = pinterest
        this.net = net
    }

    fun getPinterest(): PinterestResponse? {
        return pinterest
    }

    fun getImageUrl():String{
        //will be check on connection
        var imageUrl = ""
        if(pinterest != null) {
            if(net.equals("2G"))
                imageUrl = pinterest?.user?.profile_image?.small.toString()
            else if(net.equals("3G"))
                imageUrl = pinterest?.user?.profile_image?.medium.toString()
            else
                imageUrl = pinterest?.user?.profile_image?.large.toString()
        }
        return imageUrl
    }

    fun getCategories():String{
        var cats = ""
        for (cat in pinterest!!.categories){
            cats += cat.title+" "
        }
        return cats
    }
}