package com.example.neighbourhub.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.neighbourhub.R
import com.example.neighbourhub.ui.widgets.CustomButtonLoader
import com.example.neighbourhub.ui.widgets.CustomOutlinedTextField
import com.example.neighbourhub.ui.widgets.CustomTopAppBar_Back
import com.example.neighbourhub.viewmodel.UserProfileViewModel
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
fun UserProfile(
    vm: UserProfileViewModel = viewModel(),
    navBack: () -> Unit,
    navHome: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val scroll = rememberScrollState()
    var loaderState by remember { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState()

    // Textfield Error State
    var nameError by rememberSaveable { mutableStateOf(false) }
    var ageError by rememberSaveable { mutableStateOf(false) }
    var contactError by rememberSaveable { mutableStateOf(false) }
    var houseError by rememberSaveable { mutableStateOf(false) }
    var streetError by rememberSaveable { mutableStateOf(false) }
    var ageMsg = "Required Field!"

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CustomTopAppBar_Back(title = stringResource(R.string.user_profile_title), navBack = navBack)
        }) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(scroll)
        ) {
            Text(
                text = stringResource(R.string.user_details),
                modifier = Modifier
                    .padding(top = 25.dp)
            )
            CustomOutlinedTextField( // Name
                labelText = stringResource(R.string.user_name_full_field),
                textValue = vm.name,
                onValueChangeFun = {
                    vm.name = it
                    if (nameError) {
                        nameError = false
                    }
                },
                errorState = nameError,
                isSingleLine = true
            )

            CustomOutlinedTextField( // Age
                labelText = stringResource(R.string.age_field),
                textValue = vm.age,
                onValueChangeFun = {
                    vm.age = it
                    if (ageError) {
                        ageError = false
                    }
                },
                errorState = ageError,
                errorMsg = ageMsg,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isSingleLine = true
            )

            CustomOutlinedTextField( // Contact Number
                labelText = stringResource(R.string.contact_field),
                textValue = vm.contactNumber,
                onValueChangeFun = {
                    vm.contactNumber = it
                    if (contactError) {
                        contactError = false
                    }
                },
                errorState = contactError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isSingleLine = true
            )

            // Address
            Text(
                text = stringResource(R.string.address),
                modifier = Modifier
                    .padding(top = 25.dp)
            )

            CustomOutlinedTextField(
                // House Number
                labelText = stringResource(R.string.house_number_field),
                textValue = vm.houseNo,
                onValueChangeFun = {
                    vm.houseNo = it
                    if (houseError) {
                        houseError = false
                    }
                },
                errorState = houseError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isSingleLine = true
            )

            CustomOutlinedTextField(
                // Street Name
                labelText = stringResource(R.string.street_field),
                textValue = vm.street,
                onValueChangeFun = {
                    vm.street = it
                    if (streetError) {
                        streetError = false
                    }
                },
                errorState = streetError,
                isSingleLine = true
            )

            CustomOutlinedTextField( // Residential Area
                labelText = stringResource(R.string.residential_area_field),
                textValue = vm.residentialArea,
                onValueChangeFun = { }, isEnabled = false
            )

            CustomOutlinedTextField( // Postcode
                labelText = stringResource(R.string.postcode_field),
                textValue = vm.postcode,
                onValueChangeFun = { }, isEnabled = false
            )

            CustomOutlinedTextField( // State
                labelText = stringResource(R.string.state_field),
                textValue = vm.state,
                onValueChangeFun = { }, isEnabled = false
            )

            CustomButtonLoader(
                btnText = stringResource(R.string.save_btn),
                onClickFun = {
                    scope.launch {
                        loaderState = true

                        // Input validation - Checking Name Content
                        if (vm.name.isEmpty()) {
                            nameError = true
                            loaderState = false

                            // Display Snackbar
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "Please fill in all required fields",
                                //   actionLabel = "Do something."
                            )
                            return@launch
                        } else {
                            nameError = false
                        }

                        // Input validation - Checking Age Content
                        if (vm.age.isEmpty()) {
                            ageError = true
                            ageMsg = "Required Field!"
                            loaderState = false

                            // Display Snackbar
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "Please fill in all required fields",
                                //   actionLabel = "Do something."
                            )
                            return@launch
                        } else if (vm.age == 0.toString()) {
                            ageError = true
                            ageMsg = "Age cannot be 0"
                            loaderState = false

                            // Display Snackbar
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "Please check the age field",
                                //   actionLabel = "Do something."
                            )
                            return@launch
                        } else {
                            ageError = false
                        }

                        // Input validation - Checking Contact Number Content
                        if (vm.contactNumber.isEmpty()) {
                            contactError = true
                            loaderState = false

                            // Display Snackbar
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "Please fill in all required fields",
                                //   actionLabel = "Do something."
                            )
                            return@launch
                        } else {
                            contactError = false
                        }

                        // Input validation - Checking House Number Content
                        if (vm.houseNo.isEmpty()) {
                            houseError = true
                            loaderState = false

                            // Display Snackbar
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "Please fill in all required fields",
                                //   actionLabel = "Do something."
                            )
                            return@launch
                        } else {
                            houseError = false
                        }

                        // Input validation - Checking Street Address Content
                        if (vm.street.isEmpty()) {
                            streetError = true
                            loaderState = false

                            // Display Snackbar
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "Please fill in all required fields",
                                //   actionLabel = "Do something."
                            )
                            return@launch
                        } else {
                            streetError = false
                        }

                        val status = vm.updateProfile()
                        if (status) {
                            // Display Snackbar
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "Profile has been saved"
                            )

                            //Return to Home
                            navHome()
                        } else {
                            // Display Snackbar
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "Unexpected error occurred. Please try again"
                            )
                        }
                        loaderState = false
                    }
                }, showLoader = loaderState,
                modifier = Modifier.padding(vertical = 5.dp)
            )
        }
    }
}

/*
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Preview")
@Composable
fun UserProfilePreview() {
    NeighbourHubTheme {
        Surface {
            UserProfile(UserProfileViewModel(), {})
        }
    }
}
*/
