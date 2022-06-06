package com.example.neighbourhub.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeveloperBoard
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(var title:String, var icon:ImageVector, var route:String) {
    object Bulletin: BottomNavItem("Bulletins", Icons.Filled.DeveloperBoard, NavigationRoutes.Bulletin)
//    object Chatroom: BottomNavItem("Chatroom", Icons.Filled.Forum, NavigationRoutes.Chatroom)
    object VisitorRegistration: BottomNavItem("Register Visitor", Icons.Filled.HowToReg, NavigationRoutes.VisitorRegRoute)
    object Menu: BottomNavItem("Menu", Icons.Filled.Menu, NavigationRoutes.Menu)
}
