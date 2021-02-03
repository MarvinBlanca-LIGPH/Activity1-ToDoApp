package com.example.todoapp.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val activity: ListFragment): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ListViewModel::class.java))
            return ListViewModel(activity) as T
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}