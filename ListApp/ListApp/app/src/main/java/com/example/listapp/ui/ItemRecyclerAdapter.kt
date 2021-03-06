package com.example.listapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.example.listapp.dataClasses.Item
import com.example.listapp.databinding.ItemLayoutBinding
import kotlin.collections.List

class ItemRecyclerAdapter (private var items:List<Item>, private var onCheckboxPressed:(onCheckboxPressed: Item) -> Unit, private val onDeleteBtnClicked:(Item) -> Unit, private val onItemCardClicked:(Item) -> Unit):RecyclerView.Adapter<ItemRecyclerAdapter.ViewHolder>(){

    class ViewHolder(val binding:ItemLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun binding(item: Item, onCheckboxPressed: (Item) -> Unit, onDeleteBtnClicked: (Item) -> Unit){
            binding.itemName.text = item.name
            binding.itemCheckbox.isChecked = item.complete
            binding.itemCheckbox.setOnClickListener {
                onCheckboxPressed(item)
            }
            binding.itemDeleteBtn.setOnClickListener{
                onDeleteBtnClicked(item)
            }
        }
    }

    override fun getItemCount(): Int{
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding(item, onCheckboxPressed, onDeleteBtnClicked)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    public fun updateItems(newItems: List<Item>){
        items = newItems
        notifyDataSetChanged()
    }
}