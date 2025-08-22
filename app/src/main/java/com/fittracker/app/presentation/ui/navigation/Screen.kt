package com.fittracker.app.presentation.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.fittracker.app.R

sealed class Screen(
    val route: String,
    @StringRes val titleResId: Int,
    @DrawableRes val iconResId: Int
) {
    object Dashboard : Screen(
        route = "dashboard",
        titleResId = R.string.nav_dashboard,
        iconResId = R.drawable.ic_dashboard
    )
    
    object Steps : Screen(
        route = "steps",
        titleResId = R.string.nav_steps,
        iconResId = R.drawable.ic_steps
    )
    
    object Exercises : Screen(
        route = "exercises",
        titleResId = R.string.nav_exercises,
        iconResId = R.drawable.ic_exercises
    )
    
    object Nutrition : Screen(
        route = "nutrition",
        titleResId = R.string.nav_nutrition,
        iconResId = R.drawable.ic_nutrition
    )
    
    object Profile : Screen(
        route = "profile",
        titleResId = R.string.nav_profile,
        iconResId = R.drawable.ic_profile
    )
}