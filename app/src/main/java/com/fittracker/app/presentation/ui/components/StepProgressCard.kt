package com.fittracker.app.presentation.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fittracker.app.presentation.ui.theme.StepsPrimary
import com.fittracker.app.presentation.ui.theme.StepsSecondary

@Composable
fun StepProgressCard(
    steps: Int,
    goal: Int,
    onStepsChanged: (Int) -> Unit,
    onGoalChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val progress = if (goal > 0) steps.toFloat() / goal.toFloat() else 0f
    val progressPercentage = (progress * 100).toInt()
    
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "خطوات اليوم",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Box(
                modifier = Modifier.size(120.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                ) {
                    // الخلفية
                    drawArc(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        startAngle = 0f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
                    )
                    
                    // شريط التقدم
                    drawArc(
                        color = if (progressPercentage >= 100) StepsPrimary else StepsSecondary,
                        startAngle = -90f,
                        sweepAngle = progress * 360f,
                        useCenter = false,
                        style = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
                    )
                }
                
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = steps.toString(),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "$progressPercentage%",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "الهدف",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Text(
                        text = goal.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "المتبقي",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Text(
                        text = maxOf(0, goal - steps).toString(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (goal - steps > 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth(),
                color = if (progressPercentage >= 100) StepsPrimary else StepsSecondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    }
}