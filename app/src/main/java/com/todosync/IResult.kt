package com.todosync

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

sealed interface IResult<out T> {
    data class Success<T>(val data: T) : IResult<T>
    data class Error(val exception: Throwable) : IResult<Nothing>
    data object Loading : IResult<Nothing>
}

fun <T> IResult<T>.ifSuccess(block: (T) -> Unit) {
    if (this is IResult.Success) block(data)
}

fun <T> Flow<T>.stateAsResultIn(scope: CoroutineScope): StateFlow<IResult<T>> =
    map<T, IResult<T>> { IResult.Success(it) }
        .catch { emit(IResult.Error(it)) }
        .stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = IResult.Loading
        )