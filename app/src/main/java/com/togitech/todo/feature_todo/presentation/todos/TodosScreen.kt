package com.togitech.todo.feature_todo.presentation.todos

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.togitech.todo.feature_todo.domain.model.Todo
import com.togitech.todo.feature_todo.presentation.todos.components.OrderSection
import com.togitech.todo.feature_todo.presentation.todos.components.TodoItem
import com.togitech.todo.feature_todo.presentation.util.Screen
import com.togitech.todo.ui.theme.GreenDark
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TodosScreen(
    navController: NavController,
    viewModel: TodosViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.AddEditTodosScreen.route)
            }, backgroundColor = MaterialTheme.colors.primary) {
                Icon(
                    imageVector = Icons.Rounded.AddTask,
                    contentDescription = "Add Todo Screen",
                    tint = Color.White
                )
            }
        },

        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = { Text(text = "All TODO") },
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.onBackground,
                actions = {
                    IconButton(onClick = { viewModel.onEvent(TodosEvent.ToggleOrderSection) }) {
                        Icon(imageVector = Icons.Rounded.FilterList, contentDescription = "Filter")
                    }
                })
        }
    ) {
        it.calculateTopPadding()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(3.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedVisibility(
                    visible = state.isOrderSectionVisible,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    OrderSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        todoOrder = state.todoOrder,
                        onOrderChange = { todoOrder ->
                            viewModel.onEvent(TodosEvent.Order(todoOrder = todoOrder))
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(
                    items = state.todos,
                    key = { _, item ->
                        item.hashCode()
                    }
                ) { _, item ->
                    val showDialog = remember{ mutableStateOf(false)}
                    if (showDialog.value) {
                        AlertDialog(onDismissRequest = { showDialog.value = false },
                            title = { Text(text = "Are you sure?") },
                            text = { Text(text = "Do you want to delete this TODO?") },
                            confirmButton = {
                                Button(onClick = {
                                    viewModel.onEvent(TodosEvent.DeleteTodo(item))
                                    showDialog.value = false
                                }) {
                                    Text(text = "Confirm")
                                }
                            },
                            dismissButton = {
                                OutlinedButton(onClick = { showDialog.value = false }) {
                                    Text(text = "Cancel")
                                }
                            })
                    }
                    val dismissState = rememberDismissState(
                        confirmStateChange = { dismiss ->
                            if (dismiss == DismissValue.DismissedToStart) {
                                showDialog.value = true
                            } else if (dismiss == DismissValue.DismissedToEnd) {
                                viewModel.onEvent(TodosEvent.CompletedTodo(item))
                            }
                            false
                        }
                    )
                    SwipeToDismiss(
                        modifier = Modifier.padding(top = 10.dp),
                        state = dismissState,
                        background = {
                            val color = when (dismissState.dismissDirection) {
                                DismissDirection.StartToEnd -> if (item.isComplete) Color.DarkGray else GreenDark
                                DismissDirection.EndToStart -> Color.Red
                                null -> Color.Transparent
                            }
                            when (dismissState.dismissDirection) {
                                DismissDirection.StartToEnd -> {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(color)
                                            .padding(16.dp)
                                    ) {
                                        if (item.isComplete) {
                                            Icon(
                                                imageVector = Icons.Rounded.RemoveDone,
                                                contentDescription = "Remove Done",
                                                tint = Color.White,
                                                modifier = Modifier.align(
                                                    Alignment.CenterStart
                                                )
                                            )
                                        } else {
                                            Icon(
                                                imageVector = Icons.Rounded.DoneAll,
                                                contentDescription = "Done",
                                                tint = Color.White,
                                                modifier = Modifier.align(
                                                    Alignment.CenterStart
                                                )
                                            )
                                        }

                                    }
                                }
                                else -> {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(color)
                                            .padding(16.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.Delete,
                                            contentDescription = "Delete",
                                            tint = Color.White,
                                            modifier = Modifier.align(
                                                Alignment.CenterEnd
                                            )
                                        )
                                    }
                                }
                            }

                        },
                        dismissContent = {
                            TodoItem(todo = item, modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(Screen.AddEditTodosScreen.route + "?todoId=${item.id}")
                                })
                        },
                        directions = setOf(DismissDirection.EndToStart, DismissDirection.StartToEnd)
                    )

                }
            }
        }
    }
}

@Composable
fun DeleteAlert(viewModel: TodosViewModel, item: Todo) {


}