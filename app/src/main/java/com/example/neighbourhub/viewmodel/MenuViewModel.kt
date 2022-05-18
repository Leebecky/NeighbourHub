package com.example.neighbourhub.viewmodel

import androidx.lifecycle.ViewModel
import com.example.neighbourhub.utils.BottomNavItem
import com.example.neighbourhub.utils.MenuItem

class MenuViewModel : ViewModel() {
    val menuItemList = listOf(
        MenuItem.VisitorRegItem,
        MenuItem.LogoutItem
    )
}