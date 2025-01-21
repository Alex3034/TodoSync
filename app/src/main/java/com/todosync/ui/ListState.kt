package com.todosync.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@OptIn(ExperimentalMaterial3Api::class)
class ListState(
    val scrollBehavior: TopAppBarScrollBehavior
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberListState(
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
) = remember { ListState(scrollBehavior) }