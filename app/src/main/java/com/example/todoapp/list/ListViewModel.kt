package com.example.todoapp.list

import androidx.lifecycle.*
import com.example.todoapp.R
import com.example.todoapp.data.Task
import com.example.todoapp.util.NavComponentUtil.navigate
import io.realm.Realm
import com.example.todoapp.list.ListAdapter.Companion.itemClicked
import com.example.todoapp.util.*

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
        setNotifications()
    }

    private fun onItemClick() {
        itemClicked = {
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
        fragment.navigate(R.id.next_action, "isEdit" to false)
    }

    private fun setNotifications() {
        val channelIdArray = arrayListOf<String>()
        val messageArray = arrayListOf<String>()
        val idArray = arrayListOf<Int?>()
        val durationArray = arrayListOf<Int>()

        _taskArray.value?.forEach {
            if (it.notificationTime > 0) {
                val channelName = fragment.resources.getString(R.string.channel_name) + it.id
                val channelId = fragment.resources.getString(R.string.channel_id) + it.id

                fragment.activity?.let { activity ->
                    AppUtil.createNotificationChannel(
                        activity,
                        channelName,
                        channelId
                    )
                }

                messageArray.add(it.newTask)
                idArray.add(it.id)
                channelIdArray.add(channelId)
                durationArray.add(it.notificationTime)
            }

            checkIfHasNotification(channelIdArray, messageArray, idArray, durationArray)
        }
    }

    private fun checkIfHasNotification(
        channelIdArray: ArrayList<String>,
        messageArray: ArrayList<String>,
        idArray: ArrayList<Int?>,
        durationArray: ArrayList<Int>
    ) {
        if (channelIdArray.isNotEmpty()) {
            AppUtil.callNotification(
                fragment.activity,
                channelIdArray,
                messageArray,
                idArray,
                durationArray
            )
        }
    }
}