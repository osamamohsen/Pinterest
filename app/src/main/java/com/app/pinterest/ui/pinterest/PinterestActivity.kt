package com.app.pinterest.ui.pinterest

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Canvas
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.app.pinterest.R
import com.app.pinterest.databinding.ActivityPinterestBinding
import com.app.pinterest.ui.network.NetworkErrorActivity
import com.app.pinterest.utils.EndlessRecyclerViewScrollListener


class PinterestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPinterestBinding
    private lateinit var viewModel: PinterestListViewModel
    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Binding activity
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pinterest)
        //inject PinterestListViewModel
        viewModel = ViewModelProviders.of(this).get(PinterestListViewModel::class.java)
        //show error data
        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })

        //initialize recyclerView
        initList()

        binding.viewModel = viewModel
    }

    private fun initList() {
        //set List Oriention
        var linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //set Scroll for List
        binding.postList.addOnScrollListener(object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                viewModel.loadPinterests()
            }
        })
        binding.postList.layoutManager = linearLayoutManager



    }

    private fun showError(@StringRes errorMessage: Int) {
        if (viewModel.pinterestListAll.size == 0) {
            finish()
            startActivity(Intent(this, NetworkErrorActivity::class.java))
        } else {
            errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
            errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
            errorSnackbar?.setActionTextColor(Color.WHITE)
            errorSnackbar?.show()
        }
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }
}
