package com.example.neighbourhub.screens.setup

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.neighbourhub.ui.theme.NeighbourHubTheme
import com.example.neighbourhub.ui.widgets.CustomButtonLoader
import com.example.neighbourhub.ui.widgets.CustomDialogClose
import com.example.neighbourhub.ui.widgets.CustomIconButton
import com.example.neighbourhub.ui.widgets.CustomOutlinedTextField
import com.example.neighbourhub.viewmodel.RaInvitationViewModel
import kotlinx.coroutines.launch

@Composable
fun RaInvitation(
    navProfile: () -> Unit,
    navRaCreation: () -> Unit,
    vm: RaInvitationViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()
    var openDialog by remember { mutableStateOf(false) }
    var openInvCodeError by remember { mutableStateOf(false) }
    var showLoader by remember { mutableStateOf(false) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(2f))
            CustomOutlinedTextField(
                labelText = "RA Invitation Code",
                textValue = vm.invCode,
                isSingleLine = true,
                onValueChangeFun = { vm.invCode = it }
            ) {
                CustomIconButton(
                    onClickFun = { openDialog = true },
                    icon = Icons.Filled.Help
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            CustomButtonLoader(
                btnText = "Join", onClickFun = {
                    scope.launch {
                        showLoader = true
                        val outcome = vm.setRaForUser()

                        showLoader = false
                        if (outcome) {
                            navProfile()
                        } else {
                            openInvCodeError = true
                        }
                    }
                },
                showLoader = showLoader
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "No residential association? Click here to register!",
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(24.dp)
                    .clickable {
                        navRaCreation()
                    })

            // Alert Dialog for the Help (?) Icon
            if (openDialog) {
                CustomDialogClose(
                    "Invitation Code",
                    "Consult your residential association committee to get the invitation code to join your residential community on NeighbourHub!",
                    onDismissFun = { openDialog = false },
                    btnCloseClick = { openDialog = false })
            }

            // Error Dialog for Invalid Invitation Code
            if (openInvCodeError) {
                CustomDialogClose(
                    alertTitle = "Resident Association Not Found",
                    alertBody = "No resident association with that invitation code found. Please try again",
                    onDismissFun = { openInvCodeError = false },
                    btnCloseClick = { openInvCodeError = false })
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Preview")
@Composable
fun RaInvitationPreview() {
    NeighbourHubTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            RaInvitation({}, {}, RaInvitationViewModel())
        }
    }
}