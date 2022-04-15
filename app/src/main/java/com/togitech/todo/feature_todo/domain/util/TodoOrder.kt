package com.togitech.todo.feature_todo.domain.util

sealed class TodoOrder(val orderType: OrderType) {
    class Title(orderType: OrderType) : TodoOrder(orderType = orderType)
    class Date(orderType: OrderType) : TodoOrder(orderType = orderType)
    class Completed(orderType: OrderType) : TodoOrder(orderType = orderType)

    fun copy(orderType: OrderType): TodoOrder {
        return when (this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Completed -> Date(orderType)
        }
    }
}