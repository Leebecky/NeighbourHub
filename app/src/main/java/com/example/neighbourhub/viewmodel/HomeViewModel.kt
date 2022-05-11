package com.example.neighbourhub.viewmodel

import androidx.lifecycle.ViewModel
import com.example.neighbourhub.utils.BottomNavItem

class HomeViewModel:ViewModel() {
    val bottomNavList = listOf(
        BottomNavItem.Chatroom,
        BottomNavItem.Marketplace,
        BottomNavItem.Bulletin,
        BottomNavItem.Menu,
    )
}