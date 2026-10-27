package com.example.planora.core.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.planora.MainActivity
import com.example.planora.R
import com.example.planora.data.datastore.UserPreferencesDataStore
import com.example.planora.data.local.PlanoraDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import javax.inject.Inject

class StreakNotificationReceiver : BroadcastReceiver() {

    @Inject
    lateinit var userPreferencesDataStore: UserPreferencesDataStore

    override fun onReceive(context: Context, intent: Intent) {
        val database = PlanoraDatabase.getDatabase(context)

        CoroutineScope(Dispatchers.IO).launch {
            val userPreferences = if (::userPreferencesDataStore.isInitialized) {
                userPreferencesDataStore.userPreferencesFlow.firstOrNull()
            } else null

            if (userPreferences == null || !userPreferences.notificationsEnabled) return@launch

            val today = LocalDate.now()
            val zone = ZoneId.systemDefault()

            val completedTasks = database.taskDao().getCompletedTasksDirect()
            val completedTodayCount = completedTasks.count { taskWithDetails ->
                val completionTs = taskWithDetails.task.completionTimestamp
                completionTs != null && Instant.ofEpochMilli(completionTs).atZone(zone).toLocalDate() == today
            }

            createNotificationChannel(context)

            val openIntent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            val pendingIntent = PendingIntent.getActivity(
                context,
                STREAK_NOTIFICATION_ID,
                openIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val milestones = setOf(3, 7, 14, 30, 50, 100)

            if (userPreferences.currentStreak > 0 && completedTodayCount == 0) {
                val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("🔥 Keep your ${userPreferences.currentStreak}-day streak alive!")
                    .setContentText("You haven't completed any tasks today. Finish at least 1 task before midnight!")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .build()

                notificationManager.notify(STREAK_NOTIFICATION_ID, notification)
            } else if (completedTodayCount > 0 && milestones.contains(userPreferences.currentStreak)) {
                val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("🎉 ${userPreferences.currentStreak}-Day Streak Milestone!")
                    .setContentText("Outstanding consistency! You've maintained your productivity streak for ${userPreferences.currentStreak} days!")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .build()

                notificationManager.notify(STREAK_NOTIFICATION_ID + 1, notification)
            }

            scheduleDailyStreakCheck(context)
        }
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Streak & Productivity Updates"
            val descriptionText = "Notifications about streak risk warnings and productivity milestones"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_ID = "planora_streak_notifications"
        const val STREAK_NOTIFICATION_ID = 8888

        fun scheduleDailyStreakCheck(context: Context) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent = Intent(context, StreakNotificationReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                STREAK_NOTIFICATION_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, 20) // 8:00 PM
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                if (timeInMillis <= System.currentTimeMillis()) {
                    add(Calendar.DAY_OF_YEAR, 1)
                }
            }

            try {
                alarmManager.setAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
