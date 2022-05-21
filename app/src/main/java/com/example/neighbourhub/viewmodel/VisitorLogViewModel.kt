package com.example.neighbourhub.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neighbourhub.models.Bulletin
import com.example.neighbourhub.models.ResidentAssociation
import com.example.neighbourhub.models.Users
import com.example.neighbourhub.models.Visitor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VisitorLogViewModel : ViewModel() {

    private val _currentUser = MutableStateFlow(Users())
    val currentUser: StateFlow<Users>
        get() = _currentUser

    private val _visitorLog = MutableStateFlow<List<Visitor>>(emptyList())
    val visitorLog: StateFlow<List<Visitor>>
        get() = _visitorLog

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    //VM Initialised
    init {
        viewModelScope.launch {
            _currentUser.value = Users.currentUserId?.let { Users.getCurrentUser(it) }!!
            _visitorLog.value = Visitor.getVisitorList(currentUser.value.residentsAssociationId)
            _visitorLog.value = _visitorLog.value.sortedBy { it.visitDate }
        }
    }

    fun onRefresh() {
        viewModelScope.launch {
            _isRefreshing.emit(true)
            _visitorLog.value = Visitor.getVisitorList(currentUser.value.residentsAssociationId)
            _visitorLog.value = _visitorLog.value.sortedBy { it.visitDate }
            _isRefreshing.emit(false)
        }
    }

}