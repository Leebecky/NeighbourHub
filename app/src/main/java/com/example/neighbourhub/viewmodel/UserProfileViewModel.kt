package com.example.neighbourhub.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.neighbourhub.MainActivity
import com.example.neighbourhub.models.Users
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class UserProfileViewModel() : ViewModel() {

    var name by mutableStateOf("")
    var contactNumber by mutableStateOf("")
    var age by mutableStateOf("")
    var houseNo by mutableStateOf("")
    var street by mutableStateOf("")

    fun init(currentUser: Users?) {
        if (currentUser != null) {
            name = currentUser.name
            contactNumber = currentUser.contactNumber
            age = currentUser.age.toString()
            houseNo = currentUser.address.houseNumber
            street = currentUser.address.street
        }
    }

    suspend fun updateProfile(currentUser: Users?): Boolean {
        if (currentUser != null) {
            currentUser.name = name
            currentUser.age = age.toInt()
            currentUser.contactNumber = contactNumber
            currentUser.address.houseNumber = houseNo
            currentUser.address.street = street

            return Users.updateUserProfile(currentUser)

        } else {
            return false
        }
    }
}