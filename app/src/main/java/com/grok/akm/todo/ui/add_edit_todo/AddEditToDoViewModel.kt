package com.grok.akm.todo.ui.add_edit_todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grok.akm.todo.data.ToDo
import com.grok.akm.todo.data.ToDoRepository
import com.grok.akm.todo.ui.todo_list.ToDoListEvent
import com.grok.akm.todo.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddEditToDoViewModel @Inject constructor(
    private val repository: ToDoRepository,
    stateHandle: SavedStateHandle
) : ViewModel() {

    var todo by mutableStateOf<ToDo?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val toDoId = stateHandle.get<Int>("toDoId")!!
        if(toDoId != -1){
            viewModelScope.launch {
                todo = repository.getToDoById(toDoId)
            }
        }
    }

    fun onEvent(event: AddEditToDoEvent) {
        when(event) {
            is AddEditToDoEvent.OnDescriptionChanged -> {
                description = event.description
            }
            AddEditToDoEvent.OnSaveTodoClick -> {
                viewModelScope.launch {
                    if(title.isBlank()){
                        sendUiEvent(UiEvent.ShowSnackBar(
                            message = "Title cannot be empty"
                        ))
                        return@launch
                    }

                    repository.insertToDo(
                        ToDo(
                            id = todo?.id,
                            title = title,
                            description = description,
                            isDone = todo?.isDone ?: false
                        )
                    )
                    sendUiEvent(UiEvent.PopBackUpStack)
                }
            }
            is AddEditToDoEvent.OnTitleChanged -> {
                title = event.title
            }
        }
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}