package com.example.planora.presentation.templates.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.planora.core.designsystem.theme.AppTypography
import com.example.planora.core.designsystem.theme.IndigoPrimary
import com.example.planora.core.utils.rememberDebouncedClick

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplatesTopAppBar(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val debouncedBack = rememberDebouncedClick(onClick = onNavigateBack)
    TopAppBar(
        title = {
            AppTypography.TitleLarge(text = stringResource(R.string.templates_title))
        },
        navigationIcon = {
            IconButton(onClick = debouncedBack) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back), tint = IndigoPrimary)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = modifier
    )
}
