package com.example.neighbourhub.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neighbourhub.models.Users
import com.example.neighbourhub.utils.BottomNavItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel:ViewModel() {
    private val _currentUser = MutableStateFlow(Users())
    val currentUser: StateFlow<Users>
        get() = _currentUser
    var userRole = MutableStateFlow(currentUser.value.userRole)

    val bottomNavList = listOf(
        BottomNavItem.Chatroom,
        BottomNavItem.Marketplace,
        BottomNavItem.Bulletin,
        BottomNavItem.Menu,
    )

    //VM Initialised
    init {
        viewModelScope.launch {
            _currentUser.value = Users.currentUserId?.let { Users.getCurrentUser(it) }!!
            userRole = MutableStateFlow(currentUser.value.userRole)
        }
    }
}