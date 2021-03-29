package com.example.listapp.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listapp.databinding.ListOverviewLayoutBinding

// Recycler Adapter
class ListRecyclerAdapter(private val lists:MutableList<List>, private val onListCardClicked:(List) -> Unit) : RecyclerView.Adapter<ListRecyclerAdapter.ViewHolder>() {

    class ViewHolder(val binding:ListOverviewLayoutBinding):RecyclerView.ViewHolder(binding.root) {
        fun binding(list: List) {
            // Bind to layout id
            binding.overViewTitle.text = list.title

        }
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val list = lists[position]
        holder.binding(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListOverviewLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    public fun updateListOfLists(newLists:MutableList<List>){
        lists.clear()
        lists.addAll(newLists)
        notifyDataSetChanged()
    }
}