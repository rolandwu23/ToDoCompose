package com.grok.akm.todo.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToDo(toDo: ToDo)

    @Delete
    suspend fun deleteToDo(toDo: ToDo)

    @Query("SELECT * FROM todo WHERE id=:id")
    suspend fun getToDoById(id: Int): ToDo?

    @Query("SELECT * FROM todo")
    fun getToDos() : Flow<List<ToDo>>

}