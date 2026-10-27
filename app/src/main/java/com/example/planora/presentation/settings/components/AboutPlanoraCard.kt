package com.example.planora.presentation.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.R
import com.example.planora.core.designsystem.components.AppCard
import com.example.planora.core.designsystem.theme.AppTypography
import com.example.planora.core.designsystem.theme.dimensions

@Composable
fun AboutPlanoraCard(
    modifier: Modifier = Modifier
) {
    val dims = dimensions

    AppCard(
        modifier = modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
    ) {
        Column(modifier = Modifier.padding(dims.spacing.default)) {
            AppTypography.TitleSmall(
                text = stringResource(R.string.settings_about_title),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(dims.spacing.tiny))
            AppTypography.BodySmall(
                text = stringResource(R.string.settings_about_desc),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
