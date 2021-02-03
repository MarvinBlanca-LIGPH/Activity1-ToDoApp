package com.example.todoapp.data

import io.realm.RealmObject
import io.realm.annotations.*

open class Task(
    var newTask: String = "",
    var isPending: Boolean = true,
    var photo: String = "",
    var isNotifyFive: Boolean = false,
    var isNotifyTen: Boolean = false,
    @PrimaryKey
    var id: Int? = null
) : RealmObject()
