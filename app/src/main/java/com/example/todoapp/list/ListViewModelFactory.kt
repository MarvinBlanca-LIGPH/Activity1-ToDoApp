package com.example.todoapp.list

import androidx.lifecycle.*

class ListViewModelFactory(
    private val fragment: ListFragment
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java))
            return ListViewModel(fragment) as T
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}