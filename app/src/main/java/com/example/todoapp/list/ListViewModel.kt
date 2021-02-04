package com.example.todoapp.list

import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.data.Task
import com.example.todoapp.util.NavComponentUtil
import com.example.todoapp.util.NavComponentUtil.navigate
import io.realm.Realm

class ListViewModel(
    private val fragment: ListFragment
) : ViewModel() {
    private val realm = Realm.getDefaultInstance()
    private val _taskArray = MutableLiveData<ArrayList<Task>>()
    val taskArray: LiveData<ArrayList<Task>>
        get() = _taskArray

    init {
        getTasksFromRealm()
    }

    private fun getTasksFromRealm() {
        val realmData = realm.where(Task::class.java).findAll()
        val res = arrayListOf<Task>()
        realmData.forEach {
            res.add(it)
        }
        _taskArray.value = res
    }

    fun addClicked() {
//        val bundle = bundleOf("isEdit" to false)
//        fragment.findNavController().navigate(R.id.next_action, bundle)
        fragment.navigate(R.id.next_action,"isEdit" to false)
    }
}