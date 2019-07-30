package com.app.pinterest.ui.pinterest

import android.util.Log
import com.app.pinterest.base.BaseViewModel
import com.app.pinterest.model.PinterestResponse

class PinterestViewModel : BaseViewModel() {
    private var pinterest: PinterestResponse? = null

    fun bind(pinterest: PinterestResponse) {
        this.pinterest = pinterest
    }

    fun getPinterest(): PinterestResponse? {
        return pinterest
    }

    fun getImageUrl():String{
        //will be check on connection
        var imageUrl = ""
        if(pinterest != null) {
            imageUrl = pinterest?.user?.profile_image?.medium.toString()
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