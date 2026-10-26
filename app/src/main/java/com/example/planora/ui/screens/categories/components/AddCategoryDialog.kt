package com.example.planora.ui.screens.categories.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.R
import com.example.planora.ui.components.AppTypography
import com.example.planora.ui.theme.IndigoPrimary
import com.example.planora.ui.theme.dimensions
import com.example.planora.util.parseHexColor
import com.example.planora.util.clickableDebounced
import com.example.planora.util.rememberDebouncedClick

@Composable
fun AddCategoryDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, colorHex: String) -> Unit
) {
    val dims = dimensions
    var name by rememberSaveable { mutableStateOf("") }
    val colors = listOf("#6366F1", "#8B5CF6", "#10B981", "#06B6D4", "#F59E0B", "#F43F5E")
    var selectedColor by rememberSaveable { mutableStateOf(colors[0]) }

    val debouncedConfirm = rememberDebouncedClick {
        if (name.isNotBlank()) onConfirm(name.trim(), selectedColor)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { AppTypography.TitleMedium(text = stringResource(R.string.category_new_dialog_title), fontWeight = FontWeight.Bold) },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { AppTypography.BodyMedium(text = stringResource(R.string.category_name_label)) },
                    singleLine = true,
                    shape = RoundedCornerShape(dims.corners.medium),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = IndigoPrimary),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(dims.spacing.default))

                AppTypography.SubText(
                    text = stringResource(R.string.category_pick_color),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(dims.spacing.small))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    colors.forEach { hex ->
                        val color = hex.parseHexColor()
                        val isSelected = selectedColor == hex
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(dims.components.colorPickerCircle)
                                .clip(CircleShape)
                                .background(color)
                                .clickableDebounced { selectedColor = hex }
                                .then(
                                    if (isSelected) Modifier.border(dims.borders.medium, Color.White, CircleShape) else Modifier
                                )
                        ) {
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(dims.icons.small)
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = debouncedConfirm,
                enabled = name.isNotBlank(),
                shape = RoundedCornerShape(dims.corners.small),
                colors = ButtonDefaults.buttonColors(containerColor = IndigoPrimary)
            ) {
                AppTypography.BodyMedium(text = stringResource(R.string.category_create_button), color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                AppTypography.BodyMedium(text = stringResource(R.string.cancel))
            }
        }
    )
}
