package com.example.neighbourhub.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.neighbourhub.models.AddressLocation
import com.example.neighbourhub.models.ResidentAssociation
import com.example.neighbourhub.models.Users
import com.example.neighbourhub.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RaCreationViewModel : ViewModel() {
    // Variables
    var residentialName by mutableStateOf("")
    var residentialArea by mutableStateOf("")
    var addPostcode by mutableStateOf("")
    var addState by mutableStateOf("")

    private val _currentUser = MutableStateFlow(Users())
    val currentUser: StateFlow<Users>
        get() = _currentUser
    var userRole = MutableStateFlow(currentUser.value.userRole)

    val stateList: List<String> = listOf(
        "Johor",
        "Kedah",
        "Kelantan",
        "Malacca",
        "Negeri Sembilan",
        "Pahang",
        "Penang",
        "Perak",
        "Sabah",
        "Sarawak",
        "Selangor",
        "Terengganu",
        "Kuala Lumpur",
        "Putrajaya",
        "Labuan"
    )

    //VM Initialised
    init {
        viewModelScope.launch {
            _currentUser.value = Users.currentUserId?.let { Users.getCurrentUser(it) }!!
            userRole = MutableStateFlow(currentUser.value.userRole)
        }
    }

    fun registerAssociation(): String {

        val invCode = ResidentAssociation.generateInvCode()
        val ra = ResidentAssociation(
            raName = residentialName,
            invitationCode = invCode,
            address = AddressLocation(
                postcode = addPostcode,
                state = addState,
                residentialArea = residentialArea
            ),
            committeeMemberList = listOf(currentUser.value.id)
        )


        currentUser.value.userRole = Constants.CommitteeRole

        viewModelScope.launch {
            Users.updateUserProfile(currentUser.value)
            ResidentAssociation.updateResidentAssociation(ra)
        }

        return invCode

    }
}