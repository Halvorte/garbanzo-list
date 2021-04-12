package com.example.listapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.LogPrinter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listapp.R
import com.example.listapp.databinding.ActivityListDetailsBinding
import com.example.listapp.dataClasses.Item
import com.example.listapp.logic.ItemDataManager
import kotlinx.android.synthetic.main.item_layout.*

class ItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.listOfItems.layoutManager = LinearLayoutManager(this)
        binding.listOfItems.adapter = ItemRecyclerAdapter(emptyList<Item>(), this::onDeleteBtnClicked, this::onItemCardClicked)

        // Holds the data.
        ItemDataManager.instance.onItems = {
            (binding.listOfItems.adapter as ItemRecyclerAdapter).updateItems(it)
        }
        ItemDataManager.instance.itemLoad()


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

    private fun onDeleteBtnClicked(item: Item):Unit{
        //println("clicked $item")
        //Toast.makeText(applicationContext, "delete btn clicked $item", Toast.LENGTH_SHORT).show()

        ItemDataManager.instance.removeItem(item)
    }

    fun onItemCardClicked(item: Item): Unit {
        // What happends when itemcard is clicked. In this project nothing
    }

    fun addItem(name: String, complete: Boolean) {
        val item = Item(name, complete)
        ItemDataManager.instance.addItem(item)
    }
}