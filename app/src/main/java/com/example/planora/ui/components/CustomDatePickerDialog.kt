package com.example.planora.ui.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.util.rememberDebouncedClick
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    onDateSelected: (String) -> Unit, // YYYY-MM-DD
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()
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
            TextButton(onClick = debouncedConfirm) {
                AppTypography.BodyMedium(text = "OK", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = debouncedDismiss) {
                AppTypography.BodyMedium(text = "Cancel", fontWeight = FontWeight.Medium)
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
