package com.grok.akm.todo.ui.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grok.akm.todo.data.ToDo
import com.grok.akm.todo.data.ToDoRepository
import com.grok.akm.todo.util.Route
import com.grok.akm.todo.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoListViewModel @Inject constructor(
    private val repository: ToDoRepository
) : ViewModel(){

    val toDos = repository.getToDos()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedToDo: ToDo? = null

    fun onEvent(event: ToDoListEvent) {
        when(event) {
            ToDoListEvent.OnAddTodoClick -> {
                sendUiEvent(UiEvent.Navigate(Route.ADD_EDIT_TODO))
            }
            is ToDoListEvent.OnDeleteTodoClick -> {
                viewModelScope.launch {
                    deletedToDo = event.todo
                    repository.deleteToDo(event.todo)
                    sendUiEvent(UiEvent.ShowSnackBar(
                        "ToDo is deleted",
                        "Undo"
                    ))
                }
            }
            is ToDoListEvent.OnDoneChange -> {
                viewModelScope.launch {
                    repository.insertToDo(
                        event.todo.copy(
                            isDone = event.isDone
                        )
                    )
                }
            }
            is ToDoListEvent.OnTodoClick -> {
                sendUiEvent(UiEvent.Navigate(Route.ADD_EDIT_TODO+ "?toDoId=${event.todo.id}"))
            }
            ToDoListEvent.OnUndoDeleteClick -> {
                deletedToDo?.let { toDo ->
                    viewModelScope.launch {
                        repository.insertToDo(toDo)
                    }
                }
            }
        }
    }


    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}