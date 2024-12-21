package com.todosync.data

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import java.util.UUID

fun addTaskToFirebase() {
    val database =
        FirebaseDatabase.getInstance("https://todosync-25c4a-default-rtdb.europe-west1.firebasedatabase.app/")
    val tasksRef = database.getReference("tasks")

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