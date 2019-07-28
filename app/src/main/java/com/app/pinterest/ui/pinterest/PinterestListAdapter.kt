package com.app.pinterest.ui.pinterest

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.pinterest.R
import com.app.pinterest.databinding.ItemPinterestBinding
import com.app.pinterest.model.PinterestResponse

class PinterestListAdapter: RecyclerView.Adapter<PinterestListAdapter.ViewHolder>() {
    private lateinit var pinterestList:List<PinterestResponse>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemPinterestBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_pinterest, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pinterestList[position])
    }

    override fun getItemCount(): Int {
        return if(::pinterestList.isInitialized) pinterestList.size else 0
    }

    fun updatePinterestList(pinterestList:List<PinterestResponse>){
        this.pinterestList = pinterestList
        notifyItemRangeInserted(getItemCount(), pinterestList.size - 1);
    }

    class ViewHolder(private val binding: ItemPinterestBinding):RecyclerView.ViewHolder(binding.root){
        private val viewModel = PinterestViewModel()

        fun bind(pinterest:PinterestResponse){
            viewModel.bind(pinterest)
            binding.viewModel = viewModel
        }
    }
}