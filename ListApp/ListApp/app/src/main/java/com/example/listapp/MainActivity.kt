package com.example.listapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listapp.databinding.ActivityMainBinding
import com.example.listapp.lists.List
import com.example.listapp.lists.ListRecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.*

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
        // This is where the actionbar subtitle text is set.
        supportActionBar?.setSubtitle("Overview of all your lists")

        binding.listOfLists.layoutManager = LinearLayoutManager(this)
        binding.listOfLists.adapter = ListRecyclerAdapter(listCollection, this::onListCardClicked)


        // Starts the second activity when the floating action button is pressed
        binding.newListButton.setOnClickListener {
            val intent = Intent(this, ListDetailsActivity::class.java)
            startActivity(intent)
        }

        // Update list of lists
        //listAdapter.updateListOfLists(listOf(List()))

    }

    private fun addList(title: String){

    }

    // What happens when a card is cliced
    private fun onListCardClicked(list:List):Unit{
        // send to new activity where the content of clicked list is shown.
    }

}