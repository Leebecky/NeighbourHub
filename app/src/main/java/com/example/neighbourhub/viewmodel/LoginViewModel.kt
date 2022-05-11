package com.example.neighbourhub.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neighbourhub.MainActivity
import com.example.neighbourhub.models.Users
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class LoginViewModel() : ViewModel() {
    // Variables
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    suspend fun logInWithEmail(): AuthResult? {
        return try {
            val data = Firebase.auth
                .signInWithEmailAndPassword(email, password)
                .await()
            data
        } catch (e: Exception) {
            null
        }
    }

//    suspend fun updateCurrentUser(userId:String){
//        val currentUser: Users? = Users.getCurrentUser(userId)
//        MainActivity().myUser = currentUser
//    }

}