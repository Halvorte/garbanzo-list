package com.example.listapp.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listapp.dataClasses.List
import com.example.listapp.databinding.ListOverviewLayoutBinding

// Recycler Adapter
class ListRecyclerAdapter(private var lists:kotlin.collections.List<List>, private val onListCardClicked:(List) -> Unit) : RecyclerView.Adapter<ListRecyclerAdapter.ViewHolder>() {

    class ViewHolder(val binding:ListOverviewLayoutBinding):RecyclerView.ViewHolder(binding.root) {
        fun binding(list: List, onListCardClicked:(List) -> Unit) {
            // Bind to layout id
            binding.overViewTitle.text = list.title
            binding.listOverviewCardView.setOnClickListener{
                onListCardClicked(list)
            }

        }
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val list = lists[position]
        holder.binding(list, onListCardClicked)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListOverviewLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    public fun updateListOfLists(newLists: kotlin.collections.List<List>){
        lists = newLists
        notifyDataSetChanged()
    }
}