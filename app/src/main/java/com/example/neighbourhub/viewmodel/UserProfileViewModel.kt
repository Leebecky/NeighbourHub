package com.example.neighbourhub.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.neighbourhub.MainActivity
import com.example.neighbourhub.models.ResidentAssociation
import com.example.neighbourhub.models.Users
import com.example.neighbourhub.utils.Constants
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserProfileViewModel() : ViewModel() {

    private val _currentUser = MutableStateFlow(Users())
    private val currentUser: StateFlow<Users>
        get() = _currentUser

    var name by mutableStateOf("")
    var contactNumber by mutableStateOf("")
    var age by mutableStateOf("")
    var houseNo by mutableStateOf("")
    var street by mutableStateOf("")

    // Static values retrieved from db
    var residentialArea: String = ""
    var state: String = ""
    var postcode: String = ""

    //VM Initialised
    init {
        viewModelScope.launch {
            if (!Users.currentUserId.isNullOrEmpty()) {
                _currentUser.value = Users.currentUserId?.let { Users.getCurrentUser(it) }!!

                name = currentUser.value.name
                contactNumber = currentUser.value.contactNumber
                age = currentUser.value.age.toString()
                houseNo = currentUser.value.address.houseNumber
                street = currentUser.value.address.street

                residentialArea = currentUser.value.address.residentialArea
                state = currentUser.value.address.state
                postcode = currentUser.value.address.postcode
            }
        }
    }


//    fun init(currentUser: Users?) {
//        if (currentUser != null) {
//            name = currentUser.name
//            contactNumber = currentUser.contactNumber
//            age = currentUser.age.toString()
//            houseNo = currentUser.address.houseNumber
//            street = currentUser.address.street
//
//            residentialArea = currentUser.address.residentialArea
//            state = currentUser.address.state
//            postcode = currentUser.address.postcode
//
//        }
//    }


    suspend fun updateProfile(): Boolean {
        val currentUser = Users.currentUserId?.let { Users.getCurrentUser(it) }

        return if (currentUser != null) {
            currentUser.name = name
            currentUser.age = age.toInt()
            currentUser.contactNumber = contactNumber
            currentUser.address.houseNumber = houseNo
            currentUser.address.street = street
            currentUser.status = Constants.ActiveStatus
            Users.updateUserProfile(currentUser)
        } else {
            false
        }
    }
}