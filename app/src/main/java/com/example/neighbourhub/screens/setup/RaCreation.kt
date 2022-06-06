package com.example.neighbourhub.screens.setup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.neighbourhub.ui.widgets.*
import com.example.neighbourhub.viewmodel.RaCreationViewModel
import kotlinx.coroutines.launch

@Composable
fun RaCreation(vm: RaCreationViewModel = viewModel(), navBack: () -> Unit) {
    var dropdownExpansion by remember { mutableStateOf(false) }
    var invCode by remember { mutableStateOf("") }
    var showInvDialog by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var txtFieldSize by remember { mutableStateOf(Size.Zero) }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    // Textfield Error State
    var nameError by rememberSaveable { mutableStateOf(false) }
    var stateError by rememberSaveable { mutableStateOf(false) }
    var raAreaError by rememberSaveable { mutableStateOf(false) }
    var postcodeError by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = { CustomTopAppBar_Back("RA Registration", navBack) }) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(padding)
        ) {
            CustomOutlinedTextField(
                labelText = "Residential Association Name",
                textValue = vm.residentialName,
                onValueChangeFun = {
                    vm.residentialName = it
                    if (nameError) {
                        nameError = false
                    }
                },
                errorState = nameError,
                isSingleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 25.dp
                    )
            )
            Text(text = "Address", modifier = Modifier.padding(top = 25.dp))

            CustomOutlinedTextField(
                labelText = "Residential Area Name",
                textValue = vm.residentialArea,
                onValueChangeFun = {
                    vm.residentialArea = it
                    if (raAreaError) {
                        raAreaError = false
                    }
                },
                errorState = raAreaError,
                isSingleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 4.dp
                    )
            )
            CustomOutlinedTextField(
                labelText = "Postcode",
                textValue = vm.addPostcode,
                onValueChangeFun = {
                    vm.addPostcode = it
                    if (postcodeError) {
                        postcodeError = false
                    }
                },
                errorState = postcodeError,
                isSingleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 4.dp
                    ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
            Box {
                // Text Field for dropdown placeholder
                CustomOutlinedTextField(labelText = "State",
                    textValue = vm.addState,
                    onValueChangeFun = {
                        vm.addState = it
                        if (stateError) {
                            stateError = false
                        }
                    },
                    errorState = stateError,
                    trailingIcon = {
                        CustomIconButton(
                            onClickFun = { dropdownExpansion = !dropdownExpansion },
                            icon = if (dropdownExpansion) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            dropdownExpansion = !dropdownExpansion
                        }
                        .onGloballyPositioned { coordinates ->
                            // This value is used to assign to
                            // the DropDown the same width
                            txtFieldSize = coordinates.size.toSize()
                        }
                )
                //State Selection Dropdown
                DropdownMenu(
                    expanded = dropdownExpansion,
                    onDismissRequest = { dropdownExpansion = false },
                    modifier = Modifier
                        .requiredSizeIn(maxHeight = 300.dp)
                        .width(with(LocalDensity.current) { txtFieldSize.width.toDp() })
                ) {
                    vm.stateList.forEach { item ->
                        DropdownMenuItem(onClick = {
                            vm.addState = item
                            dropdownExpansion = false
                        }) { Text(text = item) }
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            // Button
            CustomButtonLoader(
                btnText = "Submit",
                showLoader = isLoading,
                onClickFun = {
                    scope.launch {

                        isLoading = true

                        // Input validation - Checking Ra  Name Content
                        if (vm.residentialName.isEmpty()) {
                            nameError = true
                            isLoading = false

                            // Display Snackbar
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "Please fill in all required fields"
                            )
                            return@launch
                        } else {
                            nameError = false
                        }

                        // Input validation - Checking Ra Area Name Content
                        if (vm.residentialArea.isEmpty()) {
                            raAreaError = true
                            isLoading = false

                            // Display Snackbar
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "Please fill in all required fields"
                            )
                            return@launch
                        } else {
                            raAreaError = false
                        }

                        // Input validation - Checking Ra Postcode Content
                        if (vm.addPostcode.isEmpty()) {
                            postcodeError = true
                            isLoading = false

                            // Display Snackbar
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "Please fill in all required fields"
                            )
                            return@launch
                        } else {
                            postcodeError = false
                        }

                        // Input validation - Checking Ra State Content
                        if (vm.addState.isEmpty()) {
                            stateError = true
                            isLoading = false

                            // Display Snackbar
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "Please fill in all required fields"
                            )
                            return@launch
                        } else {
                            stateError = false
                        }

                        invCode = vm.registerAssociation()
                        showInvDialog = true
                        isLoading = false
                    }
                },
                modifier = Modifier.padding(bottom = 50.dp)
            )

            if (showInvDialog) {
                CustomDialogClose(
                    alertTitle = "Invitation Code",
                    alertBody = "${vm.residentialName} has been successfully registered.\nThe invitation code is: $invCode",
                    onDismissFun = { showInvDialog = false; navBack() },
                    btnCloseClick = {
                        showInvDialog = false
                        navBack()
                    })
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Preview")
@Composable
fun RaCreationPreview() {
    NeighbourHubTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            RaCreation(RaCreationViewModel(), navBack = {})
        }
    }
}*/
