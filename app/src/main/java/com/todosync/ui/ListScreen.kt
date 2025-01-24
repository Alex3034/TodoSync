package com.todosync.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.todosync.domain.Task
import com.todosync.ui.common.DrawerMenu
import com.todosync.ui.common.MyTopAppBar
import com.todosync.ui.common.Screen
import com.todosync.ui.common.StateScaffold
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(vm: ListScreenViewModel = hiltViewModel()) {

    LaunchedEffect(Unit) {
        vm.onUiReady()
    }

    Screen {
        val state by vm.state.collectAsState()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val listState = rememberListState()
        val scope = rememberCoroutineScope()
        val focusRequester = remember { FocusRequester() }

        var selectedList by remember { mutableStateOf("1" to "All Tasks") }
        var isEditingTask by remember { mutableStateOf(false) }
        var newTaskTitle by remember { mutableStateOf("") }

        LaunchedEffect(isEditingTask) {
            if (isEditingTask) {
                focusRequester.requestFocus()
            }
        }

        StateScaffold(
            state = state,
            drawerState = drawerState,
            topBar = {
                MyTopAppBar(listState)
            },
            floatingActionButton = {
                FloatingActionButton(
                    shape = MaterialTheme.shapes.extraLarge,
                    containerColor = Color.Green,
                    onClick = { isEditingTask = !isEditingTask }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        tint = MaterialTheme.colorScheme.surface,
                        contentDescription = "add task"
                    )
                }
            },
            modifier = Modifier.nestedScroll(listState.scrollBehavior.nestedScrollConnection),
            contentWindowInsets = WindowInsets.safeDrawing,
            drawerContent = {
                DrawerMenu(
                    selectedList = selectedList,
                    onListSelected = { id, name ->
                        selectedList = id to name
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
            }
        }
    }
}