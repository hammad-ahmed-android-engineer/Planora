package com.example.planora.presentation.taskdetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.core.designsystem.components.AppButton
import com.example.planora.core.designsystem.theme.IndigoPrimary
import com.example.planora.core.designsystem.theme.dimensions
import com.example.planora.core.utils.rememberDebouncedClick

@Composable
fun TaskDetailQuickActions(
    onPostpone: () -> Unit,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    val debouncedPostpone = rememberDebouncedClick(onClick = onPostpone)
    val debouncedEdit = rememberDebouncedClick(onClick = onEdit)

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dims.spacing.small)
    ) {
        AppButton(
            text = stringResource(R.string.action_postpone_tomorrow),
            onClick = debouncedPostpone,
            icon = Icons.Default.Schedule,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )

        AppButton(
            text = stringResource(R.string.action_edit),
            onClick = debouncedEdit,
            icon = Icons.Default.Edit,
            containerColor = IndigoPrimary,
            modifier = Modifier.weight(1f)
        )
    }
}
