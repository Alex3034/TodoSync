package com.todosync.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.todosync.data.Task
import com.todosync.data.FirebaseInstance

@Composable
fun ListScreen(firebaseInstance: FirebaseInstance) {

    val tasks = firebaseInstance.observeTasks()

    Screen() {
        Scaffold(content = { innerPadding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(tasks) { task ->
                        TaskItem(
                            task = task,
                            onTaskCheckedChange = { updatedTask ->
                                firebaseInstance.updateTask(updatedTask)
                            }
                        )
                    }
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onClick = { firebaseInstance.addTaskToFirebase() }
                ) {
                    Text(text = "Agregar Tarea")
                }
            }
        })
    }
}

@Composable
fun TaskItem(task: Task, onTaskCheckedChange: (Task) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = task.title, style = MaterialTheme.typography.bodyLarge)
            Checkbox(
                checked = task.completed,
                onCheckedChange = { isChecked ->
                    onTaskCheckedChange(task.copy(completed = isChecked))
                }
            )
        }
    }
}