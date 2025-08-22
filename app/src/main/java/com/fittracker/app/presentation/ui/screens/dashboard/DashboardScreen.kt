package com.fittracker.app.presentation.ui.screens.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fittracker.app.R
import com.fittracker.app.presentation.ui.components.DashboardCard
import com.fittracker.app.presentation.ui.components.StepProgressCard
import com.fittracker.app.presentation.ui.components.WaterProgressCard
import com.fittracker.app.presentation.ui.components.CalorieProgressCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    var steps by remember { mutableStateOf(0) }
    var stepGoal by remember { mutableStateOf(10000) }
    var waterIntake by remember { mutableStateOf(0) }
    var waterGoal by remember { mutableStateOf(2000) }
    var calories by remember { mutableStateOf(0) }
    var calorieGoal by remember { mutableStateOf(2000) }

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
                steps = steps,
                goal = stepGoal,
                onStepsChanged = { steps = it },
                onGoalChanged = { stepGoal = it }
            )

            // صف من البطاقات
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // متتبع الماء
                WaterProgressCard(
                    waterIntake = waterIntake,
                    goal = waterGoal,
                    modifier = Modifier.weight(1f),
                    onWaterChanged = { waterIntake = it }
                )

                // متتبع السعرات
                CalorieProgressCard(
                    calories = calories,
                    goal = calorieGoal,
                    modifier = Modifier.weight(1f),
                    onCaloriesChanged = { calories = it }
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
                            value = "${(steps * 0.0008).toInt()}",
                            unit = "كم",
                            icon = null,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        DashboardCard(
                            title = "الوقت النشط",
                            value = "${(steps / 100).toInt()}",
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
                            value = "0",
                            unit = "جم",
                            icon = null,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        DashboardCard(
                            title = "الكربوهيدرات",
                            value = "0",
                            unit = "جم",
                            icon = null,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        DashboardCard(
                            title = "الدهون",
                            value = "0",
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