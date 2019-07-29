package com.app.pinterest.ui.network

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.app.pinterest.R
import com.app.pinterest.app.MyApplication
import com.app.pinterest.databinding.ActivityNetworkErrorBinding
import com.app.pinterest.ui.pinterest.PinterestActivity

class NetworkErrorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNetworkErrorBinding
    private lateinit var viewModel: NetworkViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_error)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_network_error)
        viewModel = ViewModelProviders.of(this).get(NetworkViewModel::class.java)

        /*
            if submit Retry action , it will check if mobile had connected wifi or not
         */
        viewModel.checkSubmit.observe(this, Observer { submit ->
            if (submit != null && submit.equals("Done")) {
                finish()
                startActivity(Intent(this, PinterestActivity::class.java))
            }
        })


        binding.viewModel = viewModel
    }
}
