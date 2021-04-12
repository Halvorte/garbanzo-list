package com.example.listapp.service

import android.util.Log
import com.example.listapp.ui.MainActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

// Firebase code here and send/recieve from data managers
class ToDoListService {
    // database instance and reference
    // Had to manually enter database url to get it to work.
    val mDatabase = FirebaseDatabase.getInstance("https://garbanzo-list-default-rtdb.europe-west1.firebasedatabase.app/").getReference()

    companion object {
        private const val TAG = "ToDoListService"
    }

    fun writeNewListToDb(){

    }

    fun writeNewItemToDb(){

    }


    //Test
    fun basicReadWrite() {
        // [START write_message]
        // Write a message to the database
        var myRef = mDatabase


        myRef.setValue("Hello, World!")
        // [END write_message]

        // [START read_message]
        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<String>()
                Log.d(MainActivity.TAG, "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(MainActivity.TAG, "Failed to read value.", error.toException())
            }
        })
        // [END read_message]
    }
}