package com.example.neighbourhub.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neighbourhub.models.ResidentAssociation
import com.example.neighbourhub.models.Users
import com.example.neighbourhub.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ManageRaViewModel : ViewModel() {

    private val _currentUser = MutableStateFlow(Users())
    private val currentUser: StateFlow<Users>
        get() = _currentUser

    private val _raData = MutableStateFlow(ResidentAssociation())
    private val raData: StateFlow<ResidentAssociation>
        get() = _raData

    var raId: String = ""
    var raName by mutableStateOf("")
    var invCode by mutableStateOf("")
    var residentialArea by mutableStateOf("")
    var state by mutableStateOf("")
    var postcode by mutableStateOf("")

    var comList: List<String> = emptyList()
    var residentList: MutableList<Users> = emptyList<Users>().toMutableList()
    private val _committeeList = MutableStateFlow<List<Users>>(emptyList())
    val committeeList: StateFlow<List<Users>>
        get() = _committeeList

    val stateList: List<String> = Constants.StateList

    //VM Initialised
    init {
        viewModelScope.launch {
            if (!Users.currentUserId.isNullOrEmpty()) {
                _currentUser.value = Users.currentUserId?.let { Users.getCurrentUser(it) }!!

                raId = currentUser.value.residentsAssociationId

                _raData.value = ResidentAssociation.getRaRecord(raId)!!

                raName = raData.value.raName
                invCode = raData.value.invitationCode
                residentialArea = raData.value.address.residentialArea
                state = raData.value.address.state
                postcode = raData.value.address.postcode
                comList = raData.value.committeeMemberList

                residentList.addAll(ResidentAssociation.retrieveResidentsList(raId))
                residentList.removeAll { r -> r.userRole == Constants.CommitteeRole }

                if (comList.isNotEmpty()) { // Retrieving data for Committee Members
                    comList.forEach { memberId ->
                        val user: Users? = Users.getCurrentUser(memberId)

                        if (user != null) {
                            _committeeList.value += user
                        }

                    }
                }
            }
        }
    }

    fun updateCommittee(data: Users) {
        residentList.remove(data)
        data.userRole = Constants.CommitteeRole
        _committeeList.value += data
    }

    fun removeCommittee(data: Users) {
        _committeeList.value -= data
        data.userRole = Constants.ResidentRole
        residentList.add(data)
    }

    suspend fun updateRa(): Boolean {
        val ra = ResidentAssociation.getRaRecord(raId)
        val committeeMemberList: MutableList<String> = emptyList<String>().toMutableList()
        return if (ra != null) {
            ra.address.residentialArea = residentialArea
            ra.address.state = state
            ra.address.postcode = postcode

            committeeList.value.forEach { user ->
                Users.updateUserProfile(user)
                committeeMemberList.add(user.id)
            }

            residentList.forEach { user ->
                Users.updateUserProfile(user)
            }

            ra.committeeMemberList = committeeMemberList

            ResidentAssociation.updateResidentAssociation(ra)
        } else {
            false
        }
    }
}