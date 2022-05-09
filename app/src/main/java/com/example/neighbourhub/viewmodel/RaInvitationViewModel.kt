package com.example.neighbourhub.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RaInvitationViewModel() : ViewModel() {
    var invCode by mutableStateOf("")

}