package com.fittracker.app.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fittracker.app.presentation.ui.theme.WaterPrimary
import com.fittracker.app.presentation.ui.theme.WaterSecondary

@Composable
fun WaterProgressCard(
    waterIntake: Int,
    goal: Int,
    onWaterChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val progress = if (goal > 0) waterIntake.toFloat() / goal.toFloat() else 0f
    val progressPercentage = (progress * 100).toInt()
    
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "الماء",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "${waterIntake}مل",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = WaterPrimary
            )
            
            Text(
                text = "من ${goal}مل",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth(),
                color = if (progressPercentage >= 100) WaterPrimary else WaterSecondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { onWaterChanged(waterIntake + 250) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WaterPrimary
                    )
                ) {
                    Text("+250مل")
                }
                
                Spacer(modifier = Modifier.width(4.dp))
                
                Button(
                    onClick = { onWaterChanged(waterIntake + 500) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WaterSecondary
                    )
                ) {
                    Text("+500مل")
                }
            }
        }
    }
}