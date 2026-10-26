package com.example.planora.ui.screens.taskdetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.R
import com.example.planora.ui.components.AppTypography
import com.example.planora.ui.theme.IndigoPrimary
import com.example.planora.ui.theme.dimensions
import com.example.planora.util.rememberDebouncedClick

@Composable
fun TaskDetailQuickActions(
    onPostpone: () -> Unit,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    val debouncedPostpone = rememberDebouncedClick(onClick = onPostpone)
    val debouncedEdit = rememberDebouncedClick(onClick = onEdit)

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dims.spacing.small)
    ) {
        OutlinedButton(
            onClick = debouncedPostpone,
            shape = RoundedCornerShape(dims.corners.medium),
            modifier = Modifier
                .weight(1f)
                .height(dims.components.buttonHeight)
        ) {
            Icon(imageVector = Icons.Default.Schedule, contentDescription = null, modifier = Modifier.size(dims.icons.small))
            Spacer(modifier = Modifier.width(dims.spacing.tiny))
            AppTypography.BodyMedium(text = stringResource(R.string.action_postpone_tomorrow), fontWeight = FontWeight.SemiBold)
        }

        Button(
            onClick = debouncedEdit,
            shape = RoundedCornerShape(dims.corners.medium),
            colors = ButtonDefaults.buttonColors(containerColor = IndigoPrimary, contentColor = Color.White),
            modifier = Modifier
                .weight(1f)
                .height(dims.components.buttonHeight)
        ) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(dims.icons.small))
            Spacer(modifier = Modifier.width(dims.spacing.tiny))
            AppTypography.BodyMedium(text = stringResource(R.string.action_edit), fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}
