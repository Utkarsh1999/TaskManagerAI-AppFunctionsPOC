package dev.utkarsh.taskmanagerai.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a single task in the user's task list.
 */
@Entity(tableName = "tasks")
data class TaskEntity(
    /** Unique identifier for this task. */
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    /** The name or description of the task. */
    val title: String,

    /** Whether this task has been completed. */
    val isCompleted: Boolean = false,

    /** Timestamp when this task was created (epoch millis). */
    val createdAt: Long = System.currentTimeMillis()
)
