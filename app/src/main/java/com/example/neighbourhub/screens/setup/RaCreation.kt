package com.example.neighbourhub.screens.setup

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.neighbourhub.ui.theme.NeighbourHubTheme
import com.example.neighbourhub.ui.widgets.CustomButton
import com.example.neighbourhub.ui.widgets.CustomIconButton
import com.example.neighbourhub.ui.widgets.CustomOutlinedTextField
import com.example.neighbourhub.ui.widgets.CustomTopAppBar_Back
import com.example.neighbourhub.viewmodel.RaCreationViewModel

@Composable
fun RaCreation(vm: RaCreationViewModel = viewModel(), navBack: () -> Unit) {
    var dropdownExpansion by remember { mutableStateOf(false) }

    var txtFieldSize by remember { mutableStateOf(Size.Zero) }
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { CustomTopAppBar_Back("RA Registration", navBack) }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            CustomOutlinedTextField(
                labelText = "Residential Association Name",
                textValue = vm.residentialName,
                onValueChangeFun = { vm.residentialName = it },
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
                onValueChangeFun = { vm.residentialArea = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 4.dp
                    )
            )
            CustomOutlinedTextField(
                labelText = "Postcode",
                textValue = vm.addPostcode,
                onValueChangeFun = { vm.addPostcode = it },
                isSingleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 4.dp
                    )
            )
            // Text Field for dropdown placeholder
            CustomOutlinedTextField(labelText = "State",
                textValue = vm.addState,
                onValueChangeFun = { vm.addState = it },
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

            ) {
                CustomIconButton(
                    onClickFun = { dropdownExpansion = !dropdownExpansion },
                    icon = if (dropdownExpansion) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown
                )

            }
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
            Spacer(modifier = Modifier.weight(1f))
            // Button
            //  TODO: RA Creation - Data Validation
            CustomButton(
                btnText = "Submit",
                onClickFun = { vm.registerAssociation() },
                modifier = Modifier.padding(bottom = 50.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Preview")
@Composable
fun RaCreationPreview() {
    NeighbourHubTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            RaCreation(RaCreationViewModel(), navBack = {})
        }
    }
}