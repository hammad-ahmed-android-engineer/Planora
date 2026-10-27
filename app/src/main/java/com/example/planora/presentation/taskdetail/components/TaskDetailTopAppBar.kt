package com.example.planora.presentation.taskdetail.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.core.designsystem.components.AppIconButton
import com.example.planora.core.designsystem.components.AppTopAppBar
import com.example.planora.core.designsystem.theme.FlameYellow
import com.example.planora.core.designsystem.theme.IndigoPrimary
import com.example.planora.core.utils.rememberDebouncedClick
import com.example.planora.domain.model.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailTopAppBar(
    task: Task?,
    onNavigateBack: () -> Unit,
    onNavigateToEdit: (String) -> Unit,
    onTogglePin: () -> Unit,
    onToggleFavorite: () -> Unit,
    onDeleteTask: () -> Unit,
    modifier: Modifier = Modifier
) {
    val debouncedBack = rememberDebouncedClick(onClick = onNavigateBack)
    val debouncedPin = rememberDebouncedClick(onClick = onTogglePin)
    val debouncedFav = rememberDebouncedClick(onClick = onToggleFavorite)
    val debouncedDelete = rememberDebouncedClick(onClick = onDeleteTask)

    AppTopAppBar(
        title = stringResource(R.string.task_detail_title),
        onNavigateBack = debouncedBack,
        navigationIconColor = IndigoPrimary,
        actions = {
            task?.let { t ->
                val debouncedEditIcon = rememberDebouncedClick { onNavigateToEdit(t.id) }
                AppIconButton(
                    onClick = debouncedPin,
                    icon = Icons.Default.PushPin,
                    contentDescription = stringResource(R.string.action_pin),
                    tint = if (t.isPinned) IndigoPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                )
                AppIconButton(
                    onClick = debouncedFav,
                    icon = if (t.isStarred) Icons.Default.Star else Icons.Outlined.StarBorder,
                    contentDescription = stringResource(R.string.action_favorite),
                    tint = if (t.isStarred) FlameYellow else MaterialTheme.colorScheme.onSurfaceVariant
                )
                AppIconButton(
                    onClick = debouncedEditIcon,
                    icon = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.action_edit),
                    tint = IndigoPrimary
                )
                AppIconButton(
                    onClick = debouncedDelete,
                    icon = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.action_delete),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        },
        modifier = modifier
    )
}
