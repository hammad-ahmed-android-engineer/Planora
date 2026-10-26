package com.example.planora.ui.screens.templates.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.domain.model.Category
import com.example.planora.domain.model.TaskTemplate
import com.example.planora.ui.components.EmptyStateView
import com.example.planora.ui.theme.dimensions

@Composable
fun TemplatesContent(
    templatesList: List<TaskTemplate>,
    categoriesList: List<Category>,
    isLoading: Boolean,
    isLandscape: Boolean,
    onCreateTemplate: () -> Unit,
    onUseTemplate: (TaskTemplate) -> Unit,
    onEditTemplate: (TaskTemplate) -> Unit,
    onDeleteTemplate: (TaskTemplate) -> Unit,
    modifier: Modifier = Modifier
) {
    val dims = dimensions

    if (templatesList.isEmpty() && !isLoading) {
        EmptyStateView(
            title = stringResource(R.string.templates_no_templates_title),
            subtitle = stringResource(R.string.templates_no_templates_subtitle),
            icon = Icons.AutoMirrored.Filled.FormatListBulleted,
            actionText = stringResource(R.string.templates_create_button),
            onActionClick = onCreateTemplate,
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
            items(items = templatesList, key = { it.id }) { tpl ->
                val category = categoriesList.find { it.id == tpl.categoryId }
                TemplateCardItem(
                    template = tpl,
                    category = category,
                    onUseTemplate = onUseTemplate,
                    onEditTemplate = onEditTemplate,
                    onDeleteTemplate = onDeleteTemplate,
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
            items(items = templatesList, key = { it.id }) { tpl ->
                val category = categoriesList.find { it.id == tpl.categoryId }
                TemplateCardItem(
                    template = tpl,
                    category = category,
                    onUseTemplate = onUseTemplate,
                    onEditTemplate = onEditTemplate,
                    onDeleteTemplate = onDeleteTemplate,
                    modifier = Modifier.animateItem()
                )
            }
        }
    }
}
