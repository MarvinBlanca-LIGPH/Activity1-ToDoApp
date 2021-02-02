package com.example.todoapp.list

import android.app.AlertDialog
import android.view.*
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.data.Task
import com.example.todoapp.databinding.ListItemBinding

class ListAdapter(task: ArrayList<Task>, val activity: FragmentActivity?) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    private val taskArray: ArrayList<Task> = task
    private var removedPosition = 0
    private lateinit var removedItem: Task

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(taskArray[position])
    }

    override fun getItemCount(): Int {
        return taskArray.size
    }

    fun deleteItem(viewHolder: RecyclerView.ViewHolder) {
        removedPosition = viewHolder.adapterPosition
        removedItem = taskArray[removedPosition]

        taskArray.removeAt(removedPosition)
        notifyItemRemoved(removedPosition)

        AlertDialog.Builder(activity)
            .setTitle("Delete")
            .setMessage("Are you sure you want to delete this task?")
            .setNegativeButton(
                "No"
            ) { _, _ ->
                taskArray.add(removedPosition,removedItem)
                notifyItemInserted(removedPosition)
            }
            .setPositiveButton("Yes") {
                _, _ ->
            }
            .create()
            .show()
    }

    class ViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.taskText.text = task.newTask
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}