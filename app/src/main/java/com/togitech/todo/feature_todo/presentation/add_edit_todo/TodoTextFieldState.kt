package com.togitech.todo.feature_todo.presentation.add_edit_todo

data class TodoTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)
