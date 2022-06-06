package com.example.neighbourhub.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neighbourhub.models.ResidentAssociation
import com.example.neighbourhub.models.Users
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PhonebookViewModel : ViewModel() {
    private val _currentUser = MutableStateFlow(Users())
    private val currentUser: StateFlow<Users>
        get() = _currentUser

    private val _contactList = MutableStateFlow<List<Users>>(emptyList())
    private val contactList: StateFlow<List<Users>>
        get() = _contactList

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    private val _groupedContacts = MutableStateFlow<Map<Char, List<Users>>>(emptyMap())
    val groupedContacts: StateFlow<Map<Char, List<Users>>>
        get() = _groupedContacts

    //VM Initialised
    init {
        viewModelScope.launch {
            _currentUser.value = Users.currentUserId?.let { Users.getCurrentUser(it) }!!
            _contactList.value =
                ResidentAssociation.retrieveResidentsList(currentUser.value.residentsAssociationId)
            _groupedContacts.value = contactList.value.sortedBy { it.name }.groupBy { it.name[0] }
        }
    }

    fun onRefresh() {
        viewModelScope.launch {
            _isRefreshing.emit(true)
            _contactList.value =
                ResidentAssociation.retrieveResidentsList(currentUser.value.residentsAssociationId)
            contactList.value.sortedBy { it.name }
            _groupedContacts.value = contactList.value.sortedBy { it.name }.groupBy { it.name[0] }
            _isRefreshing.emit(false)
        }
    }
}