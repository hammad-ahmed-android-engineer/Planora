package com.example.planora.presentation.categories

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.core.designsystem.components.AppFloatingActionButton
import com.example.planora.core.designsystem.components.AppTopAppBar
import com.example.planora.core.designsystem.components.ConfirmationDialog
import com.example.planora.core.designsystem.theme.IndigoPrimary
import com.example.planora.core.designsystem.theme.dimensions
import com.example.planora.core.utils.rememberDebouncedClick
import com.example.planora.domain.model.Category
import com.example.planora.presentation.categories.components.AddCategoryDialog
import com.example.planora.presentation.categories.components.CategoryListContent

@Composable
fun CategoryScreen(
    uiState: CategoryUiState,
    onNavigateBack: () -> Unit,
    onAddCategory: (name: String, colorHex: String) -> Unit,
    onDeleteCategory: (Category, String?) -> Unit
) {
    val dims = dimensions
    val categories = uiState.categories
    var showAddDialog by rememberSaveable { mutableStateOf(false) }
    var categoryToDeleteId by rememberSaveable { mutableStateOf<String?>(null) }
    val categoryToDelete = remember(categoryToDeleteId, categories) {
        categories.find { it.id == categoryToDeleteId }
    }
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    val debouncedBack = rememberDebouncedClick(onClick = onNavigateBack)
    val debouncedOpenAdd = rememberDebouncedClick { showAddDialog = true }

    Scaffold(
        topBar = {
            AppTopAppBar(
                title = stringResource(R.string.categories_title),
                onNavigateBack = debouncedBack
            )
        },
        floatingActionButton = {
            AppFloatingActionButton(
                onClick = debouncedOpenAdd,
                icon = Icons.Default.Add,
                contentDescription = stringResource(R.string.category_create_button),
                containerColor = IndigoPrimary,
                contentColor = Color.White,
                shape = RoundedCornerShape(dims.corners.large)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            CategoryListContent(
                categories = categories,
                isLoading = uiState.isLoading,
                isLandscape = isLandscape,
                onOpenAdd = debouncedOpenAdd,
                onDeleteCategoryRequest = { categoryToDeleteId = it }
            )
        }

        if (showAddDialog) {
            AddCategoryDialog(
                onDismiss = { showAddDialog = false },
                onConfirm = { name, hex ->
                    onAddCategory(name, hex)
                    showAddDialog = false
                }
            )
        }

        categoryToDelete?.let { cat ->
            val debouncedConfirm = rememberDebouncedClick {
                onDeleteCategory(cat, null)
                categoryToDeleteId = null
            }
            val debouncedDismiss = rememberDebouncedClick {
                categoryToDeleteId = null
            }
            ConfirmationDialog(
                title = stringResource(R.string.category_delete_title),
                message = stringResource(R.string.category_delete_msg, cat.name),
                onConfirm = debouncedConfirm,
                onDismiss = debouncedDismiss,
                confirmText = stringResource(R.string.delete),
                dismissText = stringResource(R.string.cancel),
                isDestructive = true
            )
        }
    }
}
