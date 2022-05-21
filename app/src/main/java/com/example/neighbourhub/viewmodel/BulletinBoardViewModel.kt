package com.example.neighbourhub.viewmodel

import android.util.Log
import androidx.compose.runtime.internal.liveLiteral
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.neighbourhub.models.Bulletin
import com.example.neighbourhub.models.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BulletinBoardViewModel : ViewModel() {
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    fun refresh() {
        // Run tasks while the refresh animation plays
        viewModelScope.launch {

            _isRefreshing.emit(true)
            _bulletinList.value = Bulletin.getBulletinList(ra)
            _isRefreshing.emit(false)
        }
    }

    private val _bulletinList = MutableStateFlow<List<Bulletin>>(emptyList())
    val bulletinList: StateFlow<List<Bulletin>>
        get() = _bulletinList

    private val _currentUser = MutableStateFlow(Users())
    val currentUser: StateFlow<Users>
        get() = _currentUser
    private var ra = currentUser.value.residentsAssociationId

    init {
        viewModelScope.launch {
            _currentUser.value = Users.currentUserId?.let { Users.getCurrentUser(it) }!!
            ra = currentUser.value.residentsAssociationId
            _bulletinList.value = Bulletin.getBulletinList(ra)
        }
    }


    suspend fun GetBulletinList(): List<Bulletin> {
        return Bulletin.getBulletinList(ra)
    }


}