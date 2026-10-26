package com.example.planora.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.util.rememberDebouncedClick
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTimePickerDialog(
    onTimeSelected: (String) -> Unit, // HH:mm
    onDismiss: () -> Unit
) {
    val timePickerState = rememberTimePickerState(
        initialHour = LocalTime.now().hour,
        initialMinute = LocalTime.now().minute,
        is24Hour = true
    )
    val debouncedDismiss = rememberDebouncedClick(onClick = onDismiss)

    AlertDialog(
        onDismissRequest = debouncedDismiss,
        confirmButton = {
            val debouncedConfirm = rememberDebouncedClick {
                val timeStr = LocalTime.of(timePickerState.hour, timePickerState.minute)
                    .format(DateTimeFormatter.ofPattern("HH:mm"))
                onTimeSelected(timeStr)
                onDismiss()
            }
            TextButton(onClick = debouncedConfirm) {
                AppTypography.BodyMedium(text = "OK", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = debouncedDismiss) {
                AppTypography.BodyMedium(text = "Cancel", fontWeight = FontWeight.Medium)
            }
        },
        text = {
            TimePicker(state = timePickerState)
        }
    )
}
