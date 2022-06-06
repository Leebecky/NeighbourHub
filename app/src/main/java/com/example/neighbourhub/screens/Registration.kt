package com.example.neighbourhub.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.neighbourhub.ui.widgets.*
import com.example.neighbourhub.viewmodel.RegistrationViewModel
import kotlinx.coroutines.launch

// Registration Page
@Composable
fun Registration(
    navBack: () -> Unit,
    navHome: () -> Unit,
    vm: RegistrationViewModel = viewModel()
) {
    var showPassword by remember { mutableStateOf(false) }
    var showRegistrationError by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    // Textfield Error State
    var emailError by rememberSaveable { mutableStateOf(false) }
    var passwordError by rememberSaveable { mutableStateOf(false) }
    var passwordMsg = "Required Field!"

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CustomTopAppBar_Back("Register", navBack = navBack)
        }
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
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
            CustomOutlinedTextField( // Email
                labelText = "Email",
                textValue = vm.email,
                onValueChangeFun = {
                    vm.email = it
                    if (emailError) {
                        emailError = false
                    }
                },
                modifier = Modifier.padding(top = 8.dp),
                isSingleLine = true,
                errorState = emailError,
                errorMsg = "Required Field!"
            )
            CustomOutlinedTextField( // Password
                textValue = vm.password,
                modifier = Modifier.padding(top = 8.dp),
                labelText = "Password",
                onValueChangeFun = {
                    vm.password = it
                    if (passwordError) {
                        passwordError = false
                    }
                },
                isSingleLine = true,
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = { // Show/Hide Password icon
                    CustomIconButton(
                        onClickFun = { showPassword = !showPassword },
                        icon = if (showPassword) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        btnDescription = "Show password"
                    )
                },
                errorState = passwordError,
                errorMsg = passwordMsg
            )
            Spacer(Modifier.height(30.dp))
            CustomButtonLoader(//TODO: Validation
                btnText = "Register",
                showLoader = isLoading,
                onClickFun = {
                    scope.launch {
                        isLoading = true

                        // Input validation - Checking Email Content
                        if (vm.email.isEmpty()) {
                            emailError = true
                            isLoading = false

                            // Display Snackbar
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "Please fill in all required fields",
                                //   actionLabel = "Do something."
                            )
                            return@launch
                        } else {
                            emailError = false
                        }

                        // Input validation - Checking Password Content
                        if (vm.password.isEmpty()) {
                            passwordError = true
                            passwordMsg = "Required Field!"
                            isLoading = false

                            // Display Snackbar
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "Please fill in all required fields",
                                //   actionLabel = "Do something."
                            )
                            return@launch
                        } else if (vm.password.length < 8) {
                            passwordError = true
                            passwordMsg = "Password should be at least 8 characters long!"
                            isLoading = false

                            // Display Snackbar
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "Please check the Password field",
                                //   actionLabel = "Do something."
                            )
                            return@launch
                        } else {
                            passwordError = false
                        }

                        val data = vm.registerUser()

                        if (data != null) { // Navigate to Home page
                            navHome()
                        } else {
                            showRegistrationError = true
                            Log.println(Log.INFO, "Test", "Registration Failed")
                        }

                        isLoading = false
                    }
                },
                modifier = Modifier.padding(top = 24.dp)
            )

            if (showRegistrationError) { // Registration Dialog
                CustomDialogClose(
                    alertTitle = "Registration Failed",
                    alertBody = "Error registering user. Please try again",
                    onDismissFun = { showRegistrationError = false },
                    btnCloseClick = { showRegistrationError = false })
            }
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