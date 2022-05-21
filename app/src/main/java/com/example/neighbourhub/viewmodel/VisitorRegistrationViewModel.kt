package com.example.neighbourhub.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neighbourhub.models.Users
import com.example.neighbourhub.models.Visitor
import com.example.neighbourhub.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VisitorRegistrationViewModel : ViewModel() {
    private val _currentUser = MutableStateFlow(Users())
    private val currentUser: StateFlow<Users>
        get() = _currentUser

    // Visitor Details
    var name by mutableStateOf("")
    var ic by mutableStateOf("")
    var carNumber by mutableStateOf("")
    var expectedEntryTime = mutableStateOf("")
    var visitDate = mutableStateOf("")


    //VM Initialised
    init {
        viewModelScope.launch {
            _currentUser.value = Users.currentUserId?.let { Users.getCurrentUser(it) }!!
        }
    }

    // Visitor Registration
    suspend fun visitorRegistration(): Boolean {
        val data = Visitor(
            name = name,
            icNumber = ic,
            carNumber = carNumber,
            expectedEntryTime = expectedEntryTime.value,
            visitDate = visitDate.value,
            status = Constants.VisitorRegistered,
            addressVisited = currentUser.value.address,
            createdBy = currentUser.value.id,
            raId = currentUser.value.residentsAssociationId,
        )

        return Visitor.updateVisitor(data)
    }

    fun generateQr(){
        //TODO: QR Code generation
        //Reference:
    }
}