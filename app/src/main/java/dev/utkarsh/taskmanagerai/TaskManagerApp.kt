package dev.utkarsh.taskmanagerai

import android.app.Application
import dev.utkarsh.taskmanagerai.data.TaskDatabase

/**
 * Application class that initializes the database singleton.
 */
class TaskManagerApp : Application() {

    val database: TaskDatabase by lazy {
        TaskDatabase.getInstance(this)
    }

    override fun onCreate() {
        super.onCreate()
        // Pre-initialize database on app start
        database
    }
}
