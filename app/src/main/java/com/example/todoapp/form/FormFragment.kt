package com.example.todoapp.form

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentFormBinding

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
            viewModel.isPending.value = isPending
            viewModel.photo = photo
            viewModel.notificationTime.value = notifyTime
        }
    }
}