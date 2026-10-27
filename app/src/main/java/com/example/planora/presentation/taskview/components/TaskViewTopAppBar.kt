package com.example.planora.presentation.taskview.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.core.designsystem.components.AppIconButton
import com.example.planora.core.designsystem.components.AppTopAppBar
import com.example.planora.core.designsystem.theme.IndigoPrimary
import com.example.planora.domain.model.TaskViewType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskViewTopAppBar(
    viewType: TaskViewType,
    hasTasks: Boolean,
    onNavigateBack: () -> Unit,
    onOpenTrashConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppTopAppBar(
        title = viewType.title,
        onNavigateBack = onNavigateBack,
        navigationIconColor = IndigoPrimary,
        actions = {
            if (viewType == TaskViewType.TRASH && hasTasks) {
                AppIconButton(
                    onClick = onOpenTrashConfirm,
                    icon = Icons.Default.DeleteSweep,
                    contentDescription = stringResource(R.string.trash_empty_button),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        },
        modifier = modifier
    )
}
