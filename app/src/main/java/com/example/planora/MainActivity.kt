package com.example.planora

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.planora.core.designsystem.theme.PlanoraTheme
import com.example.planora.domain.model.UserPreferences
import com.example.planora.presentation.MainViewModel
import com.example.planora.presentation.navigation.MainAppNavigation
import com.example.planora.presentation.splash.SplashScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val requestNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        // Permission result handled
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Planora)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (savedInstanceState == null) {
            intent?.getStringExtra("EXTRA_NAVIGATE_TASK_ID")?.let { taskId ->
                viewModel.setNavigateTaskId(taskId)
            }
        }

        checkAndRequestPermissions()

        setContent {
            val userPreferences by viewModel.userPreferences.collectAsStateWithLifecycle(initialValue = UserPreferences())
            val navigateTaskId by viewModel.navigateTaskId.collectAsStateWithLifecycle()
            var showSplash by rememberSaveable { mutableStateOf(true) }

            PlanoraTheme(
                themeMode = userPreferences.themeMode,
                accentColorHex = userPreferences.accentColorHex
            ) {
                Crossfade(
                    targetState = showSplash,
                    animationSpec = tween(450),
                    label = "SplashCrossfade"
                ) { isSplash ->
                    if (isSplash) {
                        SplashScreen(
                            onSplashFinished = { showSplash = false }
                        )
                    } else {
                        MainAppNavigation(
                            initialTaskId = navigateTaskId,
                            onTaskNavigated = viewModel::onTaskNavigated
                        )
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        intent.getStringExtra("EXTRA_NAVIGATE_TASK_ID")?.let { taskId ->
            viewModel.setNavigateTaskId(taskId)
        }
    }

    private fun checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}
