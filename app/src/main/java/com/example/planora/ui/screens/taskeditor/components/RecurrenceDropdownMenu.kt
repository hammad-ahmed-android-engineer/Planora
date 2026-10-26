package com.example.planora.ui.screens.taskeditor.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import com.example.planora.domain.model.RecurrenceType
import com.example.planora.ui.components.AppTypography
import com.example.planora.util.formatRecurrenceLabel

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
