package com.example.planora.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.planora.domain.model.Task
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Handles scheduling and cancelling exact alarm reminders for tasks.
 */
import javax.inject.Inject
import dagger.hilt.android.qualifiers.ApplicationContext

class AlarmScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    /**
     * Schedules a reminder alarm for [task] based on its due date and time.
     */
    fun scheduleTaskReminder(task: Task) {
        if (task.dueDate == null || task.isCompleted || task.isDeleted) {
            cancelTaskReminder(task.id)
            return
        }

        try {
            val date = LocalDate.parse(task.dueDate)
            val time = task.dueTime?.let { LocalTime.parse(it, DateTimeFormatter.ofPattern("HH:mm")) } ?: LocalTime.of(9, 0)
            val triggerTimestamp = LocalDateTime.of(date, time)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()

            scheduleReminder(task, triggerTimestamp, task.id.hashCode())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Cancels an existing reminder alarm for task with [taskId].
     */
    fun cancelTaskReminder(taskId: String) {
        cancelReminder(taskId.hashCode())
    }

    /**
     * Schedules an exact alarm at [triggerTimestamp] for [task] with [notificationId].
     */
    fun scheduleReminder(task: Task, triggerTimestamp: Long, notificationId: Int) {
        if (triggerTimestamp <= System.currentTimeMillis()) return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
            return
        }

        val intent = Intent(context, TaskReminderReceiver::class.java).apply {
            putExtra("EXTRA_TASK_ID", task.id)
            putExtra("EXTRA_TASK_TITLE", task.title)
            putExtra("EXTRA_TASK_DESC", task.description)
            putExtra("EXTRA_NOTIFICATION_ID", notificationId)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerTimestamp,
                pendingIntent
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Cancels an alarm associated with [notificationId].
     */
    fun cancelReminder(notificationId: Int) {
        val intent = Intent(context, TaskReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        pendingIntent?.let {
            alarmManager.cancel(it)
            it.cancel()
        }
    }
}
