package com.example.listapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listapp.databinding.ActivityMainBinding
import com.example.listapp.lists.List
import com.example.listapp.lists.ListRecyclerAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var listAdapter: ListRecyclerAdapter

    private var listCollection: MutableList<List> = mutableListOf(
        List("Handleliste", "melk"),
        List("Navn", "Halvor"),
        List("Huskelapp", "skolearbeid"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.listOfLists.layoutManager = LinearLayoutManager(this)
        binding.listOfLists.adapter = ListRecyclerAdapter(listCollection, this::onListCardClicked)


        binding.newListButton.setOnClickListener {

        }

        // Update list of lists
        //listAdapter.updateListOfLists(listOf(List()))

    }

    // What happens when a card is cliced
    private fun onListCardClicked(list:List):Unit{
        // send to new activity where the content of clicked list is shown.
    }

}