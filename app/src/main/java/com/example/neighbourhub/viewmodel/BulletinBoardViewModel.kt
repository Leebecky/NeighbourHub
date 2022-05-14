package com.example.neighbourhub.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neighbourhub.models.Bulletin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BulletinBoardViewModel : ViewModel() {

    private val _bulletinList = MutableStateFlow<List<Bulletin>>(emptyList())
    val bulletinList: StateFlow<List<Bulletin>>
        get() = _bulletinList

    init {
        viewModelScope.launch {
            _bulletinList.value = Bulletin.getBulletinList()
        }
    }

    suspend fun GetBulletinList(): List<Bulletin>? {
        return Bulletin.getBulletinList()
    }
}