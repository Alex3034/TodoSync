package com.todosync.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.todosync.R

@Composable
fun DrawerMenu(selectedList: Pair<String, String>, onListSelected: (String,String) -> Unit) {
    val taskLists = mapOf(
        "1" to "All Tasks",
        "2" to "Work",
        "3" to "Personal",
        "4" to "Shopping",
    )

    val additionalOptions = listOf("Settings", "Help", "About")

    Column(
        modifier = Modifier
            .fillMaxWidth(1 / 3f)
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.surface)
            .padding(top = 64.dp, start = 8.dp, end = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.perfil),
            contentDescription = "User image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )
        Text(
            text = "Alejandro Herrera Molina",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 16.dp, bottom = 16.dp)
                .fillMaxWidth()
        )
        MyDivider()
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 16.dp)
        ) {
            items(taskLists.keys.toList()) { id ->
                val listName = taskLists[id] ?: ""
                Text(
                    text = listName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable { onListSelected(id,listName) },
                    color = if (id == selectedList.first) Color.Green else Color.Unspecified
                )
            }
        }
        MyDivider()
        additionalOptions.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { /* Manejar click */ },
                horizontalArrangement = Arrangement.Start
            ) {
                val icon = when (option) {
                    "Settings" -> Icons.Default.Settings
                    "Help" -> Icons.Default.Search
                    "About" -> Icons.Default.Info
                    else -> Icons.Default.MoreVert
                }
                Icon(imageVector = icon, contentDescription = null)
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = option)
            }
        }
        Spacer(modifier = Modifier.padding(16.dp))
    }
}

@Composable
private fun MyDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 16.dp),
        thickness = 0.5.dp,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    )
}
