package com.todosync.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.todosync.domain.Task
import com.todosync.ui.common.DrawerMenu
import com.todosync.ui.common.Screen
import com.todosync.ui.common.StateScaffold
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun ListScreen(vm: ListScreenViewModel = hiltViewModel()) {

    LaunchedEffect(Unit) {
        vm.onUiReady()
    }

    Screen {
        val state by vm.state.collectAsState()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        var selectedList by remember { mutableStateOf("All Tasks") }
        
        var isEditingTask by remember { mutableStateOf(false) }
        var newTaskTitle by remember { mutableStateOf("") }
        val focusRequester = remember { FocusRequester() }

        LaunchedEffect(isEditingTask) {
            if (isEditingTask) {
                focusRequester.requestFocus()
            }
        }

        StateScaffold(
            state = state,
            drawerState = drawerState,
            snackbarHost = { SnackbarHost(snackbarHostState) },
            contentWindowInsets = WindowInsets.safeDrawing,
            drawerContent = {
                DrawerMenu(
                    selectedList = selectedList,
                    onListSelected = { list ->
                        selectedList = list
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )
            }
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
                            },
                            focusRequester = focusRequester,
                        )
                    }
                    if (isEditingTask) {
                        item {
                            TaskItem(
                                task = Task(id = "", title = newTaskTitle),
                                onTaskCheckedChange = {},
                                onDeleteClick = {},
                                isEditing = true,
                                onEditChange = { newTaskTitle = it },
                                onEditComplete = { title ->
                                    if (title.isNotBlank()) {
                                        vm.addTask(
                                            Task(
                                                id = UUID.randomUUID().toString(),
                                                title = title,
                                            )
                                        )
                                        newTaskTitle = ""
                                    }
                                    isEditingTask = false
                                },
                                focusRequester = focusRequester
                            )
                        }
                    }
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onClick = { isEditingTask = !isEditingTask }
                ) {
                    Text(text = "Agregar Tarea")
                }

            }
        }
    }
}