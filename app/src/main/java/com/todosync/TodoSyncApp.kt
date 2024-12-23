package com.todosync

import android.app.Application
import com.google.firebase.FirebaseApp

class TodoSyncApp: Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}