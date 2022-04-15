package com.togitech.todo.feature_todo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.Exception

@Entity
data class Todo(
    val title: String,
    val content: String,
    val timeStamp: Long,
    val isComplete: Boolean,
    @PrimaryKey val id: Int? = null
)

class InvalidTodoException(message: String) : Exception(message)