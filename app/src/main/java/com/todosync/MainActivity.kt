package com.todosync

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.todosync.data.FirebaseInstance
import com.todosync.ui.navigation.Navigation
import com.todosync.ui.theme.TodoSyncTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val firebaseInstance = FirebaseInstance()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Navigation(firebaseInstance)
        }
    }
}