package com.example.listapp.logic

import android.content.ContentValues.TAG
import android.util.Log
import com.example.listapp.dataClasses.List
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ListDataManager {

    // declare database reference
    private lateinit var database :DatabaseReference

    private lateinit var id: String

    private lateinit var listCollection: MutableList<List>

    var onList: ((kotlin.collections.List<List>) -> Unit)? = null
    var onListUpdate: ((list: List) -> Unit)? = null

    fun initializeDbRef(){
        database = Firebase.database.reference
        id = database.push().key.toString()
    }


    // function to write new list to database
    fun writeNewList(listId: String, title: String){
        val list = List(title)
        initializeDbRef()

        /*
        val mDatabase = FirebaseDatabase.getInstance()
        val mDbRef = mDatabase.getReference("Donor/Name")
        mDbRef.setValue("Test Halvor name").addOnFailureListener { e -> Log.d(TAG, e.localizedMessage) }
        */

        //val database = Firebase.database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("List")
        myRef.setValue("liste med lister")

        //val key = myRef.push().key?:""

        //myRef.child(key).setValue(list).addOnSuccessListener { println("write success") }.addOnFailureListener { println("failed to write") }

        //initializeDbRef()

        //database.child("Lists").child(id).setValue(list)
    }


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