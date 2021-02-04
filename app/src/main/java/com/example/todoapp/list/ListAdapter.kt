package com.example.todoapp.list

import android.app.AlertDialog
import android.content.Context
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.data.Task
import com.example.todoapp.databinding.ListItemBinding
import com.example.todoapp.util.RealmUtil.deleteItemFromRealm

class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    private var taskArray: ArrayList<Task> = arrayListOf()
    private var removedPosition = 0
    private lateinit var removedItem: Task
    lateinit var context: Context

    companion object {
        var itemClicked: ((task: Task) -> Unit)? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(taskArray[position])
    }

    fun updateItem(taskArray: ArrayList<Task>) {
        this.taskArray = taskArray
        notifyDataSetChanged()
    }

    fun deleteItem(viewHolder: RecyclerView.ViewHolder) {
        removedPosition = viewHolder.adapterPosition
        removedItem = taskArray[removedPosition]

        taskArray.removeAt(removedPosition)
        notifyItemRemoved(removedPosition)

        AlertDialog.Builder(context)
            .setTitle("Delete")
            .setMessage("Are you sure you want to delete this task?")
            .setNegativeButton(
                "No"
            ) { _, _ ->
                taskArray.add(removedPosition, removedItem)
                notifyItemInserted(removedPosition)
            }
            .setPositiveButton("Yes") { _, _ ->
                removedItem.id?.let { deleteItemFromRealm(it) }
            }
            .create()
            .show()
    }

    class ViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.taskText.apply {
                text = task.newTask
                setOnClickListener { itemClicked?.invoke(task) }
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }

    override fun getItemCount(): Int {
        return taskArray.size
    }
}