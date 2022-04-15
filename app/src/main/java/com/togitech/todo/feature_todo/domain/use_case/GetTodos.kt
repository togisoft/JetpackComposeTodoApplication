package com.togitech.todo.feature_todo.domain.use_case

import com.togitech.todo.feature_todo.domain.model.Todo
import com.togitech.todo.feature_todo.domain.repository.TodoRepository
import com.togitech.todo.feature_todo.domain.util.OrderType
import com.togitech.todo.feature_todo.domain.util.TodoOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTodos(private val repository: TodoRepository) {
    operator fun invoke(todoOrder: TodoOrder = TodoOrder.Date(OrderType.Descending)) : Flow<List<Todo>> {
        return repository.getTodo().map { todos ->
            when(todoOrder.orderType) {
                is OrderType.Ascending -> {
                    when(todoOrder) {
                        is TodoOrder.Title -> todos.sortedBy { it.title.lowercase() }
                        is TodoOrder.Date -> todos.sortedBy { it.timeStamp }
                        is TodoOrder.Completed -> todos.sortedBy { it.isComplete }
                    }
                }
                is OrderType.Descending -> {
                    when(todoOrder) {
                        is TodoOrder.Title -> todos.sortedByDescending { it.title.lowercase() }
                        is TodoOrder.Date -> todos.sortedByDescending { it.timeStamp }
                        is TodoOrder.Completed -> todos.sortedByDescending { it.isComplete }

                    }
                }
            }
        }
    }
}