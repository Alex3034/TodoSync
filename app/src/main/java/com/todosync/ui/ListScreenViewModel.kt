package com.todosync.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todosync.IResult
import com.todosync.data.Task
import com.todosync.domain.usecases.TasksUseCase
import com.todosync.stateAsResultIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val tasksUseCase: TasksUseCase
) : ViewModel() {

    private val uiReady = MutableStateFlow(false)

    val state: StateFlow<IResult<List<Task>>> = uiReady
        .filter { it }
        .flatMapLatest { tasksUseCase() }
        .stateAsResultIn(viewModelScope)


    fun onUiReady() {
        uiReady.value = true
    }
}