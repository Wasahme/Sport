package com.fittracker.app.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fittracker.app.presentation.ui.theme.NutritionPrimary
import com.fittracker.app.presentation.ui.theme.NutritionSecondary

@Composable
fun CalorieProgressCard(
    calories: Int,
    goal: Int,
    onCaloriesChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val progress = if (goal > 0) calories.toFloat() / goal.toFloat() else 0f
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
                text = "السعرات",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = calories.toString(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = NutritionPrimary
            )
            
            Text(
                text = "من ${goal} سعرة",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth(),
                color = if (progressPercentage >= 100) NutritionPrimary else NutritionSecondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { onCaloriesChanged(calories + 100) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NutritionPrimary
                    )
                ) {
                    Text("+100")
                }
                
                Spacer(modifier = Modifier.width(4.dp))
                
                Button(
                    onClick = { onCaloriesChanged(calories + 200) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NutritionSecondary
                    )
                ) {
                    Text("+200")
                }
            }
        }
    }
}