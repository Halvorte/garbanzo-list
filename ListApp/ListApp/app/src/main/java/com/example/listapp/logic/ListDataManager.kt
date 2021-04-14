package com.example.listapp.logic

import android.content.ContentValues.TAG
import android.util.Log
import com.example.listapp.dataClasses.List
import com.example.listapp.service.ToDoListService
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ListDataManager {

    private lateinit var listCollection: MutableList<List>

    var onList: ((kotlin.collections.List<List>) -> Unit)? = null
    var onListUpdate: ((list: List) -> Unit)? = null


    // Put the data from the database in the listCollection. The app gets lists from listColletion.
    fun listLoad(){
        listCollection = mutableListOf()
        onList?.invoke(listCollection)
    }

    // Add list to the list Collection
    fun addList(list: List){
        listCollection.add(list)
        onList?.invoke(listCollection)
    }

    // Remove selected list from database
    fun removeList(list: List){
        var listToDelete = List(list.title, list.uuid)
        ToDoListService.instance.deleteListFromDb(listToDelete)
    }

    // Clear listLoad so it can be loaded only with lists from the database
    fun clearList(){
        listCollection.clear()
        onList?.invoke(listCollection)
    }

    companion object{
        val instance = ListDataManager()
    }

}