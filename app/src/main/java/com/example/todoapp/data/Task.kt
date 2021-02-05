package com.example.todoapp.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Task(
    var newTask: String = "",
    var isPending: Boolean = true,
    var photo: String = "",
    var notificationTime: Int = 0,
    @PrimaryKey
    var id: Int? = null
) : RealmObject()
