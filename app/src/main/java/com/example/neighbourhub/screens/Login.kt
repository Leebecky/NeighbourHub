package com.example.neighbourhub.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.neighbourhub.R
import com.example.neighbourhub.models.Users
import com.example.neighbourhub.ui.widgets.CustomButton
import com.example.neighbourhub.ui.widgets.CustomButtonLoader
import com.example.neighbourhub.ui.widgets.CustomDialogClose
import com.example.neighbourhub.ui.widgets.CustomOutlinedTextField
import com.example.neighbourhub.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun Login(vm: LoginViewModel = viewModel(), navRegistration: () -> Unit, navHome: () -> Unit) {
    var showPassword by remember { mutableStateOf(false) }
    var showLoginError by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.logo_512),
            contentDescription = stringResource(R.string.logo_desc),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(180.dp) // resize the image
                .clip(RoundedCornerShape(10.dp))
        )
        Spacer(Modifier.height(50.dp))
        CustomOutlinedTextField(
            labelText = "Email",
            textValue = vm.email,
            onValueChangeFun = { vm.email = it },
            modifier = Modifier.padding(top = 8.dp),
            isSingleLine = true,
        )
        OutlinedTextField(
            value = vm.password,
            modifier = Modifier.padding(top = 8.dp),
            label = { Text("Password") },
            onValueChange = { vm.password = it },
            singleLine = true,
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = { // Show/Hide Password icon
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        imageVector = if (showPassword) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        contentDescription = "Show password"
                    )
                }
            }
        )
        Spacer(Modifier.height(30.dp))
        CustomButtonLoader(
            btnText = "Login",
            showLoader = isLoading,
            onClickFun = {
                scope.launch {
                    isLoading = true
                    val data = vm.logInWithEmail()

                    if (data != null) { // Navigate to Home page
                        Users.updateLoginUser()
                        navHome()
                    } else {
                        showLoginError = true
                    }
                    isLoading = false
                }
            },
            modifier = Modifier.padding(top = 24.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "No account? Click here to register!",
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .padding(bottom = 24.dp)
                .clickable {
                    navRegistration()
                })

        if (showLoginError) { //Login Error Dialog
            CustomDialogClose(
                alertTitle = "Authentication Error",
                alertBody = "Failed to login. Please try again",
                onDismissFun = { showLoginError = false },
                btnCloseClick = { showLoginError = false })
        }
    }
}
//}
