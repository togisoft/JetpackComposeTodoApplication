package com.togitech.todo.feature_todo.domain.use_case


data class TodoUseCases(
    val getTodos: GetTodos,
    val getTodo: GetTodo,
    val addTodo: AddTodo,
    val deleteTodo: DeleteTodo,
    val todoCompleted: CompletedTodo
)