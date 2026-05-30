package dev.utkarsh.taskmanagerai.appfunctions

import android.content.Context
import androidx.appfunctions.AppFunctionContext
import androidx.appfunctions.service.AppFunction
import dev.utkarsh.taskmanagerai.data.TaskDatabase
import dev.utkarsh.taskmanagerai.data.TaskRepository

/**
 * Exposes task management capabilities to AI agents like Gemini.
 *
 * These functions are indexed by the Android system and can be invoked
 * by Gemini through voice commands without opening the app.
 */
class TaskAppFunctions(context: Context) {

    private val repository: TaskRepository by lazy {
        val db = TaskDatabase.getInstance(context)
        TaskRepository(db.taskDao())
    }

    /**
     * Adds a new task to the user's task list.
     * Use this when the user wants to create, add, or remember a new task or to-do item.
     *
     * @param title The name or description of the task to add.
     * @return A confirmation message indicating the task was added.
     */
    @AppFunction(isDescribedByKDoc = true)
    suspend fun addTask(
        appFunctionContext: AppFunctionContext,
        title: String
    ): String {
        return repository.addTask(title)
    }

    /**
     * Marks an existing task as completed or done.
     * Use this when the user says they finished, completed, or are done with a task.
     *
     * @param title The exact title of the task to mark as completed.
     * @return A confirmation message or an error if the task was not found.
     */
    @AppFunction(isDescribedByKDoc = true)
    suspend fun completeTask(
        appFunctionContext: AppFunctionContext,
        title: String
    ): String {
        return repository.completeTask(title)
    }

    /**
     * Returns a summary of today's tasks including completed and pending counts.
     * Use this when the user asks about their tasks, wants a daily summary,
     * or asks what they need to do today.
     *
     * @return A formatted summary of all tasks created today.
     */
    @AppFunction(isDescribedByKDoc = true)
    suspend fun summarizeTasks(
        appFunctionContext: AppFunctionContext
    ): String {
        return repository.getTodaysSummary()
    }
}
