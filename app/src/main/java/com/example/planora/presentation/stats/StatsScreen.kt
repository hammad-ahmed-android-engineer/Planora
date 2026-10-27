package com.example.planora.presentation.stats

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.core.designsystem.components.AppTopAppBar
import com.example.planora.presentation.stats.components.CategoryDistributionCard
import com.example.planora.presentation.stats.components.CompletionHeroCard
import com.example.planora.presentation.stats.components.MetricsGrid
import com.example.planora.presentation.stats.components.StreakMetricRow
import com.example.planora.core.designsystem.theme.dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(
    uiState: StatsUiState
) {
    val dims = dimensions
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val stats = uiState.stats

    Scaffold(
        topBar = {
            AppTopAppBar(
                title = stringResource(R.string.stats_analytics_title)
            )
        }
    ) { paddingValues ->
        if (isLandscape) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(dims.spacing.default)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1.1f)
                        .verticalScroll(rememberScrollState())
                        .padding(end = dims.spacing.medium)
                ) {
                    CompletionHeroCard(completionRate = stats.completionRatePercentage)

                    Spacer(modifier = Modifier.height(dims.spacing.medium))

                    StreakMetricRow(
                        currentStreakDays = stats.currentStreakDays,
                        longestStreakDays = stats.longestStreakDays
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1.3f)
                        .verticalScroll(rememberScrollState())
                ) {
                    MetricsGrid(stats = stats)

                    Spacer(modifier = Modifier.height(dims.spacing.default))

                    CategoryDistributionCard(categories = stats.categoryDistribution)
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = dims.spacing.default, vertical = dims.spacing.small)
            ) {
                CompletionHeroCard(completionRate = stats.completionRatePercentage)

                Spacer(modifier = Modifier.height(dims.spacing.medium))

                StreakMetricRow(
                    currentStreakDays = stats.currentStreakDays,
                    longestStreakDays = stats.longestStreakDays
                )

                Spacer(modifier = Modifier.height(dims.spacing.medium))

                MetricsGrid(stats = stats)

                Spacer(modifier = Modifier.height(dims.spacing.default))

                CategoryDistributionCard(categories = stats.categoryDistribution)

                Spacer(modifier = Modifier.height(dims.spacing.large))
            }
        }
    }
}
