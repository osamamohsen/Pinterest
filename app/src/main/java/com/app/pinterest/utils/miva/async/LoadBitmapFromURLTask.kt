package com.irozon.miva.async

import android.widget.ImageView

import com.irozon.miva.cache.ImageCache
import com.irozon.miva.manager.ImageFetcher
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

import java.net.MalformedURLException
import java.net.URL

/**
 * Created by Osama Mohsen.
 */
class LoadBitmapFromURLTask(private val imageView: ImageView, private val mCache: ImageCache?, private val mCacheImage: Boolean, private val mWidth: Int, private val mHeight: Int, private val strUrl: String) {

    fun execute() {
        doAsync {
            var url: URL? = null
            try {
                url = URL(strUrl)
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }
            if (url != null) {
                val bitmap = ImageFetcher.decodeSampledBitmapFromUrl(url, mWidth, mHeight)
                uiThread {
                    if (mCacheImage) {
                        mCache?.put(strUrl, bitmap)
                    }
                    imageView.setImageBitmap(bitmap)

                }
            }
        }
    }
}

