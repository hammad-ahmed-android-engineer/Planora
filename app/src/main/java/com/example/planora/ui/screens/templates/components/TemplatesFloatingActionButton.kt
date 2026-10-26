package com.example.planora.ui.screens.templates.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.ui.theme.IndigoPrimary
import com.example.planora.ui.theme.dimensions
import com.example.planora.util.rememberDebouncedClick

@Composable
fun TemplatesFloatingActionButton(
    onCreateTemplate: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    val debouncedCreate = rememberDebouncedClick(onClick = onCreateTemplate)
    FloatingActionButton(
        onClick = debouncedCreate,
        containerColor = IndigoPrimary,
        contentColor = Color.White,
        shape = RoundedCornerShape(dims.corners.large),
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.templates_create_button),
            modifier = Modifier.size(dims.icons.large)
        )
    }
}
