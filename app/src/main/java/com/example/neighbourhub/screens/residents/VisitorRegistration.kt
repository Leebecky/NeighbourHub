package com.example.neighbourhub.screens.residents

import android.graphics.Bitmap
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.neighbourhub.R
import com.example.neighbourhub.models.Visitor
import com.example.neighbourhub.ui.widgets.*
import com.example.neighbourhub.utils.DateTimeManager
import com.example.neighbourhub.viewmodel.VisitorRegistrationViewModel
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
fun VisitorRegistration(
//    navBack: () -> Unit,
    padding: PaddingValues,
    navHome: () -> Unit,
    vm: VisitorRegistrationViewModel = viewModel()
) {

    // Composable State variables
    var showLoader by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var displayVisitor by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
//    val scaffoldState = rememberScaffoldState()

    // Textfield Error State
    var nameError by rememberSaveable { mutableStateOf(false) }
    var icError by rememberSaveable { mutableStateOf(false) }
    var carError by rememberSaveable { mutableStateOf(false) }
    var dateError by rememberSaveable { mutableStateOf(false) }
    var timeError by rememberSaveable { mutableStateOf(false) }

    var bitmap by rememberSaveable { mutableStateOf<Bitmap?>(null) }
    var data:Visitor? by rememberSaveable { mutableStateOf(null) }

    //Content
//    Scaffold(
//        scaffoldState = scaffoldState,
////        topBar = {
////            CustomTopAppBar_Back(title = "Visitor Registration", navBack = navBack)
////        }
//    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            CustomOutlinedTextField( //Visitor Name
                labelText = stringResource(R.string.name_field),
                textValue = vm.name,
                onValueChangeFun = {
                    vm.name = it
                    if (nameError) {
                        nameError = false
                    }
                },
                errorState = nameError,
            )

            CustomOutlinedTextField( //Visitor IC Number
                labelText = stringResource(R.string.ic_number_field),
                textValue = vm.ic,
                isSingleLine = true,
                onValueChangeFun = {
                    vm.ic = it
                    if (icError) {
                        icError = false
                    }
                },
                errorState = icError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )

            CustomOutlinedTextField( //Visitor Car Number
                labelText = stringResource(R.string.car_number_field),
                textValue = vm.carNumber,
                isSingleLine = true,
                onValueChangeFun = {
                    vm.carNumber = it
                    if (carError) {
                        carError = false
                    }
                },
                errorState = carError,
                errorMsg = "Required Field!",
//                modifier = Modifier.padding(top = 4.dp)
            )


            CustomOutlinedTextField(
                //Visitor Visitation Date
                labelText = stringResource(R.string.visit_date),
                textValue = vm.visitDate.value,
                onValueChangeFun = {
                    vm.visitDate.value = it
                    if (dateError) {
                        dateError = false
                    }
                },
                isEnabled = false,
                isReadOnly = true,
                trailingIcon = {
                    CustomDatePicker(
                        LocalContext.current,
                        DateTimeManager().year,
                        DateTimeManager().month,
                        DateTimeManager().day,
                        vm.visitDate
                    )
                },
                errorState = dateError
            )

            CustomOutlinedTextField( //Visitor Expected Entry Time
                labelText = stringResource(R.string.entry_time_field),
                textValue = vm.expectedEntryTime.value,
                onValueChangeFun = {
                    vm.expectedEntryTime.value = it
                    if (timeError) {
                        timeError = false
                    }
                },
                isEnabled = false,
                isReadOnly = true,
                trailingIcon = {
                    CustomTimePicker(LocalContext.current, 8, 30, vm.expectedEntryTime)
                },
                errorState = timeError,
            )

            Spacer(modifier = Modifier.weight(1f))

            CustomButtonLoader(
                btnText = stringResource(R.string.register_visitor_btn),
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .width(300.dp), onClickFun = {
                    scope.launch {
                        showLoader = true

                        // Input validation
                        if (vm.name.isEmpty()) { // Name
                            nameError = true
                            showLoader = false

                            return@launch
                        } else {
                            nameError = false
                        }

                        if (vm.ic.isEmpty()) { // ic
                            icError = true
                            showLoader = false

                            return@launch
                        } else {
                            icError = false
                        }

                        if (vm.carNumber.isEmpty()) { // car
                            carError = true
                            showLoader = false

                            return@launch
                        } else {
                            carError = false
                        }

                        if (vm.visitDate.value.isEmpty()) { // date
                            dateError = true
                            showLoader = false

                            return@launch
                        } else {
                            dateError = false
                        }

                        if (vm.expectedEntryTime.value.isEmpty()) { // time
                            timeError = true
                            showLoader = false


                            return@launch
                        } else {
                            timeError = false
                        }


                       data = vm.visitorRegistration()

                        if (data != null) {
                            bitmap = vm.generateQr(data!!)

                            displayVisitor = true
                        } else {
                            showErrorDialog = true
                        }
                        showLoader = false
                    }
                }, showLoader = showLoader
            )

            if (showErrorDialog) {
                CustomDialogClose(
                    alertTitle = "Error",
                    alertBody = "Failed to register visitor. Please try again",
                    onDismissFun = { showErrorDialog = false },
                    btnCloseClick = { showErrorDialog = false })
            }

            if (displayVisitor) {
                CustomVisitorDialog(
                    onDismissFun = navHome
                ) {
                    data?.let { VisitorRegistered(navHome = navHome, data = it, qrCode = bitmap) }
                }
            }
        }
    }
//}

@Composable
fun CustomVisitorDialog(
    onDismissFun: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(onDismissRequest = onDismissFun, content = content)
}

/*
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Preview")
@Composable
fun VisitorRegistrationPreview() {
    NeighbourHubTheme {
        Surface {
            VisitorRegistration({}, VisitorRegistrationViewModel())
        }
    }
}
*/
