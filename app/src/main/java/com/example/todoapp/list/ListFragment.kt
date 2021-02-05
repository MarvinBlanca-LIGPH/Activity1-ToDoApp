package com.example.todoapp.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentListBinding

class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private val listAdapter = ListAdapter()
    lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ListViewModelFactory(this)
        viewModel = ViewModelProvider(this, factory).get(ListViewModel::class.java)
        binding.listViewModel = viewModel
        binding.lifecycleOwner = this

        binding.recyclerView.apply {
            ItemTouchHelper(itemTouchHelper).attachToRecyclerView(this)
            adapter = listAdapter
        }

        observers()
    }

    private fun observers() {
        viewModel.taskArray.observe(viewLifecycleOwner, { tasks ->
            listAdapter.updateItem(tasks)
        })
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