package com.todosync.data

import com.todosync.domain.Task
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val firebaseInstance: FirebaseInstance
) : TaskRepository {

    override val tasks: Flow<List<Task>> = firebaseInstance.observeTasksFlow()

    override suspend fun addTask(task: Task) {
        firebaseInstance.addTask(task)
    }

    override suspend fun updateTask(task: Task) {
        firebaseInstance.updateTask(task)
    }
}