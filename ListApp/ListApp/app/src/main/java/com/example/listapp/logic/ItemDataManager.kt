package com.example.listapp.logic
import com.example.listapp.dataClasses.Item
import com.example.listapp.service.ToDoListService
import kotlin.collections.List

class ItemDataManager {

    private lateinit var itemCollection: MutableList<Item>

    var onItems: ((List<Item>) -> Unit)? = null

    fun itemLoad(){
        itemCollection = mutableListOf()
        onItems?.invoke(itemCollection)
    }

    // Adds items to the itemCollection
    fun addItem(item: Item){
        itemCollection.add(item)
        onItems?.invoke(itemCollection)
    }

    fun removeItem(item: Item, list:com.example.listapp.dataClasses.List){
        ToDoListService.instance.deleteItemFromDb(item, list)
    }

    fun clearItems(){
        itemCollection.clear()
        onItems?.invoke(itemCollection)
    }

    fun updateItem(item: Item, list: com.example.listapp.dataClasses.List){
        ToDoListService.instance.updateItemToDb(item, list)
    }

    companion object{
        val instance = ItemDataManager()
    }
}