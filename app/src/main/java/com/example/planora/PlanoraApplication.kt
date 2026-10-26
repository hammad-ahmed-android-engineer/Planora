package com.example.planora

import android.app.Application
import com.example.planora.notification.StreakNotificationReceiver
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PlanoraApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        StreakNotificationReceiver.scheduleDailyStreakCheck(this)
    }
}
