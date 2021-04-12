package com.example.listapp.logic

import android.content.ContentValues.TAG
import android.util.Log
import com.example.listapp.dataClasses.List
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ListDataManager {

    private lateinit var listCollection: MutableList<List>

    var onList: ((kotlin.collections.List<List>) -> Unit)? = null
    var onListUpdate: ((list: List) -> Unit)? = null


    fun listLoad(){

        listCollection = mutableListOf(
                List("Handleliste"),
                List("Navn"),
                List("Utgifter")
        )

        onList?.invoke(listCollection)

    }

    fun addList(list: List){
        listCollection.add(list)
        onList?.invoke(listCollection)
    }

    fun removeList(list: List){
        listCollection.remove(list)
        onList?.invoke(listCollection)
    }

    companion object{
        val instance = ListDataManager()
    }

}