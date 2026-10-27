package com.example.planora.core.designsystem.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.core.utils.rememberDebouncedClick
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTimePickerDialog(
    onTimeSelected: (String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    initialHour: Int = LocalTime.now().hour,
    initialMinute: Int = LocalTime.now().minute,
    is24Hour: Boolean = true,
    confirmText: String = stringResource(R.string.confirm),
    dismissText: String = stringResource(R.string.cancel)
) {
    val timePickerState = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        is24Hour = is24Hour
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
            AppTextButton(
                text = confirmText,
                onClick = debouncedConfirm
            )
        },
        dismissButton = {
            AppTextButton(
                text = dismissText,
                onClick = debouncedDismiss
            )
        },
        text = {
            TimePicker(state = timePickerState)
        },
        modifier = modifier
    )
}
