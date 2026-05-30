package dev.utkarsh.taskmanagerai.data

import java.util.Calendar

/**
 * Repository layer providing domain-friendly task operations.
 * Returns human-readable result strings suitable for voice responses.
 */
class TaskRepository(private val taskDao: TaskDao) {

    /**
     * Adds a new task with the given title.
     * @return A confirmation message.
     */
    suspend fun addTask(title: String): String {
        if (title.isBlank()) {
            return "Cannot add a task with an empty title."
        }
        val entity = TaskEntity(title = title.trim())
        taskDao.insertTask(entity)
        return "Task '${title.trim()}' has been added to your list."
    }

    /**
     * Marks the first incomplete task matching the title as completed.
     * @return A confirmation or error message.
     */
    suspend fun completeTask(title: String): String {
        if (title.isBlank()) {
            return "Please specify which task to complete."
        }
        val rowsUpdated = taskDao.markCompletedByTitle(title.trim())
        return if (rowsUpdated > 0) {
            "Task '${title.trim()}' has been marked as completed."
        } else {
            "Could not find an incomplete task called '${title.trim()}'. Check the exact task name and try again."
        }
    }

    /**
     * Generates a summary of today's tasks.
     * @return A formatted summary string.
     */
    suspend fun getTodaysSummary(): String {
        val startOfDay = getStartOfDayMillis()
        val todayTasks = taskDao.getTodayTasks(startOfDay)

        if (todayTasks.isEmpty()) {
            return "You have no tasks for today. Enjoy your free time!"
        }

        val completed = todayTasks.count { it.isCompleted }
        val pending = todayTasks.size - completed

        val sb = StringBuilder()
        sb.appendLine("📋 Today's Task Summary:")
        sb.appendLine("• Total: ${todayTasks.size}")
        sb.appendLine("• Completed: $completed ✅")
        sb.appendLine("• Pending: $pending ⏳")
        sb.appendLine()

        if (pending > 0) {
            sb.appendLine("Pending tasks:")
            todayTasks.filter { !it.isCompleted }.forEachIndexed { index, task ->
                sb.appendLine("  ${index + 1}. ${task.title}")
            }
        }

        if (completed > 0) {
            sb.appendLine("Completed tasks:")
            todayTasks.filter { it.isCompleted }.forEachIndexed { index, task ->
                sb.appendLine("  ${index + 1}. ${task.title}")
            }
        }

        return sb.toString().trim()
    }

    /**
     * Returns all tasks for display in the UI.
     */
    suspend fun getAllTasks(): List<TaskEntity> {
        return taskDao.getAllTasks()
    }

    /**
     * Returns all incomplete tasks.
     */
    suspend fun getIncompleteTasks(): List<TaskEntity> {
        return taskDao.getIncompleteTasks()
    }

    private fun getStartOfDayMillis(): Long {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis
    }
}
