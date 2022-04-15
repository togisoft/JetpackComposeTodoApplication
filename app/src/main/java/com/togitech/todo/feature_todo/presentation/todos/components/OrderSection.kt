package com.togitech.todo.feature_todo.presentation.todos.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.togitech.todo.feature_todo.domain.model.Todo
import com.togitech.todo.feature_todo.domain.util.OrderType
import com.togitech.todo.feature_todo.domain.util.TodoOrder

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    todoOrder: TodoOrder = TodoOrder.Date(OrderType.Descending),
    onOrderChange: (TodoOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Title",
                selected = todoOrder is TodoOrder.Title,
                onSelect = { onOrderChange(TodoOrder.Title(todoOrder.orderType)) })
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Date",
                selected = todoOrder is TodoOrder.Date,
                onSelect = { onOrderChange(TodoOrder.Date(todoOrder.orderType)) })
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Completed",
                selected = todoOrder is TodoOrder.Completed,
                onSelect = { onOrderChange(TodoOrder.Completed(todoOrder.orderType)) })
            Spacer(modifier = Modifier.height(16.dp))
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = "Ascending",
                selected = todoOrder.orderType is OrderType.Ascending,
                onSelect = { onOrderChange(todoOrder.copy(OrderType.Ascending)) })
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = todoOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(todoOrder.copy(OrderType.Descending))
                })
        }


    }

}