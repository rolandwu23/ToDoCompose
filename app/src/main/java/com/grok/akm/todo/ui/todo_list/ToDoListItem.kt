package com.grok.akm.todo.ui.todo_list

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grok.akm.todo.data.ToDo

@Composable
fun ToDoListItem (
    toDo: ToDo,
    onEvent: (ToDoListEvent) -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){

        Column (
            modifier = Modifier.weight(1f) ,
            verticalArrangement =  Arrangement.Center
        ){
            Row(
               verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = toDo.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = {
                    onEvent(ToDoListEvent.OnDeleteTodoClick(toDo))
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete ToDo"
                    )
                }
            }
            toDo.description?.let { description ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = description)
            }
        }
        Checkbox(
            checked = toDo.isDone,
            onCheckedChange = {isChecked ->
                onEvent(ToDoListEvent.OnDoneChange(toDo,isChecked))
            }
        )
    }

}