package com.example.listapp.lists

import com.example.listapp.dataClasses.List

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

    companion object{
        val instance = ListDataManager()
    }

}