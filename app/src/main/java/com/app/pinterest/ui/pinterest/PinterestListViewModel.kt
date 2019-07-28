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
    @Inject
    lateinit var pinterestApi: PinterestApi
    val pinterestListAdapter: PinterestListAdapter =
        PinterestListAdapter()
    var pinterestListAll = ArrayList<PinterestResponse>()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadPinterests() }
    val errorMessage: MutableLiveData<Int> = MutableLiveData()


    private lateinit var subscription: Disposable

    init{
        loadPinterests()
    }

    public fun loadPinterests(){
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

    private fun onRetrievePinterestListStart(){
        loadingVisibility.value = View.VISIBLE
    }

    private fun onRetrievePinterestListFinish(){
        loadingVisibility.value = View.GONE
    }

    private fun onRetrievePinterestListSuccess(pinterestList:List<PinterestResponse>){
        pinterestListAll.addAll(pinterestList)
        pinterestListAdapter.updatePinterestList(pinterestListAll)
    }

    private fun onRetrievePinterestListError(){
        errorMessage.value = R.string.error
    }

    //subscription property when the ViewModel is no longer used and will be destroyed.
    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

}