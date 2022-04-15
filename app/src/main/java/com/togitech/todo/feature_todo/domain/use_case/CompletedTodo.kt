package com.togitech.todo.feature_todo.domain.use_case

import com.togitech.todo.feature_todo.domain.repository.TodoRepository

class CompletedTodo(private val repository: TodoRepository) {
    suspend operator fun invoke(completed: Boolean, id: Int) {
        return repository.completedTodo(completed = completed, id = id)
    }
}