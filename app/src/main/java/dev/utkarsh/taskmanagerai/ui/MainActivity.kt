package dev.utkarsh.taskmanagerai.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.utkarsh.taskmanagerai.ui.theme.TaskManagerAITheme

/**
 * Single-activity host for the Task Manager UI.
 * The primary interaction model is voice via Gemini —
 * this screen is a convenience dashboard.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskManagerAITheme {
                val viewModel: TaskViewModel = viewModel()
                TaskListScreen(viewModel = viewModel)
            }
        }
    }
}
