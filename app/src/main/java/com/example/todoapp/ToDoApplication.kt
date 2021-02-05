package com.example.todoapp

import android.app.Application
import io.realm.*

class ToDoApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name("task_db")
            .schemaVersion(1)
            .build()
        Realm.setDefaultConfiguration(config)
    }
}