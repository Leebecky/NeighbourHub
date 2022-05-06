package com.example.neighbourhub.screens

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.neighbourhub.ui.widgets.CustomButton
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Home(navBack: () -> Unit) {
    Scaffold() {
        Text(text = "Test")
        CustomButton(btnText = "Back", onClickFun = {
            FirebaseAuth.getInstance().signOut()
            navBack()
        })
    }
}