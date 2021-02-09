package com.example.todoapp.form

import androidx.lifecycle.*

class FormViewModelFactory(
    private val fragment: FormFragment
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FormViewModel::class.java))
            return FormViewModel(fragment) as T
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}