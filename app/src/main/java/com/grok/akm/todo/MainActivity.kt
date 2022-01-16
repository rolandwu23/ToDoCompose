package com.grok.akm.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.grok.akm.todo.ui.add_edit_todo.AddEditToDoScreen
import com.grok.akm.todo.ui.theme.ToDoTheme
import com.grok.akm.todo.ui.todo_list.ToDoListScreen
import com.grok.akm.todo.util.Route
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Route.TODO_LIST
                ){
                    composable(Route.TODO_LIST){
                        ToDoListScreen(
                            onNavigate = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                    composable(Route.ADD_EDIT_TODO,
                    arguments = listOf(
                        navArgument(name = "toDoId"){
                            type = NavType.IntType
                            defaultValue = -1
                        }
                    )){
                        AddEditToDoScreen(onPopBackStack = {
                            navController.popBackStack()
                        })
                    }
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ToDoTheme {
        Greeting("Android")
    }
}