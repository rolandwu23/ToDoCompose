package com.grok.akm.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDo(
    @PrimaryKey val id: Int? = null,
    var title: String,
    var description: String?,
    var isDone: Boolean,
)
