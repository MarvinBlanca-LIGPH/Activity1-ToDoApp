package com.example.todoapp.form

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentFormBinding
import com.example.todoapp.util.*

class FormFragment : Fragment() {
    lateinit var binding: FragmentFormBinding
    lateinit var viewModel: FormViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_form, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = FormViewModelFactory(this)
        viewModel = ViewModelProvider(this, factory).get(FormViewModel::class.java)

        binding.formViewModel = viewModel
        binding.lifecycleOwner = this

        val isEdit = arguments?.getBoolean("isEdit") ?: false
        val taskId = arguments?.getInt("id")
        val task = arguments?.getString("task")
        val isPending = arguments?.getBoolean("isPending") ?: true
        val photo = arguments?.getString("photo")
        val notifyTime = arguments?.getInt("notifyTime") ?: 0

        viewModel.isEdit.value = isEdit

        if (isEdit) {
            viewModel.taskText.value = task
            viewModel.taskId.value = taskId
            viewModel.pendingChecked.value = isPending
            viewModel.photo.value = photo
            viewModel.notificationTime.value = notifyTime
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == viewModel.createImage && resultCode == Activity.RESULT_OK) {
            val image = data?.extras?.get("data") as Bitmap
            val imageString = AppUtil.bitMapToString(image)
            viewModel.photo.value = imageString
        } else if (requestCode == viewModel.pickImage && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data
            val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, imageUri)
            val imageString = AppUtil.bitMapToString(bitmap)
            viewModel.photo.value = imageString
        }
    }
}