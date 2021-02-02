package com.example.todoapp.data

data class Task(
    val newTask: String,
    val isPending: Boolean = true,
    val photo: String = "",
    val notify: ArrayList<String> = arrayListOf(""),
    val id: Int? = null
)
