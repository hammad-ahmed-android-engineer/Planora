package com.example.planora.presentation.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.core.designsystem.components.AppFilterChip
import com.example.planora.core.designsystem.theme.AppTypography
import com.example.planora.core.designsystem.theme.dimensions
import com.example.planora.core.utils.color
import com.example.planora.domain.model.Priority

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PriorityFilterSection(
    selectedPriority: Priority?,
    onSelectPriority: (Priority?) -> Unit,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    val primaryColor = MaterialTheme.colorScheme.primary
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.FilterList,
                contentDescription = null,
                tint = primaryColor,
                modifier = Modifier.size(dims.icons.small)
            )
            Spacer(modifier = Modifier.width(dims.spacing.tiny))
            AppTypography.SubText(
                text = stringResource(R.string.filter_priority_header),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(dims.spacing.small))

        FlowRow(horizontalArrangement = Arrangement.spacedBy(dims.spacing.small)) {
            AppFilterChip(
                selected = selectedPriority == null,
                onClick = { onSelectPriority(null) },
                label = stringResource(R.string.filter_all_tasks),
                selectedColor = primaryColor
            )

            Priority.entries.filter { it != Priority.NONE }.forEach { priority ->
                val isSelected = selectedPriority == priority
                val chipColor = priority.color

                AppFilterChip(
                    selected = isSelected,
                    onClick = { onSelectPriority(if (isSelected) null else priority) },
                    label = priority.label,
                    selectedColor = chipColor
                )
            }
        }
    }
}
