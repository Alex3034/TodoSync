package com.todosync.ui.common

import android.text.Layout
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.todosync.R
import com.todosync.ui.ListState
import org.checkerframework.checker.units.qual.A

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MyTopAppBar(listState: ListState, selectedList: String) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Text(
                    text = selectedList,
                )
            }
        },
        scrollBehavior = listState.scrollBehavior,
    )
}