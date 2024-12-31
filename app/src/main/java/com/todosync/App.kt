package com.todosync

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import com.todosync.data.FirebaseInstance
import com.todosync.data.TaskRepository
import com.todosync.data.TaskRepositoryImpl
import com.todosync.domain.usecases.TasksUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseInstance(@ApplicationContext context: Context): FirebaseInstance {
        return FirebaseInstance(context)
    }

    @Provides
    @Singleton
    fun provideTaskRepository(firebaseInstance: FirebaseInstance): TaskRepository {
        return TaskRepositoryImpl(firebaseInstance)
    }

    @Provides
    @Singleton
    fun provideTasksUseCase(repository: TaskRepository): TasksUseCase {
        return TasksUseCase(repository)
    }
}