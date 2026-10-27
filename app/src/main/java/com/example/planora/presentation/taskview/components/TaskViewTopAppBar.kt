package com.example.planora.presentation.taskview.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteSweep
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
import com.example.planora.domain.model.TaskViewType
import com.example.planora.core.designsystem.theme.AppTypography
import com.example.planora.core.designsystem.theme.IndigoPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskViewTopAppBar(
    viewType: TaskViewType,
    hasTasks: Boolean,
    onNavigateBack: () -> Unit,
    onOpenTrashConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            AppTypography.TitleLarge(text = viewType.title)
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = IndigoPrimary
                )
            }
        },
        actions = {
            if (viewType == TaskViewType.TRASH && hasTasks) {
                IconButton(onClick = onOpenTrashConfirm) {
                    Icon(
                        imageVector = Icons.Default.DeleteSweep,
                        contentDescription = stringResource(R.string.trash_empty_button),
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
