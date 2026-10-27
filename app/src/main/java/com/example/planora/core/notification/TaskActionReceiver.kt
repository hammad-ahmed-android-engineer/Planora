package com.example.planora.core.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.planora.data.local.PlanoraDatabase
import com.example.planora.data.mapper.toDomain
import com.example.planora.data.repository.TaskRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val taskId = intent.getStringExtra("EXTRA_TASK_ID") ?: return
        val notificationId = intent.getIntExtra("EXTRA_NOTIFICATION_ID", -1)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (notificationId != -1) {
            notificationManager.cancel(notificationId)
        }

        val database = PlanoraDatabase.getDatabase(context)
        val repository = TaskRepositoryImpl(database.taskDao())

        when (intent.action) {
            ACTION_COMPLETE -> {
                CoroutineScope(Dispatchers.IO).launch {
                    val taskWithDetails = database.taskDao().getTaskWithDetailsByIdDirect(taskId)
                    taskWithDetails?.let {
                        repository.completeTask(it.toDomain(), true)
                    }
                }
            }
            ACTION_SNOOZE -> {
                val alarmScheduler = AlarmScheduler(context)
                val snoozeTimeMs = System.currentTimeMillis() + 15 * 60 * 1000
                CoroutineScope(Dispatchers.IO).launch {
                    val taskWithDetails = database.taskDao().getTaskWithDetailsByIdDirect(taskId)
                    taskWithDetails?.let {
                        alarmScheduler.scheduleReminder(
                            it.toDomain(),
                            snoozeTimeMs,
                            if (notificationId != -1) notificationId else taskId.hashCode()
                        )
                    }
                }
            }
        }
    }

    companion object {
        const val ACTION_COMPLETE = "com.example.planora.ACTION_TASK_COMPLETE"
        const val ACTION_SNOOZE = "com.example.planora.ACTION_TASK_SNOOZE"
    }
}
