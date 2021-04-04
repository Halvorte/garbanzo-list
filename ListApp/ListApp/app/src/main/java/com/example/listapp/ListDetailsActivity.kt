package com.example.listapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listapp.databinding.ActivityListDetailsBinding
import com.example.listapp.dataClasses.Item
import com.example.listapp.lists.ItemDataManager
import com.example.listapp.lists.ItemRecyclerAdapter
import com.google.firebase.database.DatabaseReference

class ListDetailsActivity : AppCompatActivity() {

    lateinit var database: DatabaseReference

    private lateinit var binding: ActivityListDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //database = FirebaseDatabase.getInstance().reference

        binding.listOfItems.layoutManager = LinearLayoutManager(this)
        binding.listOfItems.adapter = ItemRecyclerAdapter(emptyList<Item>(), this::onItemCardClicked)

        // Holds the data.
        ItemDataManager.instance.onItems = {
            (binding.listOfItems.adapter as ItemRecyclerAdapter).updateItems(it)
        }
        ItemDataManager.instance.itemLoad()

        binding.addItemButton.setOnClickListener {
            // Add functionality to add another item to list.
        }

        showNewItemDialogbox()

    }

    // function to add another item to the list
    private fun showNewItemDialogbox(){
        binding.addItemButton.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.new_item_dialogbox, null)
            val editText = dialogLayout.findViewById<EditText>(R.id.dialogbox_newItem)

            with(builder){
                setTitle("Enter new to-do item")
                setPositiveButton("OK"){dialog, which ->
                    val newItemText = editText.text.toString()
                    addItem(newItemText, false)
                }
                setNegativeButton("Cancel"){dialog, which ->
                    Log.d("Main", "Negative button clicked")
                }
                setView(dialogLayout)
                show()
            }
        }
    }

    fun onItemCardClicked(item: Item): Unit {
        // What happends when itemcard is clicked. In this project nothing
    }

    fun addItem(name: String, complete: Boolean) {
        val item = Item(name, complete)
        ItemDataManager.instance.addItem(item)
    }
}