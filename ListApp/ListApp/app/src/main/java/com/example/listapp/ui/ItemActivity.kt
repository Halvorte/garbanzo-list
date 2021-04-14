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
import com.example.listapp.dataClasses.List
import com.example.listapp.logic.ItemDataManager
import com.example.listapp.service.ToDoListService
import kotlinx.android.synthetic.main.item_layout.*

class ItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListDetailsBinding

    private lateinit var listToUse: List


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get the data of the list that was selected.
        val recievedList = intent.getParcelableExtra<List>(EXTRA_LIST_INFO)
        var chosenList = List(recievedList?.title, recievedList?.uuid)
        listToUse = chosenList

        // Update lists from database
        ToDoListService.instance.readItemFromDb(chosenList)

        binding.listOfItems.layoutManager = LinearLayoutManager(this)
        binding.listOfItems.adapter = ItemRecyclerAdapter(emptyList<Item>(),this::onCheckboxPressed, this::onDeleteBtnClicked, this::onItemCardClicked)

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
                    addItemToDb(newItemText, false, listToUse)
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
        ItemDataManager.instance.removeItem(item, listToUse)
    }

    // what happens when checkbox it ticked or unticked
    private fun onCheckboxPressed(item: Item):Unit{
        ItemDataManager.instance.updateItem(item, listToUse)
    }

    fun onItemCardClicked(item: Item): Unit {
        // What happens when itemcard is clicked. In this project nothing
    }

    fun addItemToDb(name: String, complete: Boolean, listToUse: List) {
        val item = Item(name, complete)

        ToDoListService.instance.writeNewItemToDb(item, listToUse)
    }
}