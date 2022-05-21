package com.example.neighbourhub.viewmodel

import androidx.lifecycle.ViewModel
import com.example.neighbourhub.utils.MenuItem


class MenuViewModel : ViewModel() {


    val menuItemList = listOf(
        MenuItem.UserProfile,
        MenuItem.VisitorRegItem,
        MenuItem.Phonebook,
        MenuItem.VisitorLog_Com,
        MenuItem.LogoutItem
    )


}