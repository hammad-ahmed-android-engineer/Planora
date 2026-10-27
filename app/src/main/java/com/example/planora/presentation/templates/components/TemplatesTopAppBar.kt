package com.example.planora.presentation.templates.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.core.designsystem.components.AppTopAppBar
import com.example.planora.core.designsystem.theme.IndigoPrimary
import com.example.planora.core.utils.rememberDebouncedClick

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplatesTopAppBar(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val debouncedBack = rememberDebouncedClick(onClick = onNavigateBack)
    AppTopAppBar(
        title = stringResource(R.string.templates_title),
        onNavigateBack = debouncedBack,
        navigationIconColor = IndigoPrimary,
        modifier = modifier
    )
}
