package com.example.todoapp.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentListBinding
import com.example.todoapp.list.ListAdapter.Companion.itemClicked
import com.example.todoapp.util.NavComponentUtil.navigate

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

        itemClicked = {
            Toast.makeText(activity, it.toString(), Toast.LENGTH_SHORT).show()
            navigate(
                R.id.next_action,
                "isEdit" to true,
                "id" to it.id,
                "task" to it.newTask,
                "isPending" to it.isPending,
                "photo" to it.photo,
                "isNotifyFive" to it.isNotifyFive,
                "isNotifyTen" to it.isNotifyTen,
            )
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