package com.example.neighbourhub.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class MenuItem(
    var title: String,
    var icon: ImageVector,
    var route: String,
    var accessLevel: String
) {
    object LogoutItem : MenuItem(
        "Logout",
        Icons.Filled.Logout,
        NavigationRoutes.LogoutRoute,
        Constants.ResidentRole
    )

    object VisitorRegItem :
        MenuItem(
            "Visitor Registration",
            Icons.Filled.HowToReg,
            NavigationRoutes.VisitorRegRoute,
            Constants.ResidentRole
        )

    object Phonebook : MenuItem(
        "Phonebook",
        Icons.Filled.ContactPage,
        NavigationRoutes.Phonebook,
        Constants.ResidentRole
    )

    object UserProfile :
        MenuItem(
            "User Profile",
            Icons.Filled.AccountCircle,
            NavigationRoutes.UserProfile,
            Constants.ResidentRole
        )

    // Committee Menu Options
    object VisitorLog_Com : MenuItem(
        "Visitor Log",
        Icons.Filled.Class,
        NavigationRoutes.VisitorLog,
        Constants.CommitteeRole
    )

    object ManagePayment_Com : MenuItem(
        "Manage Payment",
        Icons.Filled.RequestQuote,
        NavigationRoutes.ManagePayment,
        Constants.CommitteeRole
    )
}
