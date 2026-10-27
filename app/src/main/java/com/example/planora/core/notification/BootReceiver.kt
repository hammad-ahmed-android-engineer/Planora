package com.example.planora.core.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.planora.data.local.PlanoraDatabase
import com.example.planora.data.local.entity.toDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (action == Intent.ACTION_BOOT_COMPLETED ||
            action == Intent.ACTION_TIME_CHANGED ||
            action == Intent.ACTION_TIMEZONE_CHANGED) {

            val database = PlanoraDatabase.getDatabase(context)
            val alarmScheduler = AlarmScheduler(context)

            CoroutineScope(Dispatchers.IO).launch {
                val now = System.currentTimeMillis()

                val missedReminders = database.reminderDao().getMissedPendingReminders(now)
                missedReminders.forEach { reminderEntity ->
                    val taskWithDetails = database.taskDao().getTaskWithDetailsByIdDirect(reminderEntity.taskId)
                    if (taskWithDetails != null && !taskWithDetails.task.isCompleted && !taskWithDetails.task.isDeleted) {
                        val reminderIntent = Intent(context, TaskReminderReceiver::class.java).apply {
                            putExtra("EXTRA_TASK_ID", taskWithDetails.task.id)
                            putExtra("EXTRA_TASK_TITLE", "Missed Task: ${taskWithDetails.task.title}")
                            putExtra("EXTRA_TASK_DESC", taskWithDetails.task.description.ifEmpty { "Reminder was missed while device was inactive" })
                            putExtra("EXTRA_NOTIFICATION_ID", reminderEntity.notificationId)
                        }
                        context.sendBroadcast(reminderIntent)
                    }
                }

                val pendingReminders = database.reminderDao().getUpcomingPendingReminders(now)
                pendingReminders.forEach { reminderEntity ->
                    val taskWithDetails = database.taskDao().getTaskWithDetailsByIdDirect(reminderEntity.taskId)
                    if (taskWithDetails != null && !taskWithDetails.task.isCompleted && !taskWithDetails.task.isDeleted) {
                        alarmScheduler.scheduleReminder(
                            taskWithDetails.toDomain(),
                            reminderEntity.triggerTimestamp,
                            reminderEntity.notificationId
                        )
                    }
                }

                StreakNotificationReceiver.scheduleDailyStreakCheck(context)
            }
        }
    }
}
