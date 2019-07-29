package com.app.pinterest.utils.image

import javax.swing.UIManager.put
import android.graphics.Bitmap
import android.util.LruCache
import java.lang.reflect.Array.get


class BitmapCache(maxSize: Int) : LruCache(maxSize), ImageCache {

    fun getBitmap(url: String): Bitmap {
        return get(url)
    }

    fun putBitmap(url: String, bitmap: Bitmap) {
        put(url, bitmap)
    }
}

interface ImageCache {

}
