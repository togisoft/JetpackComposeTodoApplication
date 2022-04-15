package com.togitech.todo.feature_todo.presentation.todos.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Circle
import androidx.compose.material.icons.rounded.DeleteSweep
import androidx.compose.material.icons.rounded.DoneAll
import androidx.compose.material.icons.rounded.RemoveDone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.togitech.todo.feature_todo.domain.model.Todo

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TodoItem(
    todo: Todo,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(0),
        modifier = modifier
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Icon(
                imageVector = Icons.Rounded.Circle,
                contentDescription = "Is Complete",
                tint = if (todo.isComplete) Color.Green else Color.Red
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column() {
                Text(
                    text = todo.title,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold
                )
                Text(text = todo.content, style = MaterialTheme.typography.body2, maxLines = 5)
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
    }

}
