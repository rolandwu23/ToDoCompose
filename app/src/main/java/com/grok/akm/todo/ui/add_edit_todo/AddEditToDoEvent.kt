package com.grok.akm.todo.ui.add_edit_todo

sealed class AddEditToDoEvent{
    data class OnTitleChanged(val title: String): AddEditToDoEvent()
    data class OnDescriptionChanged(val description: String): AddEditToDoEvent()
    object OnSaveTodoClick: AddEditToDoEvent()
}
