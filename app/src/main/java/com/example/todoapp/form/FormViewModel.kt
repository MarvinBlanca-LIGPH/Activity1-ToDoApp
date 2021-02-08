package com.example.todoapp.form

import android.content.Intent
import android.provider.MediaStore
import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.data.Task
import com.example.todoapp.util.*

class FormViewModel(
    private val fragment: FormFragment
) : ViewModel() {
    val isEdit = MutableLiveData<Boolean>()
    val taskId = MutableLiveData<Int>()
    val taskText = MutableLiveData<String>()
    val pendingChecked = MutableLiveData<Boolean>()
    val notificationTime = MutableLiveData<Int>()
    val photo = MutableLiveData<String>()
    val fiveMinutesInMillis = fragment.resources.getInteger(R.integer.five_minutes_millis)
    val tenMinutesInMillis = fragment.resources.getInteger(R.integer.ten_minutes_millis)
    val createImage = 0
    val pickImage = 1

    fun statusRadioButton(pending: Boolean) {
        pendingChecked.value = pending
    }

    fun notifyRadioButton(time: Int) {
        notificationTime.value = time
    }

    fun photoClicked() {
        var intent: Intent
        fragment.context?.let { context ->
            AppUtil.createAlertDialog(
                context,
                context.resources.getString(R.string.camera_title),
                context.resources.getString(R.string.camera_message),
                context.resources.getString(R.string.button_gallery),
                context.resources.getString(R.string.button_camera),
                negativeButtonClicked = {
                    intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    fragment.startActivityForResult(intent, pickImage)
                },
                positiveButtonClicked = {
                    intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    if (fragment.activity?.packageManager?.let { intent?.resolveActivity(it) } != null) {
                        fragment.startActivityForResult(intent, createImage)
                    }
                }
            )
        }
    }

    fun onClick() {
        fragment.activity?.let { AppUtil.hideKeyboard(it) }

        if (!taskText.value.isNullOrEmpty()) {
            val currId = if (isEdit.value == true) taskId.value else RealmUtil.getNextRealmId()
            val task = Task(
                taskText.value.toString(),
                pendingChecked.value ?: true,
                photo.value ?: "",
                notificationTime.value ?: 0,
                currId
            )

            if (isEdit.value == true) {
                RealmUtil.editItemFromRealm(task)
            } else {
                RealmUtil.insertItemToRealm(task)
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