package com.fittracker.app.presentation.ui.screens.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fittracker.app.R
import com.fittracker.app.presentation.ui.components.DashboardCard
import com.fittracker.app.presentation.ui.components.StepProgressCard
import com.fittracker.app.presentation.ui.components.WaterProgressCard
import com.fittracker.app.presentation.ui.components.CalorieProgressCard
import com.fittracker.app.presentation.viewmodel.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val stepGoal = 10000
    val waterGoal = 2000
    val calorieGoal = 2000

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.nav_dashboard),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // مرحباً بك
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "مرحباً بك في FitTracker",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "تطبيقك الشامل للياقة البدنية والصحة",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                }
            }

            // عداد الخطوات
            StepProgressCard(
                steps = uiState.totalSteps,
                goal = stepGoal,
                onStepsChanged = { /* سيتم تحديثه لاحقاً */ },
                onGoalChanged = { /* سيتم تحديثه لاحقاً */ }
            )

            // صف من البطاقات
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // متتبع الماء
                WaterProgressCard(
                    waterIntake = uiState.waterIntake,
                    goal = waterGoal,
                    modifier = Modifier.weight(1f),
                    onWaterChanged = { viewModel.updateWaterIntake(it) }
                )

                // متتبع السعرات
                CalorieProgressCard(
                    calories = uiState.foodCalories,
                    goal = calorieGoal,
                    modifier = Modifier.weight(1f),
                    onCaloriesChanged = { viewModel.updateFoodCalories(it) }
                )
            }

            // إحصائيات سريعة
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "إحصائيات اليوم",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        DashboardCard(
                            title = "المسافة",
                            value = "${uiState.totalDistance.toInt()}",
                            unit = "كم",
                            icon = null,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        DashboardCard(
                            title = "الوقت النشط",
                            value = "${uiState.activeMinutes}",
                            unit = "دقيقة",
                            icon = null,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // التمارين الأخيرة
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "التمارين الأخيرة",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "لا توجد تمارين حديثة",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }

            // التغذية اليومية
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "التغذية اليومية",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        DashboardCard(
                            title = "البروتين",
                            value = "${uiState.protein}",
                            unit = "جم",
                            icon = null,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        DashboardCard(
                            title = "الكربوهيدرات",
                            value = "${uiState.carbs}",
                            unit = "جم",
                            icon = null,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        DashboardCard(
                            title = "الدهون",
                            value = "${uiState.fat}",
                            unit = "جم",
                            icon = null,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}