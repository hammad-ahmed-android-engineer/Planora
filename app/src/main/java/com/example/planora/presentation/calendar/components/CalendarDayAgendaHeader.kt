package com.example.planora.presentation.calendar.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.core.designsystem.theme.AppTypography
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun CalendarDayAgendaHeader(
    selectedDate: LocalDate,
    taskCount: Int
) {
    val formatted = remember(selectedDate) {
        selectedDate.format(DateTimeFormatter.ofPattern("EEEE, MMMM d"))
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            AppTypography.TitleMedium(
                text = formatted,
                color = MaterialTheme.colorScheme.onSurface
            )
            AppTypography.Caption(
                text = if (taskCount == 1) {
                    stringResource(R.string.calendar_scheduled_one)
                } else {
                    stringResource(R.string.calendar_scheduled_count, taskCount)
                },
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
