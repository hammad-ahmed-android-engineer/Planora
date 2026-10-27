package com.example.planora.presentation.taskeditor.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import com.example.planora.domain.model.RecurrenceType
import com.example.planora.core.designsystem.theme.AppTypography
import com.example.planora.core.utils.formatRecurrenceLabel

@Composable
fun RecurrenceDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onRecurrenceSelected: (RecurrenceType) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        RecurrenceType.entries.forEach { type ->
            DropdownMenuItem(
                text = { AppTypography.BodyMedium(text = type.formatRecurrenceLabel()) },
                onClick = {
                    onRecurrenceSelected(type)
                    onDismissRequest()
                }
            )
        }
    }
}
