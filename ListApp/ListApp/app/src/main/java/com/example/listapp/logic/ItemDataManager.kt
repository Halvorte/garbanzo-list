package com.example.listapp.logic
import com.example.listapp.dataClasses.Item
import com.example.listapp.service.ToDoListService
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

    // Adds items to the itemCollection
    fun addItem(item: Item){
        itemCollection.add(item)
        onItems?.invoke(itemCollection)
    }

    fun removeItem(item: Item, list:com.example.listapp.dataClasses.List){
        ToDoListService.instance.deleteItemFromDb(item, list)
        /*
        itemCollection.remove(item)
        onItems?.invoke(itemCollection)

         */
    }

    fun clearItems(){
        itemCollection.clear()
        onItems?.invoke(itemCollection)
    }

    companion object{
        val instance = ItemDataManager()
    }

}