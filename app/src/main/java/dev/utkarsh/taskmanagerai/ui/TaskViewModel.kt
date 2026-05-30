package dev.utkarsh.taskmanagerai.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.utkarsh.taskmanagerai.data.TaskDatabase
import dev.utkarsh.taskmanagerai.data.TaskEntity
import dev.utkarsh.taskmanagerai.data.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * UI state for the task list screen.
 */
data class TaskListUiState(
    val tasks: List<TaskEntity> = emptyList(),
    val isLoading: Boolean = true,
    val newTaskTitle: String = "",
    val snackbarMessage: String? = null
)

/**
 * ViewModel managing task list state and user interactions.
 */
class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository by lazy {
        val db = TaskDatabase.getInstance(application)
        TaskRepository(db.taskDao())
    }

    private val _uiState = MutableStateFlow(TaskListUiState())
    val uiState: StateFlow<TaskListUiState> = _uiState.asStateFlow()

    init {
        loadTasks()
    }

    fun loadTasks() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val tasks = repository.getAllTasks()
            _uiState.update { it.copy(tasks = tasks, isLoading = false) }
        }
    }

    fun updateNewTaskTitle(title: String) {
        _uiState.update { it.copy(newTaskTitle = title) }
    }

    fun addTask() {
        val title = _uiState.value.newTaskTitle.trim()
        if (title.isBlank()) return

        viewModelScope.launch {
            val result = repository.addTask(title)
            _uiState.update { it.copy(newTaskTitle = "", snackbarMessage = result) }
            loadTasks()
        }
    }

    fun completeTask(task: TaskEntity) {
        viewModelScope.launch {
            val result = repository.completeTask(task.title)
            _uiState.update { it.copy(snackbarMessage = result) }
            loadTasks()
        }
    }

    fun clearSnackbar() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }
}
