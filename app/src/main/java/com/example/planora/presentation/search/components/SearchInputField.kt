package com.example.planora.presentation.search.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.core.designsystem.components.AppIconButton
import com.example.planora.core.designsystem.components.AppOutlinedTextField
import com.example.planora.core.utils.rememberDebouncedClick

@Composable
fun SearchInputField(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val debouncedClear = rememberDebouncedClick { onQueryChange("") }

    AppOutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = stringResource(R.string.search_placeholder),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.nav_search),
                tint = primaryColor
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                AppIconButton(
                    onClick = debouncedClear,
                    icon = Icons.Default.Clear,
                    contentDescription = stringResource(R.string.search_clear),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        singleLine = true,
        modifier = modifier.fillMaxWidth()
    )
}
