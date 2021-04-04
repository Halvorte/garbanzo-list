package com.example.listapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listapp.databinding.ActivityMainBinding
import com.example.listapp.dataClasses.List
import com.example.listapp.lists.ListDataManager
import com.example.listapp.lists.ListRecyclerAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var listAdapter: ListRecyclerAdapter

    companion object {
        private const val TAG = "MainActivity"
    }

    /*
    private var listCollection: MutableList<List> = mutableListOf(
        List("Handleliste"),
        List("Navn"),
        List("Huskelapp")
    )
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // This is where the actionbar subtitle text is set.
        supportActionBar?.setSubtitle("Overview of all your lists")

        binding.listOfLists.layoutManager = LinearLayoutManager(this)
        binding.listOfLists.adapter = ListRecyclerAdapter(emptyList<List>(), this::onListCardClicked)

        // Holds the data
        ListDataManager.instance.onList = {
            (binding.listOfLists.adapter as ListRecyclerAdapter).updateListOfLists(it)
        }
        ListDataManager.instance.listLoad()


        // Starts the second activity when the floating action button is pressed
        /*
        binding.newListButton.setOnClickListener {
            val intent = Intent(this, ListDetailsActivity::class.java)
            startActivity(intent)
        }
         */


        basicReadWrite()

    }

    private fun addList(title: String){

    }

    // What happens when a card is clicked
    private fun onListCardClicked(list: List):Unit{
        // send to new activity where the content of clicked list is shown.
        val intent = Intent(this, ListDetailsActivity::class.java)
        startActivity(intent)
    }

    fun basicReadWrite() {
        // [START write_message]
        // Write a message to the database
        val database = Firebase.database
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World!")
        // [END write_message]

        // [START read_message]
        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<String>()
                Log.d(TAG, "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
        // [END read_message]
    }

}