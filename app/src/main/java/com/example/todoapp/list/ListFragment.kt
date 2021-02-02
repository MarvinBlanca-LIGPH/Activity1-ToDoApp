package com.example.todoapp.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.*
import com.example.todoapp.R
import com.example.todoapp.data.Task
import com.example.todoapp.databinding.FragmentListBinding

class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private lateinit var listAdapter: ListAdapter
    private var task = ArrayList<Task>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addTodoFab.setOnClickListener {
            it.findNavController().navigate(ListFragmentDirections.nextAction())
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupAdapter()
    }

    private fun setupAdapter() {
        task = arrayListOf(
            Task("FIRST TASK"),
            Task("SECOND TASK"),
            Task("THIRD TASK"),
            Task("Do assignments in English and Filipino and Araling Panlipunan and more.")
        )

        listAdapter = ListAdapter(task, activity)
        binding.recyclerView.apply {
            ItemTouchHelper(itemTouchHelper).attachToRecyclerView(this)
            adapter = listAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            TODO("Not yet implemented")
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            listAdapter.deleteItem(viewHolder)
        }
    }
}