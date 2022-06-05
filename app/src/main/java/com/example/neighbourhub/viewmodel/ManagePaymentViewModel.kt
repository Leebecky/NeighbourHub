package com.example.neighbourhub.viewmodel

import android.util.Log
import androidx.compose.runtime.internal.liveLiteral
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.neighbourhub.models.Payment
import com.example.neighbourhub.models.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ManagePaymentViewModel : ViewModel() {
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    fun refresh() {
        // Run tasks while the refresh animation plays
        viewModelScope.launch {

            _isRefreshing.emit(true)
            _payList.value = Payment.getPaymentList(ra)
            _isRefreshing.emit(false)
        }
    }

    private val _payList = MutableStateFlow<List<Payment>>(emptyList())
    val payList: StateFlow<List<Payment>>
        get() = _payList

    private val _currentUser = MutableStateFlow(Users())
    val currentUser: StateFlow<Users>
        get() = _currentUser
    private var ra = currentUser.value.residentsAssociationId

    init {
        viewModelScope.launch {
            _currentUser.value = Users.currentUserId?.let { Users.getCurrentUser(it) }!!
            ra = currentUser.value.residentsAssociationId
            _payList.value = Payment.getPaymentList(ra)
        }
    }


    suspend fun GetPaymentList(): List<Payment> {
        return Payment.getPaymentList(ra)
    }


}