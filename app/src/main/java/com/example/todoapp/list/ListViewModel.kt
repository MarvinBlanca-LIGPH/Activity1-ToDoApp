package com.example.todoapp.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.todoapp.data.Task
import io.realm.Realm

class ListViewModel(
    private val activity: ListFragment
) : ViewModel() {
    private val realm = Realm.getDefaultInstance()
    private val _taskArray = MutableLiveData<ArrayList<Task>>()
    val taskArray: LiveData<ArrayList<Task>>
        get() = _taskArray

    init {
        getTasksFromRealm()
    }

    private fun getTasksFromRealm() {
        val task = arrayListOf(
            Task("FIRST TASK", id = 1),
            Task("SECOND TASK", id = 2),
            Task("THIRD TASK", id = 3),
            Task("Do assignments in English and Filipino and Araling Panlipunan and more.", id = 4)
        )

        realm.beginTransaction()
        task.forEach {
            realm.copyToRealmOrUpdate(it)
        }
        realm.commitTransaction()

        val realmData = realm.where(Task::class.java).findAll()
        val res = arrayListOf<Task>()
        realmData.forEach {
            res.add(it)
        }
        _taskArray.value = res
    }

    fun addClicked() {
        activity.findNavController().navigate(ListFragmentDirections.nextAction())
    }
}