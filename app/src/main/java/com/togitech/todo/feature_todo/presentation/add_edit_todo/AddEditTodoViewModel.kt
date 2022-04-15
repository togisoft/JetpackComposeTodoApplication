package com.togitech.todo.feature_todo.presentation.add_edit_todo

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.togitech.todo.feature_todo.domain.model.InvalidTodoException
import com.togitech.todo.feature_todo.domain.model.Todo
import com.togitech.todo.feature_todo.domain.use_case.TodoUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTodoViewModel @Inject constructor(
    private val todoUseCases: TodoUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _todoTitle = mutableStateOf(
        TodoTextFieldState(
            hint = "Enter title..."
        )
    )
    val todoTitle: State<TodoTextFieldState> = _todoTitle

    private val _todoContent = mutableStateOf(
        TodoTextFieldState(
            hint = "Enter some content"
        )
    )
    val todoContent: State<TodoTextFieldState> = _todoContent


    private val _todoCompleted = mutableStateOf(false)
    val todoCompleted: State<Boolean> = _todoCompleted


    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentTodoId: Int? = null

    init {
        savedStateHandle.get<Int>("todoId")?.let { todoId ->
            if (todoId != -1) {
                viewModelScope.launch {
                    todoUseCases.getTodo(todoId)?.also { todo ->
                        currentTodoId = todo.id
                        _todoTitle.value = todoTitle.value.copy(
                            text = todo.title,
                            isHintVisible = false
                        )
                        _todoContent.value = todoContent.value.copy(
                            text = todo.content,
                            isHintVisible = false
                        )
                        _todoCompleted.value = todo.isComplete
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditTodoEvent) {
        when (event) {
            is AddEditTodoEvent.EnteredTitle -> {
                _todoTitle.value = todoTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditTodoEvent.ChangeTitleFocus -> {
                _todoTitle.value = _todoTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _todoTitle.value.text.isBlank()
                )
            }

            is AddEditTodoEvent.EnteredContent -> {
                _todoContent.value = todoContent.value.copy(
                    text = event.value
                )
            }

            is AddEditTodoEvent.ChangeContentFocus -> {
                _todoContent.value = _todoContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _todoContent.value.text.isBlank()
                )
            }

            is AddEditTodoEvent.ChangeCompleted -> {
                _todoCompleted.value = event.completed
            }

            is AddEditTodoEvent.SaveTodo -> {
                viewModelScope.launch {
                    try {
                        todoUseCases.addTodo(
                            Todo(
                                title = todoTitle.value.text,
                                content = todoContent.value.text,
                                isComplete = todoCompleted.value,
                                timeStamp = System.currentTimeMillis(),
                                id = currentTodoId
                            )
                        )
                        _eventFlow.emit(UIEvent.SaveTodo)

                    } catch (e: InvalidTodoException) {
                        _eventFlow.emit(
                            UIEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save todo"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
        object SaveTodo : UIEvent()
    }

}