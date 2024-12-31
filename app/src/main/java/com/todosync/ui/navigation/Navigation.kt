package com.todosync.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.todosync.ui.ListScreen
import kotlinx.serialization.Serializable

@Serializable
object List

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = List) {
        composable<List> {
            ListScreen()
        }
    }
}