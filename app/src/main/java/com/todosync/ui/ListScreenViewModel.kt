package com.todosync.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todosync.ui.common.IResult
import com.todosync.data.TaskRepository
import com.todosync.domain.Task
import com.todosync.domain.usecases.TasksUseCase
import com.todosync.ui.common.stateAsResultIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val tasksUseCase: TasksUseCase,
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val uiReady = MutableStateFlow(false)

    val state: StateFlow<IResult<List<Task>>> = uiReady
        .filter { it }
        .flatMapLatest { tasksUseCase() }
        .stateAsResultIn(viewModelScope)

    fun addTask(task: Task) {
        viewModelScope.launch {
            runCatching { taskRepository.addTask(task) }
                .onFailure { Log.e("ListScreenViewModel", "Error adding task", it) }
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            runCatching { taskRepository.updateTask(task) }
                .onFailure { Log.e("ListScreenViewModel", "Error updating task", it) }
        }
    }

    fun onUiReady() {
        uiReady.value = true
    }
}