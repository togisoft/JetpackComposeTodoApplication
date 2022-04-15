package com.togitech.todo.feature_todo.presentation.util

sealed class Screen(val route: String) {
    object TodosScreen : Screen("todos")
    object AddEditTodosScreen : Screen("add_edit_todos")
}
