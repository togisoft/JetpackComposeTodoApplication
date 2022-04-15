package com.togitech.todo.feature_todo.domain.util

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}