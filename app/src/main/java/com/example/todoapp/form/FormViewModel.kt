package com.example.todoapp.form

import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.data.Task
import com.example.todoapp.util.HideKeyboardUtil
import com.example.todoapp.util.RealmUtil.editItemFromRealm
import com.example.todoapp.util.RealmUtil.getNextRealmId
import com.example.todoapp.util.RealmUtil.insertItemToRealm

class FormViewModel(
    private val fragment: FormFragment
) : ViewModel() {
    var isEdit = MutableLiveData<Boolean>()
    var taskId = MutableLiveData<Int>()
    val taskText = MutableLiveData<String>()
    var isPending = MutableLiveData<Boolean>()
    val pendingChecked = MutableLiveData<Boolean>()
    var photo: String? = ""
    var notificationTime = MutableLiveData<Int>()

    fun statusRadioButton(pending: Boolean) {
        isPending.value = pending
    }

    fun notifyRadioButton(time: Int) {
        notificationTime.value= time
    }

    fun onClick() {
        fragment.activity?.let { HideKeyboardUtil.hideKeyboard(it) }

        if (!taskText.value.isNullOrEmpty()) {
            val currId = if (isEdit.value == true) taskId.value else getNextRealmId()
            val task = Task(
                taskText.value.toString(),
                isPending.value ?: true,
                "",
                notificationTime.value ?: 0,
                currId
            )

            if (isEdit.value == true) {
                editItemFromRealm(task)
            } else {
                insertItemToRealm(task)
            }
            fragment.findNavController().navigate(FormFragmentDirections.backToMain())
        } else {
            Toast.makeText(
                fragment.context,
                fragment.resources.getString(R.string.invalid),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}