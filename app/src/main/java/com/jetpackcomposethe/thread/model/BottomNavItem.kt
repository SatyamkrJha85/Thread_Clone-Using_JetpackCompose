package com.jetpackcomposethe.thread.model

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val title:String,
    val route:String,
    val icon:ImageVector
)