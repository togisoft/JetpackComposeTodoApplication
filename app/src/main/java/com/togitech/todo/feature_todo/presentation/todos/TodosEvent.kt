package com.togitech.todo.feature_todo.presentation.todos

import com.togitech.todo.feature_todo.domain.model.Todo
import com.togitech.todo.feature_todo.domain.util.TodoOrder

sealed class TodosEvent {
    data class Order(val todoOrder: TodoOrder) : TodosEvent()
    data class DeleteTodo(val todo: Todo) : TodosEvent()
    data class CompletedTodo(val todo: Todo) : TodosEvent()
    object RestoreTodo : TodosEvent()
    object ToggleOrderSection : TodosEvent()
}
