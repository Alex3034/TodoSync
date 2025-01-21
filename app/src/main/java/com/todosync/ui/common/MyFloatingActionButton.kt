package com.todosync.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun MyFloatingActionButton(isEditingTask: Boolean) {
    var isEditingTask1 = isEditingTask
    FloatingActionButton(
        shape = MaterialTheme.shapes.extraLarge,
        containerColor = Color.Green,
        onClick = { isEditingTask1 = !isEditingTask1 }) {
        Icon(
            imageVector = Icons.Default.Add,
            tint = MaterialTheme.colorScheme.surface,
            contentDescription = "add task"
        )
    }
}