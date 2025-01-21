package com.todosync.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DrawerMenu(selectedList: String, onListSelected: (String) -> Unit) {
    val taskLists = listOf("All Tasks", "Work", "Personal", "Shopping")

    Column {
        taskLists.forEach { list ->
            Text(
                text = list,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable { onListSelected(list) },
                color = if (list == selectedList) Color.Blue else Color.Black
            )
        }
    }
}