package com.example.listapp.logic
import com.example.listapp.dataClasses.Item
import kotlin.collections.List

class ItemDataManager {

    private lateinit var itemCollection: MutableList<Item>

    var onItems: ((List<Item>) -> Unit)? = null

    fun itemLoad(){
        itemCollection = mutableListOf(
                Item("Homework", false),
                Item("Clean", false),
                Item("make food", true),
                Item("test1", true),
                Item("test2", true)
        )

        onItems?.invoke(itemCollection)
    }

    fun addItem(item: Item){
        itemCollection.add(item)
        onItems?.invoke(itemCollection)
    }

    companion object{
        val instance = ItemDataManager()
    }

}