package com.example.todoapp.util

import com.example.todoapp.data.Task
import io.realm.Realm

object RealmUtil {

    fun insertItemToRealm(task: Task) {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(task)
        realm.commitTransaction()
    }

    fun editItemFromRealm(task: Task) {
        val realm = Realm.getDefaultInstance()
        val item = realm.where(Task::class.java).equalTo("id", task.id).findAll()
        item.forEach {
            realm.beginTransaction()
            it.newTask = task.newTask
            it.isPending = task.isPending
            it.notificationTime = task.notificationTime
            it.photo = task.photo
            realm.commitTransaction()
        }
    }

    fun notificationTriggered(id: Int) {
        val realm = Realm.getDefaultInstance()
        val item = realm.where(Task::class.java).equalTo("id", id).findAll()
        item.forEach {
            realm.beginTransaction()
            it.notificationTime = 0
            realm.commitTransaction()
        }
    }

    fun deleteItemFromRealm(id: Int) {
        val realm = Realm.getDefaultInstance()
        val task = realm.where(Task::class.java).equalTo("id", id).findAll()
        realm.beginTransaction()
        task.deleteAllFromRealm()
        realm.commitTransaction()
    }

    fun getNextRealmId(): Int {
        val id = Realm.getDefaultInstance().where(Task::class.java).max("id")
        return if (id == null) {
            1
        } else {
            id.toInt() + 1
        }
    }
}