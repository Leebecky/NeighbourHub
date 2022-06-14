package com.example.neighbourhub.screens.committee

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.DisabledByDefault
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.neighbourhub.R
import com.example.neighbourhub.models.Users
import com.example.neighbourhub.ui.widgets.CustomButtonLoader
import com.example.neighbourhub.ui.widgets.CustomIconButton
import com.example.neighbourhub.ui.widgets.CustomOutlinedTextField
import com.example.neighbourhub.ui.widgets.CustomTopAppBar_Back
import com.example.neighbourhub.viewmodel.ManageRaViewModel
import kotlinx.coroutines.launch


@ExperimentalComposeUiApi
@Composable
fun ManageRa(
    vm: ManageRaViewModel = viewModel(),
    navBack: () -> Unit,
    navHome: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val scroll = rememberScrollState()
    var loaderState by remember { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState()
    val listState = rememberLazyListState()
    val committeeList by vm.committeeList.collectAsState()

    var stateDropdownExpansion by remember { mutableStateOf(false) }
    var stateTxtFieldSize by remember { mutableStateOf(Size.Zero) }
    var comDropdownExpansion by remember { mutableStateOf(false) }
    var comTxtFieldSize by remember { mutableStateOf(Size.Zero) }


    // Textfield Error State
    var nameError by rememberSaveable { mutableStateOf(false) }
    var stateError by rememberSaveable { mutableStateOf(false) }
    var raAreaError by rememberSaveable { mutableStateOf(false) }
    var postcodeError by rememberSaveable { mutableStateOf(false) }


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CustomTopAppBar_Back(title = stringResource(R.string.manage_ra_title), navBack = navBack)
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
                text = stringResource(R.string.ra_details),
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(top = 25.dp)
            )
            CustomOutlinedTextField( // RA Name
                labelText = stringResource(R.string.ra_name_field),
                textValue = vm.raName,
                onValueChangeFun = {
                    vm.raName = it
                    if (nameError) {
                        nameError = false
                    }
                },
                errorState = nameError,
                isSingleLine = true
            )

            CustomOutlinedTextField( // Invitation Code
                labelText = stringResource(R.string.inv_code_field),
                textValue = vm.invCode,
                onValueChangeFun = { }, isEnabled = false
            )

            // Resident Association Address
            Text(
                text = stringResource(R.string.address),
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(top = 25.dp)
            )

            CustomOutlinedTextField(
                // RA Area Name
                labelText = stringResource(R.string.residential_area_field),
                textValue = vm.residentialArea,
                onValueChangeFun = {
                    vm.residentialArea = it
                    if (raAreaError) {
                        raAreaError = false
                    }
                },
                errorState = raAreaError,
                isSingleLine = true
            )

            CustomOutlinedTextField(
                // Postcode
                labelText = stringResource(R.string.postcode_field),
                textValue = vm.postcode,
                onValueChangeFun = {
                    vm.postcode = it
                    if (postcodeError) {
                        postcodeError = false
                    }
                },
                errorState = postcodeError,
                isSingleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )

            Box {
                // Text Field for dropdown placeholder
                CustomOutlinedTextField(labelText = stringResource(R.string.state_field),
                    textValue = vm.state,
                    onValueChangeFun = {
                        vm.state = it
                        if (stateError) {
                            stateError = false
                        }
                    },
                    isReadOnly = true,
                    errorState = stateError,
                    trailingIcon = {
                        CustomIconButton(
                            onClickFun = { stateDropdownExpansion = !stateDropdownExpansion },
                            icon = if (stateDropdownExpansion) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown
                        )
                    },
                    modifier = Modifier
                        .clickable {
                            stateDropdownExpansion = !stateDropdownExpansion
                        }
                        .onGloballyPositioned { coordinates ->
                            // This value is used to assign to the DropDown the same width
                            stateTxtFieldSize = coordinates.size.toSize()
                        }
                )
                //State Selection Dropdown
                DropdownMenu(
                    expanded = stateDropdownExpansion,
                    onDismissRequest = { stateDropdownExpansion = false },
                    modifier = Modifier
                        .requiredSizeIn(maxHeight = 300.dp)
                        .width(with(LocalDensity.current) { stateTxtFieldSize.width.toDp() })
                ) {
                    vm.stateList.forEach { item ->
                        DropdownMenuItem(onClick = {
                            vm.state = item
                            stateDropdownExpansion = false
                        }) { Text(text = item) }
                    }
                }
            }

            // Resident Association Committee
            Text(
                text = stringResource(R.string.committee),
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(top = 25.dp)
            )

            Box {
                CustomOutlinedTextField(
                    labelText = stringResource(R.string.committee_list),
                    textValue = "",
                    onValueChangeFun = {},
                    isReadOnly = true,
                    trailingIcon = {
                        CustomIconButton(
                            onClickFun = { comDropdownExpansion = !comDropdownExpansion },
                            icon = Icons.Filled.AddBox
                        )
                    },
                    modifier = Modifier
                        .clickable(onClick = { comDropdownExpansion = !comDropdownExpansion })
                        .onGloballyPositioned { coordinates ->
                            // This value is used to assign to the DropDown the same width
                            comTxtFieldSize = coordinates.size.toSize()
                        })
                //Committee Selection Dropdown
                DropdownMenu(
                    expanded = comDropdownExpansion,
                    onDismissRequest = { comDropdownExpansion = false },
                    modifier = Modifier
                        .requiredSizeIn(maxHeight = 300.dp)
                        .width(with(LocalDensity.current) { comTxtFieldSize.width.toDp() })
                ) {
                    vm.residentList.forEach { item ->
                        DropdownMenuItem(onClick = {
                            vm.updateCommittee(item)
                            comDropdownExpansion = false
                        }) { Text(text = item.name) }
                    }
                }
            }
            LazyColumn(
                state = listState,
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.height(250.dp)
            ) {
                items(committeeList) { item ->
                    MemberCard(item) { vm.removeCommittee(item) }
                }
            }


            CustomButtonLoader(
                btnText = stringResource(R.string.save_btn),
                onClickFun = {
                    scope.launch {
                        loaderState = true

                        // Input validation - Checking Ra Name Content
                        if (vm.raName.isEmpty()) {
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

                        // Input validation - Checking Ra Area Name Content
                        if (vm.residentialArea.isEmpty()) {
                            raAreaError = true
                            loaderState = false

                            // Display Snackbar
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "Please fill in all required fields",
                                //   actionLabel = "Do something."
                            )
                            return@launch
                        } else {
                            raAreaError = false
                        }


                        // Input validation - Checking State Content
                        if (vm.state.isEmpty()) {
                            stateError = true
                            loaderState = false

                            // Display Snackbar
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "Please fill in all required fields",
                                //   actionLabel = "Do something."
                            )
                            return@launch
                        } else {
                            stateError = false
                        }

                        // Input validation - Checking Postcode Address Content
                        if (vm.postcode.isEmpty()) {
                            postcodeError = true
                            loaderState = false

                            // Display Snackbar
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "Please fill in all required fields",
                            )
                            return@launch
                        } else {
                            postcodeError = false
                        }

                        val status = vm.updateRa()
                        if (status) {
                            // Display Snackbar
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "Resident Association has been updated"
                            )

                            //Return to Home
//                            navHome()
                        } else {
                            // Display Snackbar
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "Unexpected error occurred. Please try again"
                            )
                        }
                        loaderState = false
                    }
                }, showLoader = loaderState,
                modifier = Modifier.padding(vertical = 15.dp)
            )
        }
    }
}

@Composable
fun MemberCard(item: Users, removeUser:()->Unit) {
    Card(
//        shape = AbsoluteRoundedCornerShape(topLeft = 20.dp, topRight = 20.dp),
        modifier = Modifier.fillMaxWidth(0.75f),
        border = BorderStroke(1.dp, Color.Gray)
    ) {

        Row(
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.padding(start = 15.dp)) {
                Text(text = item.name,   style = MaterialTheme.typography.subtitle1)
                Text(
                    text = "${item.address.houseNumber}, ${item.address.street}",
                    style = MaterialTheme.typography.subtitle2
                )
            }
            CustomIconButton(onClickFun = removeUser, icon = Icons.Filled.DisabledByDefault)
        }
    }
}