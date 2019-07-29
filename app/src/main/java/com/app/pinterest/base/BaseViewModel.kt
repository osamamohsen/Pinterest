package com.app.pinterest.base

import android.arch.lifecycle.ViewModel
import com.app.pinterest.injection.component.DaggerViewModelInjector
import com.app.pinterest.injection.component.ViewModelInjector
import com.app.pinterest.injection.module.NetworkModule
import com.app.pinterest.ui.pinterest.PinterestListViewModel

abstract class BaseViewModel : ViewModel(){
    /*
        first we make initialize the Network Module
        which PinterestApi used in retrieve data
     */
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            // inject PinterestListViewModel
            is PinterestListViewModel -> injector.inject(this)
        }
    }
}