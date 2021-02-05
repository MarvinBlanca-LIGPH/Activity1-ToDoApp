package com.example.todoapp.list

import android.widget.Toast
import androidx.lifecycle.*
import com.example.todoapp.R
import com.example.todoapp.data.Task
import com.example.todoapp.util.NavComponentUtil.navigate
import io.realm.Realm
import com.example.todoapp.list.ListAdapter.Companion.itemClicked

class ListViewModel(
    private val fragment: ListFragment
) : ViewModel() {
    private val realm = Realm.getDefaultInstance()
    private val _taskArray = MutableLiveData<ArrayList<Task>>()
    val taskArray: LiveData<ArrayList<Task>>
        get() = _taskArray

    init {
        getTasksFromRealm()
        onItemClick()
    }

    private fun onItemClick() {
        itemClicked = {
            Toast.makeText(fragment.context, it.toString(), Toast.LENGTH_SHORT).show()
            fragment.navigate(
                R.id.next_action,
                "isEdit" to true,
                "id" to it.id,
                "task" to it.newTask,
                "isPending" to it.isPending,
                "photo" to it.photo,
                "notifyTime" to it.notificationTime
            )
        }
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
        fragment.navigate(R.id.next_action,"isEdit" to false)
    }
}