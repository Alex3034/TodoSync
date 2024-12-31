package com.todosync.data

import com.todosync.domain.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    val tasks: Flow<List<Task>>
    suspend fun addTask(task: Task)
    suspend fun updateTask(task: Task)
}