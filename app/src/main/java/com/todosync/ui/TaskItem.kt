package com.todosync.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.todosync.domain.Task


@Composable
fun TaskItem(
    task: Task,
    onTaskCheckedChange: (Task) -> Unit,
    onDeleteClick: (Task) -> Unit,
    onEditComplete: (String) -> Unit = {},
    isEditing: Boolean = false,
    onEditChange: (String) -> Unit = {},
    focusRequester: FocusRequester
) {
    var isEditingState by remember { mutableStateOf(isEditing) }
    var editedText by remember { mutableStateOf(task.title) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isEditingState) {
                TextField(
                    value = editedText,
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Green
                    ),
                    onValueChange = {
                        editedText = it
                        onEditChange(it)
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onEditComplete(editedText)
                            isEditingState = false
                        }
                    )
                )
            } else {
                Text(text = task.title, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyLarge)
            }
            IconButton(onClick = { onDeleteClick(task) }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Task")
            }
            Checkbox(
                checked = task.completed,
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.Green
                ),
                onCheckedChange = { isChecked ->
                    onTaskCheckedChange(task.copy(completed = isChecked))
                }
            )
        }
    }
}