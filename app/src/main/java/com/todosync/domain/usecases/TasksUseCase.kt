package com.todosync.domain.usecases

import com.todosync.data.TaskRepository
import com.todosync.domain.Task
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TasksUseCase @Inject constructor(private val repository: TaskRepository) {
    operator fun invoke(): Flow<List<Task>> = repository.tasks
}
