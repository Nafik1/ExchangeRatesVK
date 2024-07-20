package com.example.exchangeratesvk.domain.entity

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed class ExhangeRateState<out T> {

    data class Success<T>(val data: T) : ExhangeRateState<T>()
    data class Error(val exception: Throwable) : ExhangeRateState<Nothing>()
    object Loading : ExhangeRateState<Nothing>()
}

fun <T> Flow<T>.asResult(): Flow<ExhangeRateState<T>> {
    return this
        .map<T, ExhangeRateState<T>> { ExhangeRateState.Success(it) }
        .onStart { emit(ExhangeRateState.Loading) }
        .catch { emit(ExhangeRateState.Error(it)) }
}