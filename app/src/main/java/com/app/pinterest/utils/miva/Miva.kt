package com.irozon.miva

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.annotation.AnyRes
import android.support.annotation.DrawableRes
import android.widget.ImageView

import com.irozon.miva.async.LoadBitmapFromDrawableTask
import com.irozon.miva.async.LoadBitmapFromURLTask
import com.irozon.miva.cache.ImageCache
import com.irozon.miva.manager.PlaceHolder
import java.lang.ref.WeakReference


/**
 * Created by Osama Mohsen.
 */

class Miva {
    private var mUrl: String? = null
    private var enableCache = true
    private var mRes: Int = 0
    private var mPlaceHolderBitmap: Bitmap? = null
    private var mWidth = DEFAULT_WIDTH
    private var mHeight = DEFAULT_HEIGHT
    private var cacheAllowed = 1f

    companion object {
        private var contextWeakReference: WeakReference<Context>? = null
        private lateinit var mivaWeakReference: WeakReference<Miva>
        var DEFAULT_WIDTH = 500
        var DEFAULT_HEIGHT = 500

        fun with(context: Context): Miva {

            contextWeakReference = WeakReference(context)

            val miva = Miva()
            mivaWeakReference = WeakReference(miva)

            DEFAULT_HEIGHT = Utils.getScreenHeight(getContext())!!
            DEFAULT_WIDTH = Utils.getScreenWidth(getContext())!!

            return getMiva()
        }
        private fun getContext(): Context? {
            return contextWeakReference?.get()
        }



        private fun getMiva(): Miva {
            return mivaWeakReference.get()!!
        }
    }

    /**
     * Image source by url
     * @param url
     */
    fun source(url: String): Miva {
        mUrl = url
        mRes = -100
        return getMiva()
    }

    /**
     * Image source by drawable resource
     * @param res
     */
    fun source(@DrawableRes res: Int): Miva {
        mRes = res
        mUrl = null
        return getMiva()
    }

    /**
     * Resize image
     * @param width
     * @param height
     */
    fun resize(width: Int, height: Int): Miva {
        if (width != 0)
            mWidth = width
        if (height != 0)
            mHeight = height
        return getMiva()
    }

    /**
     * Placeholder image
     * @param placeholder - Drawable
     */
    fun placeholder(@AnyRes placeholder: Int): Miva {
        if (placeholder != PlaceHolder.placeHolder) {
            try {
                // Try for drawable resource
                mPlaceHolderBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getContext()?.resources, placeholder), 300, 300, true)
                PlaceHolder.placeHolder = placeholder
                PlaceHolder.placeHolderBitmap = mPlaceHolderBitmap
                PlaceHolder.placeHolderColor = -1
            } catch (ignored: Exception) {
                PlaceHolder.placeHolder = placeholder
                PlaceHolder.placeHolderBitmap = null
                PlaceHolder.placeHolderColor = placeholder
            }

        } else {
            mPlaceHolderBitmap = PlaceHolder.placeHolderBitmap
        }
        return getMiva()
    }

    /**
     * Enable cache and set cache size - 0 to 1f
     * @param percent
     */
    fun enableCache(percent: Float): Miva {
        enableCache = true
        cacheAllowed = percent
        return getMiva()
    }

    /**
     * Disable cache
     */
    fun disableCache(): Miva {
        enableCache = false
        cacheAllowed = 0f
        return getMiva()
    }

    /**
     * Load image to an ImageView
     * @param imageView
     */
    fun loadImage(imageView: ImageView) {
        var imageCache: ImageCache? = null
        var bitmap: Bitmap? = null
        if (enableCache) {
            /*Get imageCache instance*/
            imageCache = ImageCache[getContext(), cacheAllowed]
            var imageKey: String? = null
            if (mUrl != null) { //that mean it is loaded from url
                imageKey = mUrl
            }
            if (mRes != -100) { //that mean it is loaded from resources
                imageKey = mRes.toString()
            }
            bitmap = imageKey?.let { imageCache.get(it) }
        }
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap)
        } else {
            if (mUrl != null) { /*Load using url*/
                val task = LoadBitmapFromURLTask(imageView, imageCache, enableCache, mWidth, mHeight, mUrl!!)

                // Set Placeholder
                if (PlaceHolder.placeHolderBitmap != null) { // Placeholder is bitmap
                    imageView.setImageBitmap(PlaceHolder.placeHolderBitmap)
                } else if (PlaceHolder.placeHolderColor != -1) { // Placeholder is color
                    imageView.setImageResource(PlaceHolder.placeHolderColor)
                }

                task.execute()
            }
            if (mRes != -100) { /*Load using Drawable resource*/
                val task = LoadBitmapFromDrawableTask(getContext(), imageView, imageCache, enableCache, mWidth, mHeight, mRes)

                // Set Placeholder
                if (PlaceHolder.placeHolderBitmap != null) { // Placeholder is bitmap
                    imageView.setImageBitmap(PlaceHolder.placeHolderBitmap)
                } else if (PlaceHolder.placeHolderColor != -1) { // Placeholder is color
                    imageView.setImageResource(PlaceHolder.placeHolderColor)
                }

                task.execute()
            }
        }
    }
}