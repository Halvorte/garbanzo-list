package com.example.listapp

class ToDoModel {

    companion object Factory {
        fun createList(): ToDoModel = ToDoModel()
    }

    var UID: String? = null
    var itemDataText: String? = null
    var complete: Boolean? = false

}