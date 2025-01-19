package com.todosync.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.todosync.domain.Task
import com.todosync.ui.common.Screen
import com.todosync.ui.common.StateScaffold
import java.util.UUID

@Composable
fun ListScreen(vm: ListScreenViewModel = hiltViewModel()) {

    LaunchedEffect(Unit) {
        vm.onUiReady()
    }

    Screen {
        val state by vm.state.collectAsState()

        StateScaffold(
            state = state,
            contentWindowInsets = WindowInsets.safeDrawing
        ) { innerPadding, tasks ->
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
                                vm.updateTask(updatedTask)
                            },
                            onDeleteClick = { deleteTask ->
                                vm.deleteTask(deleteTask)
                            }
                        )
                    }
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onClick = {
                        vm.addTask(
                            Task(
                                id = UUID.randomUUID().toString(),
                                title = "Nueva Tarea",
                                completed = false
                            )
                        )
                    }
                ) {
                    Text(text = "Agregar Tarea")
                }
            }
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onTaskCheckedChange: (Task) -> Unit,
    onDeleteClick: (Task) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = task.title, style = MaterialTheme.typography.bodyLarge)
            IconButton(onClick = { onDeleteClick(task) }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Task")
            }
            Checkbox(
                checked = task.completed,
                onCheckedChange = { isChecked ->
                    onTaskCheckedChange(task.copy(completed = isChecked))
                }
            )
        }
    }
}