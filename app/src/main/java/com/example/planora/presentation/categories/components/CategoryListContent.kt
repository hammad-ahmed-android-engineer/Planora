package com.example.planora.presentation.categories.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.domain.model.Category
import com.example.planora.core.designsystem.components.EmptyStateView
import com.example.planora.core.designsystem.theme.dimensions

@Composable
fun CategoryListContent(
    categories: List<Category>,
    isLoading: Boolean,
    isLandscape: Boolean,
    onOpenAdd: () -> Unit,
    onDeleteCategoryRequest: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val dims = dimensions

    if (categories.isEmpty() && !isLoading) {
        EmptyStateView(
            title = stringResource(R.string.category_no_categories_title),
            subtitle = stringResource(R.string.category_no_categories_subtitle),
            actionText = stringResource(R.string.category_create_button),
            onActionClick = onOpenAdd,
            modifier = modifier.fillMaxSize()
        )
    } else if (isLandscape) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(dims.components.emptyStateIconContainer * 4),
            modifier = modifier
                .fillMaxSize()
                .padding(dims.spacing.medium),
            horizontalArrangement = Arrangement.spacedBy(dims.spacing.small),
            verticalArrangement = Arrangement.spacedBy(dims.spacing.small)
        ) {
            items(categories, key = { it.id }) { cat ->
                CategoryCardItem(
                    category = cat,
                    onDelete = { onDeleteCategoryRequest(cat.id) },
                    modifier = Modifier.animateItem()
                )
            }
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = dims.spacing.medium, vertical = dims.spacing.small)
        ) {
            items(categories, key = { it.id }) { cat ->
                CategoryCardItem(
                    category = cat,
                    onDelete = { onDeleteCategoryRequest(cat.id) },
                    modifier = Modifier.animateItem()
                )
            }
        }
    }
}
