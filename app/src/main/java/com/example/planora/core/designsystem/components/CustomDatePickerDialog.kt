package com.example.planora.core.designsystem.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.core.utils.rememberDebouncedClick
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun CustomDatePickerDialog(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    initialSelectedDateMillis: Long? = null,
    confirmText: String = stringResource(R.string.confirm),
    dismissText: String = stringResource(R.string.cancel)
) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialSelectedDateMillis)
    val debouncedDismiss = rememberDebouncedClick(onClick = onDismiss)

    DatePickerDialog(
        onDismissRequest = debouncedDismiss,
        confirmButton = {
            val debouncedConfirm = rememberDebouncedClick {
                datePickerState.selectedDateMillis?.let { ms ->
                    val dateStr = Instant.ofEpochMilli(ms)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                        .format(DateTimeFormatter.ISO_LOCAL_DATE)
                    onDateSelected(dateStr)
                }
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
        modifier = modifier
    ) {
        DatePicker(state = datePickerState)
    }
}
