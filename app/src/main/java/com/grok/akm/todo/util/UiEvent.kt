package com.grok.akm.todo.util

sealed class UiEvent{
    object PopBackUpStack: UiEvent()
    data class Navigate(val route: String): UiEvent()
    data class ShowSnackBar(
        val message: String,
        val action: String? = null
    ): UiEvent()
}
