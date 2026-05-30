package dev.utkarsh.taskmanagerai.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Data Access Object for task operations.
 */
@Dao
interface TaskDao {

    /**
     * Inserts a new task into the database.
     * @return The row ID of the newly inserted task.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity): Long

    /**
     * Marks a specific task as completed by its ID.
     */
    @Query("UPDATE tasks SET isCompleted = 1 WHERE id = :taskId")
    suspend fun markCompleted(taskId: Long)

    /**
     * Marks the first incomplete task matching the given title as completed.
     * Returns the number of rows updated.
     */
    @Query("UPDATE tasks SET isCompleted = 1 WHERE id = (SELECT id FROM tasks WHERE title = :title AND isCompleted = 0 LIMIT 1)")
    suspend fun markCompletedByTitle(title: String): Int

    /**
     * Returns all incomplete tasks, ordered by creation time descending.
     */
    @Query("SELECT * FROM tasks WHERE isCompleted = 0 ORDER BY createdAt DESC")
    suspend fun getIncompleteTasks(): List<TaskEntity>

    /**
     * Returns all tasks created today (since midnight), ordered by creation time.
     */
    @Query("SELECT * FROM tasks WHERE createdAt >= :startOfDay ORDER BY createdAt ASC")
    suspend fun getTodayTasks(startOfDay: Long): List<TaskEntity>

    /**
     * Returns all tasks ordered by creation time descending.
     */
    @Query("SELECT * FROM tasks ORDER BY createdAt DESC")
    suspend fun getAllTasks(): List<TaskEntity>

    /**
     * Deletes all tasks from the database.
     */
    @Query("DELETE FROM tasks")
    suspend fun deleteAll()
}
