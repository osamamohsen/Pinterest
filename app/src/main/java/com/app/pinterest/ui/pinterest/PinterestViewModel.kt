package com.app.pinterest.ui.pinterest

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

    fun getCategories():String{
        var cats = ""
        for (cat in pinterest!!.categories){
            cats += cat.title+" "
        }
        return cats
    }
}