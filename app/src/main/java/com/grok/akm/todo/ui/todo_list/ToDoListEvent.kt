package com.grok.akm.todo.ui.todo_list

import com.grok.akm.todo.data.ToDo

sealed class ToDoListEvent{
    data class OnDeleteTodoClick(val todo: ToDo): ToDoListEvent()
    data class OnDoneChange(val todo: ToDo, val isDone: Boolean): ToDoListEvent()
    object OnUndoDeleteClick: ToDoListEvent()
    data class OnTodoClick(val todo: ToDo): ToDoListEvent()
    object OnAddTodoClick: ToDoListEvent()
}
