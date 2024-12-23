package com.todosync.data

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.UUID

class FirebaseInstance() {

    private val database = FirebaseDatabase.getInstance("https://todosync-25c4a-default-rtdb.europe-west1.firebasedatabase.app/")
    private val tasksRef = database.getReference("tasks")

    fun addTaskToFirebase() {

        val taskId = UUID.randomUUID().toString() // Genera un ID único
        val task = Task(id = taskId, title = "Nueva Tarea", completed = false)

        tasksRef.child(taskId).setValue(task)
            .addOnSuccessListener {
                Log.d("Firebase", "Tarea añadida exitosamente")
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Error al añadir la tarea", e)
            }
    }

    @Composable
    fun observeTasks(): List<Task> {
        var tasks by remember { mutableStateOf<List<Task>>(emptyList()) }

        LaunchedEffect(Unit) {
            tasksRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val taskList = snapshot.children.mapNotNull { it.getValue(Task::class.java) }
                    tasks = taskList
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Error fetching tasks", error.toException())
                }
            })
        }

        return tasks
    }

}