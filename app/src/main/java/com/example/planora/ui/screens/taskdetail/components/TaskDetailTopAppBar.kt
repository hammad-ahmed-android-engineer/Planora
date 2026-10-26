package com.example.planora.ui.screens.taskdetail.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.domain.model.Task
import com.example.planora.ui.components.AppTypography
import com.example.planora.ui.theme.FlameYellow
import com.example.planora.ui.theme.IndigoPrimary
import com.example.planora.util.rememberDebouncedClick

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

    TopAppBar(
        title = {
            AppTypography.TitleLarge(text = stringResource(R.string.task_detail_title))
        },
        navigationIcon = {
            IconButton(onClick = debouncedBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = IndigoPrimary
                )
            }
        },
        actions = {
            task?.let { t ->
                val debouncedEditIcon = rememberDebouncedClick { onNavigateToEdit(t.id) }
                IconButton(onClick = debouncedPin) {
                    Icon(
                        imageVector = Icons.Default.PushPin,
                        contentDescription = stringResource(R.string.action_pin),
                        tint = if (t.isPinned) IndigoPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = debouncedFav) {
                    Icon(
                        imageVector = if (t.isStarred) Icons.Default.Star else Icons.Outlined.StarBorder,
                        contentDescription = stringResource(R.string.action_favorite),
                        tint = if (t.isStarred) FlameYellow else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = debouncedEditIcon) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.action_edit),
                        tint = IndigoPrimary
                    )
                }
                IconButton(onClick = debouncedDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.action_delete),
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = modifier
    )
}
