package com.app.pinterest.network

import com.app.pinterest.model.PinterestResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface PinterestApi {
    @GET("raw/wgkJgazE")
    fun getData(): Observable<List<PinterestResponse>>
}