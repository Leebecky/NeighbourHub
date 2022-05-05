package com.example.neighbourhub.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginViewModel() : ViewModel() {
    // Variables
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    fun login() = viewModelScope.launch {
        Log.println(Log.INFO, "Test", email)
        Log.println(Log.INFO, "Test", password)

        try {
            Firebase.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.println(Log.INFO, "Test", "Login Successful")
                    } else {
                        Log.println(Log.INFO, "Test", "Login Failed")
                    }
                }

        } catch (ex: Exception) {
            Log.println(Log.ERROR, "Exception", ex.message.orEmpty())
        }
    }
}