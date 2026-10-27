package com.example.planora.presentation.search.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.core.designsystem.components.AppTextButton
import com.example.planora.core.designsystem.theme.AppTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    hasActiveFilters: Boolean,
    onClearFilters: () -> Unit,
    modifier: Modifier = Modifier
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    TopAppBar(
        title = {
            AppTypography.TitleLarge(text = stringResource(R.string.search_title))
        },
        actions = {
            if (hasActiveFilters) {
                AppTextButton(
                    text = stringResource(R.string.search_clear_all),
                    onClick = onClearFilters,
                    color = primaryColor
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = modifier
    )
}
