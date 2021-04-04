package com.example.listapp.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listapp.dataClasses.Item
import com.example.listapp.databinding.ItemLayoutBinding
import kotlin.collections.List

class ItemRecyclerAdapter (private var items:List<Item>, private val onItemCardClicked:(Item) -> Unit):RecyclerView.Adapter<ItemRecyclerAdapter.ViewHolder>(){

    class ViewHolder(val binding:ItemLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun binding(item: Item){
            binding.itemName.text = item.name
            binding.itemCheckbox.isChecked = item.complete
        }
    }

    override fun getItemCount(): Int{
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    public fun updateItems(newItems: List<Item>){
        items = newItems
        notifyDataSetChanged()
    }
}
