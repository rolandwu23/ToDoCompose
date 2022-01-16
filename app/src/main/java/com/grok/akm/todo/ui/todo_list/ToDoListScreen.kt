package com.grok.akm.todo.ui.todo_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.grok.akm.todo.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun ToDoListScreen(
    navController: NavController,
    viewModel: ToDoListViewModel = hiltViewModel()
) {
    val toDos = viewModel.toDos.collectAsState(emptyList())
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent: UiEvent ->
            when(uiEvent){
                is UiEvent.Navigate -> {
                    navController.navigate(uiEvent.route)
                }
                is UiEvent.ShowSnackBar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        uiEvent.message,
                        uiEvent.action
                    )
                    if(result == SnackbarResult.ActionPerformed){
                        viewModel.onEvent(ToDoListEvent.OnUndoDeleteClick)
                    }
                }
                else -> Unit
            }
        }
    }


    Scaffold (
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(ToDoListEvent.OnAddTodoClick)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add ToDo"
                )
            }
        }
    ){
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            items(toDos.value) {toDo->
                ToDoListItem(
                    toDo = toDo,
                    onEvent = viewModel::onEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .clickable {
                            viewModel.onEvent(ToDoListEvent.OnTodoClick(toDo))
                        }
                )
            }
        }
    }

}