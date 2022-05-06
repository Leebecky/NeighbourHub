package com.example.neighbourhub.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.neighbourhub.R
import com.example.neighbourhub.ui.widgets.CustomButton
import com.example.neighbourhub.ui.widgets.CustomIconButton
import com.example.neighbourhub.ui.widgets.CustomOutlinedTextField
import com.example.neighbourhub.viewmodel.RegistrationViewModel
import kotlinx.coroutines.launch

// Registration Page
@Composable
fun Registration(navBack: () -> Unit, navHome:()->Unit, vm: RegistrationViewModel = viewModel()) {
    var showPassword by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Register") },
                navigationIcon = {
                    CustomIconButton(
                        onClickFun = navBack,
                        icon = Icons.Filled.ArrowBack
                    )
                }
            )
        }
    ) {
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
                    CustomIconButton(
                        onClickFun = { showPassword = !showPassword },
                        icon = if (showPassword) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        btnDescription = "Show password"
                    )
                }
            )
            Spacer(Modifier.height(30.dp))
            CustomButton(
                btnText = "Register",
                onClickFun = {
                    scope.launch {
                        val data = vm.registerUser()
                        Log.println(Log.INFO, "Test", data.toString())
                        if (data != null) { // Navigate to Home page
                            navHome()
                        } else { //TODO: Display Error Message
                            Log.println(Log.INFO, "Test", "Login Failed")
                        }
                    }
                },
                modifier = Modifier.padding(top = 24.dp)
            )

        }
    }
}

////Testing Page for navigation demonstration
//@Composable
//fun Registration(navControl: () -> Unit, name: String) {
//    NeighbourHubTheme {
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colors.background
//        ) {
//            Column {
//                Text(text = "Hello $name!")
//                CustomButton(
//                    btnText = "Back", onClickFun =
//                    navControl
//                )
//                CustomButton(btnText = "Back", onClickFun = { FirebaseAuth.getInstance().signOut() }
//                )
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    NeighbourHubTheme {
////        Registration("Android")
//    }
//}