package com.example.planora.presentation.templates.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.core.designsystem.components.AppFloatingActionButton
import com.example.planora.core.designsystem.theme.IndigoPrimary

@Composable
fun TemplatesFloatingActionButton(
    onCreateTemplate: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppFloatingActionButton(
        onClick = onCreateTemplate,
        icon = Icons.Default.Add,
        contentDescription = stringResource(R.string.templates_create_button),
        containerColor = IndigoPrimary,
        contentColor = Color.White,
        modifier = modifier
    )
}
