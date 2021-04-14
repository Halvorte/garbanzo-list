package com.example.listapp.service

import android.util.Log
import com.example.listapp.dataClasses.Item
import com.example.listapp.dataClasses.List
import com.example.listapp.logic.ItemDataManager
import com.example.listapp.logic.ListDataManager
import com.example.listapp.ui.MainActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

// Firebase code here and send/recieve from data managers
class ToDoListService {

    // database instance and reference
    // Had to manually enter database url to get it to work.
    private val mDatabase = FirebaseDatabase.getInstance("https://garbanzo-list-default-rtdb.europe-west1.firebasedatabase.app/").getReference()

    private val listsRef = FirebaseDatabase.getInstance("https://garbanzo-list-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Lists")


    companion object {
        private const val TAG = "ToDoListService"
        val instance = ToDoListService()
    }

    // function to write a new list to database
    fun writeNewListToDb(list: List) {
        val newList = List(list.title)
        val key = mDatabase.child("Lists").push().key
        if (key != null) {
            newList.uuid = key
            //mDatabase.child("Lists").child(key).setValue(newList)
        }
        if (key != null) {
            mDatabase.child("Lists").child(key).setValue(newList)
        }
    }

    // function to write new item to database
    fun writeNewItemToDb(item: Item, list: List) {
        val newItem:Item = item
        val listId = list.uuid.toString()

        listsRef.child(listId).child("Items").child(newItem.name.toString()).setValue(newItem)
    }

    // function to update list in database
    fun updateListToDb() {

    }

    // function to update item in database
    fun updateItemToDb(item: Item, list: List) {
        val listId = list.uuid.toString()
        val newBool = !item.complete
        val updatedItem = Item(item.name, newBool)
        listsRef.child(listId).child("Items").child(item.name.toString()).setValue(updatedItem)
    }

    // function to delete list from the database
    fun deleteListFromDb(list: List) {
        // Get the reference of the list to delete. Setting the value to null deletes it from the database.
        listsRef.child(list.uuid.toString()).setValue(null)
        // delete

    }

    // function to delete item from a specified list in the database.
    fun deleteItemFromDb(item: Item, list: List){
        listsRef.child(list.uuid.toString()).child("Items").child(item.name.toString()).setValue(null)
    }

    // Read Lists from the database.
    fun readFromDb() {
        val list = ArrayList<List>()
        listsRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot!!.exists()) {
                    list.clear()

                    // Clear the list that gets loaded to the gui so it only contains the the lists from the database.
                    ListDataManager.instance.clearList()

                    for (e in dataSnapshot.children) {
                        val post = e.getValue(List::class.java)
                        list.add(post!!)
                        val toList = List(post.title, post.uuid)
                        ListDataManager.instance.addList(toList)
                    }
                }



                Log.d(TAG, "got Items")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "loadItem:onCancelled")//, databaseError.toException())
            }
        })
    }


    // read items from database
    fun readItemFromDb(list: List){
        val items = ArrayList<Item>()
        val listId = list.uuid

        if (listId != null) {
            listsRef.child(listId).child("Items").addValueEventListener(object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    if (dataSnapshot!!.exists()) {
                        items.clear()

                        // Clear the list of items so only items from the database is shown.
                        ItemDataManager.instance.clearItems()

                        for (e in dataSnapshot.children) {
                            var value = e.getValue() as HashMap<*,*>
                            var name = value["name"].toString()
                            var complete = value["complete"]

                            val post: Item = Item(name, complete as Boolean)
                            //val post = e.getValue(Item::class.java)
                            items.add(post!!)
                            val toItem = Item(post.name, post.complete)
                            ItemDataManager.instance.addItem(toItem)
                        }
                    }
                    Log.d(TAG, "got Items")
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(TAG, "loadItems:onCancelled")//, databaseError.toException())
                }
            })
        }
    }

    // Function to write to database to set it up and give it test data.
    fun writeToDb(){
        val mDatabase = FirebaseDatabase.getInstance("https://garbanzo-list-default-rtdb.europe-west1.firebasedatabase.app/").getReference()
        val lists: kotlin.collections.List<List> = mutableListOf(
                List("Salads"),
                List("TV's"),
                List("Movies"),
                List("Programming languages")
        )
        lists.forEach{
            val key = mDatabase.child("Lists").push().key
            if (key != null) {
                it.uuid = key
            }
            if (key != null) {
                mDatabase.child("Lists").child(key).setValue(it)
            }
        }
        //mDatabase.child("").setValue("yoTest #1")
    }

    // Function to test and set up items in the database.
    fun writeItemToDb(){
        val mDatabase = FirebaseDatabase.getInstance("https://garbanzo-list-default-rtdb.europe-west1.firebasedatabase.app/").getReference()
        val items: kotlin.collections.List<Item> = mutableListOf(
                Item("RED", false),
                Item("James Bond", false),
                Item("Aquaman", true),
                Item("Kingsman", false)
        )
        items.forEach{
            //var name = it.name.toString()
            mDatabase.child("Lists").child("-MYBzQLMOE3unsHZtjow").child("Items").child(it.name.toString()).setValue(it)
        }


    }


}
