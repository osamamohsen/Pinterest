package com.app.pinterest.ui.pinterest

import android.databinding.DataBindingUtil
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.pinterest.R
import com.app.pinterest.app.MyApplication
import com.app.pinterest.databinding.ItemPinterestBinding
import com.app.pinterest.model.PinterestResponse

class PinterestListAdapter: RecyclerView.Adapter<PinterestListAdapter.ViewHolder>() {
    // fetch list data
    private lateinit var pinterestList:ArrayList<PinterestResponse>
    private var removeItem: PinterestResponse? = null
    private var removePosition: Int = 0
    private var networkStrength: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // binding item_pinterest layout
        val binding: ItemPinterestBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_pinterest, parent, false)
        return ViewHolder(binding)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pinterestList[position],networkStrength)
    }

    override fun getItemCount(): Int {
        //return data count if exist or 0
        return if(::pinterestList.isInitialized) pinterestList.size else 0
    }

    fun updatePinterestList(pinterestList:ArrayList<PinterestResponse>,networkStrength:String){
        //update data after call service again in scroll , and notify list which end with
        this.pinterestList = pinterestList
        this.networkStrength = networkStrength
        notifyItemRangeInserted(getItemCount(), pinterestList.size - 1);
    }

    fun removeItem(viewHolder: RecyclerView.ViewHolder) {
        removePosition = viewHolder.adapterPosition
        removeItem = pinterestList[removePosition]
        pinterestList.removeAt(viewHolder.adapterPosition)
        notifyItemRemoved(viewHolder.adapterPosition)

        Snackbar.make(viewHolder.itemView,"${removeItem!!.user.name} had been deleted",Snackbar.LENGTH_LONG)
            .setActionTextColor(Color.WHITE)
            .setAction(MyApplication.instance?.getText(R.string.undo)){
                pinterestList.add(removePosition, removeItem!!)
                notifyItemInserted(removePosition)
            }.show()


    }


    class ViewHolder(private val binding: ItemPinterestBinding):RecyclerView.ViewHolder(binding.root){
        private val viewModel = PinterestViewModel()

        //bint
        fun bind(pinterest:PinterestResponse,networkStrength: String){
            viewModel.bind(pinterest,networkStrength)
            binding.viewModel = viewModel
        }
    }
}