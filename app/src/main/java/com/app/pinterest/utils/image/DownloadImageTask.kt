package com.app.pinterest.utils.image

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView


 class DownloadImageTask(internal var bmImage: ImageView) : AsyncTask<String, Void, Bitmap>() {

    override fun doInBackground(vararg urls: String): Bitmap? {
        val urldisplay = urls[0]
        var mIcon11: Bitmap? = null
        try {
            val `in` = java.net.URL(urldisplay).openStream()
            mIcon11 = BitmapFactory.decodeStream(`in`)
        } catch (e: Exception) {
            Log.e("Error", e.message)
            e.printStackTrace()
        }

        return mIcon11
    }

    override fun onPostExecute(result: Bitmap) {
//        bmImage.setImageResource(null);
        bmImage.setImageBitmap(result)
    }
    /*
        new DownloadImageTask((ImageView) findViewById(R.id.imageView1))
        .execute(MY_URL_STRING);

        URL url = new URL("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464");
        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        imageView.setImageBitmap(bmp);
     */
}