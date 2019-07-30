package com.irozon.miva.async

import android.content.Context
import android.widget.ImageView
import com.irozon.miva.cache.ImageCache
import com.irozon.miva.manager.ImageFetcher
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by Osama Mohsen.
 */
class LoadBitmapFromDrawableTask(private val mContext: Context?, private val imageView: ImageView, private val mCache: ImageCache?, private val mCacheImage: Boolean, private val mWidth: Int, private val mHeight: Int, var res: Int) {
    fun execute() {
        doAsync {
            val bitmap = mContext?.let { ImageFetcher.decodeSampledBitmapFromDrawable(it, res, mWidth, mHeight) }
            uiThread {
                if (mCacheImage) {
                    bitmap?.let { it1 -> mCache?.put(res.toString(), it1) }
                }
                imageView.setImageBitmap(bitmap)
            }
        }
    }
}

