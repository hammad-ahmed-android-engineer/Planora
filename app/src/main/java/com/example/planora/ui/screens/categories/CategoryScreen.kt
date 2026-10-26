package com.example.planora.ui.screens.categories

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.planora.domain.model.Category
import com.example.planora.ui.components.AppTypography
import com.example.planora.ui.screens.categories.components.AddCategoryDialog
import com.example.planora.ui.screens.categories.components.CategoryDeleteDialog
import com.example.planora.ui.screens.categories.components.CategoryListContent
import com.example.planora.ui.theme.IndigoPrimary
import com.example.planora.ui.theme.dimensions
import com.example.planora.util.rememberDebouncedClick

@OptIn(ExperimentalMaterial3Api::class)
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
            TopAppBar(
                title = {
                    AppTypography.TitleLarge(text = stringResource(R.string.categories_title))
                },
                navigationIcon = {
                    IconButton(onClick = debouncedBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back), tint = IndigoPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = debouncedOpenAdd,
                containerColor = IndigoPrimary,
                contentColor = Color.White,
                shape = RoundedCornerShape(dims.corners.large)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(R.string.category_create_button))
            }
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
            CategoryDeleteDialog(
                category = cat,
                onDismiss = { categoryToDeleteId = null },
                onConfirm = {
                    onDeleteCategory(cat, null)
                    categoryToDeleteId = null
                }
            )
        }
    }
}
