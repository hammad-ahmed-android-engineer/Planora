package com.example.planora.ui.screens.calendar.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.planora.R
import com.example.planora.domain.model.Task
import com.example.planora.ui.components.AppTypography
import com.example.planora.ui.theme.IndigoPrimary
import com.example.planora.ui.theme.LocalDimensions
import com.example.planora.util.clickableDebounced
import com.example.planora.util.rememberDebouncedClick
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun CalendarMonthCard(
    currentMonth: YearMonth,
    onPrevMonth: () -> Unit,
    onNextMonth: () -> Unit,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    taskDateMap: Map<String?, List<Task>>,
    modifier: Modifier = Modifier
) {
    val dims = LocalDimensions.current
    val shape = RoundedCornerShape(dims.corners.extraLarge)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .border(dims.borders.thin, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f), shape),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = dims.elevations.low)
    ) {
        Column(modifier = Modifier.padding(dims.spacing.medium)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                val debouncedPrev = rememberDebouncedClick(onClick = onPrevMonth)
                val debouncedNext = rememberDebouncedClick(onClick = onNextMonth)

                IconButton(onClick = debouncedPrev) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.calendar_prev_month),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                AppTypography.TitleMedium(
                    text = currentMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                IconButton(onClick = debouncedNext) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = stringResource(R.string.calendar_next_month),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(dims.spacing.small))

            val daysOfWeek = remember { listOf("M", "T", "W", "T", "F", "S", "S") }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                daysOfWeek.forEach { day ->
                    AppTypography.Caption(
                        text = day,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(dims.spacing.small))

            val firstDayOfMonth = remember(currentMonth) { currentMonth.atDay(1) }
            val firstDayOfWeek = remember(firstDayOfMonth) {
                (firstDayOfMonth.dayOfWeek.value - 1) % 7
            }
            val daysInMonth = remember(currentMonth) { currentMonth.lengthOfMonth() }
            val totalCells = firstDayOfWeek + daysInMonth

            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dims.components.emptyStateIconContainer * 3),
                userScrollEnabled = false
            ) {
                items(totalCells) { index ->
                    if (index >= firstDayOfWeek) {
                        val dayNumber = index - firstDayOfWeek + 1
                        val date = currentMonth.atDay(dayNumber)
                        val isSelected = date == selectedDate
                        val isToday = date == LocalDate.now()
                        val dateStr = date.format(DateTimeFormatter.ISO_LOCAL_DATE)
                        val hasTasks = taskDateMap[dateStr]?.isNotEmpty() == true

                        val backgroundColor by animateColorAsState(
                            targetValue = when {
                                isSelected -> MaterialTheme.colorScheme.primary
                                isToday -> MaterialTheme.colorScheme.primaryContainer
                                else -> Color.Transparent
                            },
                            animationSpec = tween(durationMillis = 200),
                            label = "calBg"
                        )

                        val textColor = when {
                            isSelected -> MaterialTheme.colorScheme.onPrimary
                            isToday -> MaterialTheme.colorScheme.onPrimaryContainer
                            else -> MaterialTheme.colorScheme.onSurface
                        }

                        val debouncedDayClick = rememberDebouncedClick {
                            onDateSelected(date)
                        }

                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .padding(dims.spacing.tiny)
                                .clip(CircleShape)
                                .background(backgroundColor)
                                .clickableDebounced(onClick = debouncedDayClick),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                AppTypography.LabelSmall(
                                    text = dayNumber.toString(),
                                    color = textColor,
                                    fontWeight = if (isSelected || isToday) FontWeight.Bold else FontWeight.Normal
                                )
                                if (hasTasks) {
                                    Spacer(modifier = Modifier.height(dims.spacing.micro))
                                    Box(
                                        modifier = Modifier
                                            .size(dims.icons.micro)
                                            .clip(CircleShape)
                                            .background(
                                                if (isSelected) MaterialTheme.colorScheme.onPrimary
                                                else IndigoPrimary
                                            )
                                    )
                                }
                            }
                        }
                    } else {
                        Spacer(modifier = Modifier.aspectRatio(1f))
                    }
                }
            }
        }
    }
}
