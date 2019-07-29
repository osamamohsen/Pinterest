package com.app.pinterest.ui.pinterest

import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.app.pinterest.R
import com.app.pinterest.base.BaseViewModel
import com.app.pinterest.model.PinterestResponse
import com.app.pinterest.network.PinterestApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PinterestListViewModel : BaseViewModel() {
    //inject api connection
    @Inject
    lateinit var pinterestApi: PinterestApi

    //initialize adapter
    val pinterestListAdapter: PinterestListAdapter =  PinterestListAdapter()

    //all list data
    var pinterestListAll = ArrayList<PinterestResponse>()

    //initialize loader
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    //initialize error
    val errorClickListener = View.OnClickListener { loadPinterests() }

    //initialize error message
    val errorMessage: MutableLiveData<Int> = MutableLiveData()


    //initialize connection
    private lateinit var subscription: Disposable

    init{
        loadPinterests()
    }

    //load all pinterests data
    fun loadPinterests(){
        subscription = pinterestApi.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePinterestListStart() }
            .doOnTerminate { onRetrievePinterestListFinish() }
            .subscribe(
                { result -> onRetrievePinterestListSuccess(result) },
                { onRetrievePinterestListError() }
            )
    }

    //show loader
    private fun onRetrievePinterestListStart(){
        loadingVisibility.value = View.VISIBLE
    }

    //hide loader
    private fun onRetrievePinterestListFinish(){
        loadingVisibility.value = View.GONE
    }

    //update list data adapter
    private fun onRetrievePinterestListSuccess(pinterestList:List<PinterestResponse>){
        pinterestListAll.addAll(pinterestList)
        pinterestListAdapter.updatePinterestList(pinterestListAll)
    }

    //found problem in retrieve data
    private fun onRetrievePinterestListError(){
        errorMessage.value = R.string.error
    }

    //subscription property when the ViewModel is no longer used and will be destroyed.
    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

}