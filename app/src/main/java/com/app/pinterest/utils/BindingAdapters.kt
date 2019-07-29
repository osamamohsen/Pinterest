package net.gahfy.mvvmposts.utils

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.app.pinterest.R
import com.app.pinterest.app.MyApplication
import com.app.pinterest.ui.pinterest.PinterestListAdapter
import com.app.pinterest.utils.image.DownloadImageTask
import com.makeramen.roundedimageview.RoundedImageView
import net.gahfy.mvvmposts.utils.extension.getParentActivity
import java.net.URL

//adapter list data
@BindingAdapter("adapter")
fun setAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter

    //set list delete swap
    val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
            //remember we don't make any direction here , so we dont need it
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
            (adapter as PinterestListAdapter).removeItem(viewHolder)
        }

        var swipeBackgroundColor : ColorDrawable = ColorDrawable(Color.parseColor("#FF0000"))
        var deleteIcon: Drawable = ContextCompat.getDrawable(MyApplication.instance!!,R.drawable.ic_delete)!!

        //add background color when deleted
        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {

            val itemView = viewHolder.itemView
            val iconMargin = (itemView.height - deleteIcon.intrinsicHeight)/2

            if(dX > 0){
                //swap from left to right
                swipeBackgroundColor.setBounds(itemView.left,itemView.top , dX.toInt(),itemView.bottom)
                deleteIcon.setBounds(itemView.left + iconMargin,itemView.top + iconMargin,
                    itemView.left + iconMargin + deleteIcon.intrinsicWidth,itemView.bottom - iconMargin)
            }else{
                //swap from right to left
                swipeBackgroundColor.setBounds(itemView.right + dX.toInt(),itemView.top ,itemView.right,itemView.bottom)
                deleteIcon.setBounds(itemView.right - iconMargin - deleteIcon.intrinsicWidth,
                    itemView.top + iconMargin, itemView.right - iconMargin,itemView.bottom - iconMargin)
            }

            swipeBackgroundColor.draw(c)

            c.save()

            if(dX > 0)
                c.clipRect(itemView.left,itemView.top , dX.toInt(),itemView.bottom)
            else
                c.clipRect(itemView.right + dX.toInt(),itemView.top ,itemView.right,itemView.bottom)

            c.restore()
            deleteIcon.draw(c)

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallBack)
    itemTouchHelper.attachToRecyclerView(view)

}


//responsible for visibility loader
@BindingAdapter("mutableVisibility")
fun setMutableVisibility(view: View,  visibility: MutableLiveData<Int>?) {
    val parentActivity:AppCompatActivity? = view.getParentActivity()
    if(parentActivity != null && visibility != null) {
        visibility.observe(parentActivity, Observer { value -> view.visibility = value?:View.VISIBLE})
    }
}

//responsible for set text data
@BindingAdapter("mutableText")
fun setMutableText(view: TextView,  text: MutableLiveData<String>?) {
    val parentActivity:AppCompatActivity? = view.getParentActivity()
    if(parentActivity != null && text != null) {
        text.observe(parentActivity, Observer { value -> view.text = value?:""})
    }
}

@BindingAdapter("imageUrl")
fun setImageUrl(view: ImageView, imageUrl:String) {
    if(!imageUrl.equals("")) {
        Log.e("imageUrl",imageUrl)
        DownloadImageTask(view).execute(imageUrl);
    }
}


object Bindings {
    //responsible for add animation for view
    @BindingAdapter("customOnClick")
    @JvmStatic
    fun setCustomClick(view: View,  anim: (Animation) -> Any?) {
        val animation:Animation = AnimationUtils.loadAnimation(MyApplication.instance, R.anim.bounce)
        view.animation = animation
        animation.start()
    }
}


