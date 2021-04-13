package com.example.listapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listapp.R
import com.example.listapp.databinding.ActivityMainBinding
import com.example.listapp.dataClasses.List
import com.example.listapp.logic.ListDataManager
import com.example.listapp.service.ToDoListService
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var listAdapter: ListRecyclerAdapter

    companion object {
        internal const val TAG = "MainActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // This is where the actionbar subtitle text is set.
        supportActionBar?.setSubtitle("Overview of all your lists")

        // Update lists from database
        ToDoListService.instance.readFromDb()

        binding.listOfLists.layoutManager = LinearLayoutManager(this)
        binding.listOfLists.adapter = ListRecyclerAdapter(emptyList<List>(), this::onDeleteListBtnClicked, this::onListCardClicked)

        // Holds the data
        ListDataManager.instance.onList = {
            (binding.listOfLists.adapter as ListRecyclerAdapter).updateListOfLists(it)
        }
        ListDataManager.instance.listLoad()


        //writeToDb()
        newListDialogbox()


    }

    private fun addListToDb(title: String){
        val list = List(title)
        //ListDataManager.instance.addList(list)
        ToDoListService.instance.writeNewListToDb(list)
    }

    // Function to add new Lists
    private fun newListDialogbox(){
        binding.newListButton.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.new_item_dialogbox, null)
            val editText = dialogLayout.findViewById<EditText>(R.id.dialogbox_newItem)

            with(builder){
                setTitle("Enter new List name")
                setPositiveButton("OK"){dialog, which ->
                    val newListText = editText.text.toString()
                    addListToDb(newListText)

                }
                setNegativeButton("Cancel"){dialog, which ->
                    Log.d("Main", "Negative button clicked")
                }
                setView(dialogLayout)
                show()
            }
        }
    }

    // What happens when a card is clicked
    private fun onListCardClicked(list: List):Unit{
        // send to new activity where the content of clicked list is shown.
        val intent = Intent(this, ItemActivity::class.java)
        startActivity(intent)
    }

    // What happens when the delete button is pressed
    private fun onDeleteListBtnClicked(list: List){
        ListDataManager.instance.removeList(list)
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

}