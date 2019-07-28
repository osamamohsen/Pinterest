package com.app.pinterest.base

import android.arch.lifecycle.ViewModel
import com.app.pinterest.injection.component.DaggerViewModelInjector
import com.app.pinterest.injection.component.ViewModelInjector
import com.app.pinterest.injection.module.NetworkModule
import com.app.pinterest.ui.pinterest.PinterestListViewModel

abstract class BaseViewModel : ViewModel(){
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
            is PinterestListViewModel -> injector.inject(this)
        }
    }
}