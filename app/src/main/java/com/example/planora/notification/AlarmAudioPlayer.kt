package com.example.planora.notification

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import android.provider.Settings

/**
 * Helper class responsible for playing and stopping alarm audio ringtones.
 * Configured with USAGE_ALARM so audio rings loudly on the system alarm stream.
 */
class AlarmAudioPlayer(private val context: Context) {

    private var ringtone: Ringtone? = null
    private var mediaPlayer: MediaPlayer? = null

    /**
     * Starts playing the default system alarm ringtone in a continuous loop.
     */
    fun start() {
        try {
            stop()
            var alertUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            if (alertUri == null) {
                alertUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
            }
            if (alertUri == null) {
                alertUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            }
            if (alertUri == null) {
                alertUri = Settings.System.DEFAULT_ALARM_ALERT_URI
            }

            try {
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(context.applicationContext, alertUri)
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_ALARM)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build()
                    )
                    setVolume(1.0f, 1.0f)
                    isLooping = true
                    prepare()
                    start()
                }
            } catch (_: Exception) {
                ringtone = RingtoneManager.getRingtone(context.applicationContext, alertUri)?.apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        audioAttributes = AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_ALARM)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build()
                        isLooping = true
                    }
                    play()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Stops ringtone playback.
     */
    fun stop() {
        try {
            ringtone?.stop()
            ringtone = null
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
