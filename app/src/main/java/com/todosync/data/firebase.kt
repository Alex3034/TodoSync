package com.todosync.data

import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.todosync.domain.Task
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseInstance(context: Context) {

    private val database = FirebaseDatabase.getInstance("https://todosync-25c4a-default-rtdb.europe-west1.firebasedatabase.app/")
    private val tasksRef = database.getReference("tasks")

    suspend fun addTask(task: Task) = suspendCoroutine<Unit> { continuation ->
        tasksRef.child(task.id).setValue(task)
            .addOnSuccessListener { continuation.resume(Unit) }
            .addOnFailureListener { continuation.resumeWithException(it) }
    }

    fun observeTasksFlow(): Flow<List<Task>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val taskList = snapshot.children.mapNotNull { it.getValue(Task::class.java) }
                trySend(taskList)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        tasksRef.addValueEventListener(listener)
        awaitClose { tasksRef.removeEventListener(listener) }
    }

    suspend fun updateTask(task: Task) = suspendCoroutine<Unit> { continuation ->
        tasksRef.child(task.id).setValue(task)
            .addOnSuccessListener { continuation.resume(Unit) }
            .addOnFailureListener { continuation.resumeWithException(it) }
    }

    fun deleteTask(task: Task) {
        tasksRef.child(task.id).removeValue()
    }
}