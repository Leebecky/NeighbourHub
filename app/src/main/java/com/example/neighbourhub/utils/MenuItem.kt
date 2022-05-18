package com.example.neighbourhub.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material.icons.filled.Logout
import androidx.compose.ui.graphics.vector.ImageVector

sealed class MenuItem(
    var title: String,
    var icon: ImageVector,
    var route: String
) {
    object LogoutItem : MenuItem("Logout", Icons.Filled.Logout, NavigationRoutes.LogoutRoute)
    object VisitorRegItem :
        MenuItem("Visitor Registration", Icons.Filled.HowToReg, NavigationRoutes.VisitorRegRoute)
}
