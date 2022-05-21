package com.example.neighbourhub.screens.residents

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.neighbourhub.ui.widgets.*
import com.example.neighbourhub.utils.DateTimeManager
import com.example.neighbourhub.viewmodel.VisitorRegistrationViewModel
import kotlinx.coroutines.launch

@Composable
fun VisitorRegistration(navBack: () -> Unit, vm: VisitorRegistrationViewModel = viewModel()) {

    // Composable State variables
    var showLoader by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    //Content
    Scaffold(topBar = {
        CustomTopAppBar_Back(title = "Visitor Registration", navBack = navBack)
    }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            CustomOutlinedTextField( //Visitor Name
                labelText = "Name",
                textValue = vm.name,
                onValueChangeFun = { vm.name = it },
                modifier = Modifier.padding(top = 16.dp)
            )

            CustomOutlinedTextField( //Visitor IC Number
                labelText = "IC Number",
                textValue = vm.ic,
                isSingleLine = true,
                onValueChangeFun = { vm.ic = it },
                modifier = Modifier.padding(top = 4.dp)
            )

            CustomOutlinedTextField( //Visitor Car Number
                labelText = "Car Number",
                textValue = vm.carNumber,
                isSingleLine = true,
                onValueChangeFun = { vm.carNumber = it },
                modifier = Modifier.padding(top = 4.dp)
            )


            CustomOutlinedTextField( //Visitor Visitation Date
                labelText = "Visitation Date",
                textValue = vm.visitDate.value,
                onValueChangeFun = { vm.visitDate.value = it },
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
                modifier = Modifier.padding(top = 4.dp)
            )

            CustomOutlinedTextField( //Visitor Expected Entry Time
                labelText = "Entry Time",
                textValue = vm.expectedEntryTime.value,
                onValueChangeFun = { vm.expectedEntryTime.value = it },
                isEnabled = false,
                isReadOnly = true,
                trailingIcon = {
                    CustomTimePicker(LocalContext.current, 8, 30, vm.expectedEntryTime)
                },
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            CustomButtonLoader(btnText = "Register Visitor",
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .width(300.dp), onClickFun = {
                    scope.launch {
                        showLoader = true
                        val result = vm.visitorRegistration()

                        if (result) {
//                        vm.generateQr()
                            navBack()
                        } else { //TODO: Validation
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
        }
    }
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
