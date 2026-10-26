package com.example.planora.notification

import android.app.KeyguardManager
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.planora.notification.components.FullScreenAlertContent
import com.example.planora.ui.theme.PlanoraTheme

/**
 * Full-screen alarm activity triggered when high priority reminders fire while device is locked.
 */
class FullScreenAlertActivity : ComponentActivity() {

    private lateinit var audioPlayer: AlarmAudioPlayer
    private lateinit var vibratorHelper: AlarmVibratorHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        audioPlayer = AlarmAudioPlayer(this)
        vibratorHelper = AlarmVibratorHelper(this)

        turnScreenOnAndKeyguardOff()
        audioPlayer.start()
        vibratorHelper.start()

        val taskId = intent.getStringExtra("EXTRA_TASK_ID") ?: ""
        val taskTitle = intent.getStringExtra("EXTRA_TASK_TITLE") ?: "Scheduled Task Reminder"
        val taskDesc = intent.getStringExtra("EXTRA_TASK_DESC") ?: ""
        val notificationId = intent.getIntExtra("EXTRA_NOTIFICATION_ID", taskId.hashCode())

        setContent {
            PlanoraTheme {
                FullScreenAlertContent(
                    taskTitle = taskTitle,
                    taskDesc = taskDesc,
                    onMarkComplete = {
                        stopSoundAndVibration()
                        dismissNotification(notificationId)
                        sendBroadcast(Intent(this, TaskActionReceiver::class.java).apply {
                            action = TaskActionReceiver.ACTION_COMPLETE
                            putExtra("EXTRA_TASK_ID", taskId)
                            putExtra("EXTRA_NOTIFICATION_ID", notificationId)
                        })
                        finish()
                    },
                    onSnooze = {
                        stopSoundAndVibration()
                        dismissNotification(notificationId)
                        sendBroadcast(Intent(this, TaskActionReceiver::class.java).apply {
                            action = TaskActionReceiver.ACTION_SNOOZE
                            putExtra("EXTRA_TASK_ID", taskId)
                            putExtra("EXTRA_NOTIFICATION_ID", notificationId)
                        })
                        finish()
                    },
                    onDismiss = {
                        stopSoundAndVibration()
                        dismissNotification(notificationId)
                        finish()
                    }
                )
            }
        }
    }

    private fun turnScreenOnAndKeyguardOff() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
            val keyguardManager = getSystemService(KEYGUARD_SERVICE) as KeyguardManager
            keyguardManager.requestDismissKeyguard(this, null)
        }
        @Suppress("DEPRECATION")
        window.addFlags(
            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )
    }

    private fun stopSoundAndVibration() {
        audioPlayer.stop()
        vibratorHelper.stop()
    }

    private fun dismissNotification(notificationId: Int) {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(notificationId)
    }

    override fun onDestroy() {
        stopSoundAndVibration()
        super.onDestroy()
    }
}

