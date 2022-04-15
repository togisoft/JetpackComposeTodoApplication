package com.togitech.todo.feature_todo.domain.use_case

import com.togitech.todo.feature_todo.domain.model.InvalidTodoException
import com.togitech.todo.feature_todo.domain.model.Todo
import com.togitech.todo.feature_todo.domain.repository.TodoRepository
import kotlin.jvm.Throws

class AddTodo(
    private val repository: TodoRepository
) {
    @Throws(InvalidTodoException::class)
    suspend operator fun invoke(todo: Todo) {
        if (todo.title.isBlank()) {
            throw InvalidTodoException("The title of the note can't be empty.")
        }
        if (todo.content.isBlank()) {
            throw InvalidTodoException("The content of the note can't be empty.")
        }

        repository.insertTodo(todo = todo)
    }
}