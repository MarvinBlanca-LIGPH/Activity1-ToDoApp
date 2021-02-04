package com.example.todoapp.form

import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.data.Task
import com.example.todoapp.util.RealmUtil.getNextRealmId
import com.example.todoapp.util.RealmUtil.insertItemToRealm
import io.realm.Realm

class FormViewModel(
    private val fragment: FormFragment
) : ViewModel() {
    val taskText = MutableLiveData<String>()
    var isPending: Boolean = true
    var notifyFive: Boolean = false
    var notifyTen: Boolean = false

    init {
    }

    fun checkedRadioButton(pending: Boolean) {
        isPending = pending
    }

    fun onClick() {
        if (!taskText.value.isNullOrEmpty()) {
            val task = Task(
                taskText.value.toString(),
                isPending,
                "",
                notifyFive,
                notifyTen,
                getNextRealmId()
            )
            insertItemToRealm(task)
            fragment.findNavController().navigate(FormFragmentDirections.backToMain())

        } else {
            Toast.makeText(fragment.context, fragment.resources.getString(R.string.invalid), Toast.LENGTH_SHORT).show()
        }
    }
}