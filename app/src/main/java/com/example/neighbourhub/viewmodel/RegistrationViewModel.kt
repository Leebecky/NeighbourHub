package com.example.neighbourhub.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neighbourhub.models.Users
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class RegistrationViewModel() : ViewModel() {
    // Variables
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    suspend fun createUser(): AuthResult? {
        return try {
            val data = Firebase.auth
                .createUserWithEmailAndPassword(email, password)
                .await()
            data
        } catch (e: Exception) {
            null
        }
    }

    suspend fun registerUser(): AuthResult? {
        val auth = createUser()
        if (auth != null) {
            auth.user?.let { Users.registerUser(it.uid, it.email.orEmpty()) }
        }
        return auth
    }

}