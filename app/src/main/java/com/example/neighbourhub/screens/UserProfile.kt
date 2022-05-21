package com.example.neighbourhub.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.neighbourhub.models.Users
import com.example.neighbourhub.ui.widgets.CustomButtonLoader
import com.example.neighbourhub.ui.widgets.CustomOutlinedTextField
import com.example.neighbourhub.ui.widgets.CustomTopAppBar_Back
import com.example.neighbourhub.viewmodel.UserProfileViewModel
import kotlinx.coroutines.launch

@Composable
fun UserProfile(
    vm: UserProfileViewModel = viewModel(),
    navBack: () -> Unit,
    navHome: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val scroll = rememberScrollState()
    var loaderState by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            CustomTopAppBar_Back(title = "User Profile", navBack = navBack)
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
                text = "User Details",
                modifier = Modifier
                    .padding(top = 25.dp)
            )
            CustomOutlinedTextField( // Name
                labelText = "Full Name",
                textValue = vm.name,
                onValueChangeFun = { vm.name = it })

            CustomOutlinedTextField( // Age
                labelText = "Age",
                textValue = vm.age,
                onValueChangeFun = { vm.age = it })

            CustomOutlinedTextField( // Contact Number
                labelText = "Contact Number",
                textValue = vm.contactNumber,
                onValueChangeFun = { vm.contactNumber = it })

            // Address
            Text(
                text = "Address",
                modifier = Modifier
                    .padding(top = 25.dp)
            )

            CustomOutlinedTextField( // House Number
                labelText = "House Number",
                textValue = vm.houseNo,
                onValueChangeFun = { vm.houseNo = it })

            CustomOutlinedTextField( // Street Name
                labelText = "Street Name",
                textValue = vm.street,
                onValueChangeFun = { vm.street = it })

            CustomOutlinedTextField( // Residential Area
                labelText = "Residential Area",
                textValue = vm.residentialArea,
                onValueChangeFun = { }, isEnabled = false
            )

            CustomOutlinedTextField( // Postcode
                labelText = "Postcode",
                textValue = vm.postcode,
                onValueChangeFun = { }, isEnabled = false
            )

            CustomOutlinedTextField( // State
                labelText = "State",
                textValue = vm.state,
                onValueChangeFun = { }, isEnabled = false
            )

            CustomButtonLoader(btnText = "Save",
                onClickFun = {
                scope.launch {
                    loaderState = true
                    val status = vm.updateProfile()
                    if (status) {
                        //Return to Home
                        navHome()
                    } else {
                        //TODO: Add error dialog
                    }
                    loaderState = false
                }
            }, showLoader = loaderState, modifier = Modifier.padding(vertical = 5.dp))
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
