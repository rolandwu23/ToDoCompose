package com.grok.akm.todo.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ToDoRepositoryImpl @Inject constructor(
    private val toDoDao: ToDoDao
): ToDoRepository{


    override suspend fun insertToDo(toDo: ToDo) {
        toDoDao.insertToDo(toDo)
    }

    override suspend fun deleteToDo(toDo: ToDo) {
        toDoDao.deleteToDo(toDo)
    }

    override suspend fun getToDoById(id: Int): ToDo? {
        return toDoDao.getToDoById(id)
    }

    override fun getToDos(): Flow<List<ToDo>> {
        return toDoDao.getToDos()
    }


}