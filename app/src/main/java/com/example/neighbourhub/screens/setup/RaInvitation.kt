package com.example.neighbourhub.screens.setup

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.neighbourhub.ui.theme.NeighbourHubTheme
import com.example.neighbourhub.ui.widgets.*
import com.example.neighbourhub.viewmodel.RaInvitationViewModel

@Composable
fun RaInvitation(
    navProfile: () -> Unit,
    navRaCreation: () -> Unit,
    vm: RaInvitationViewModel = viewModel()
) {
    //TODO: Implement ViewModel for managing dialog/RA Codes
    val openDialog = remember { mutableStateOf(false) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(2f))
            CustomOutlinedTextField(
                labelText = "RA Invitation Code",
                textValue = vm.invCode,
                onValueChangeFun = { vm.invCode = it }
            ) {
                CustomIconButton(
                    onClickFun = { openDialog.value = true },
                    icon = Icons.Filled.Help
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            CustomButton(btnText = "Join", onClickFun = navProfile)
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
            if (openDialog.value) {
                CustomDialogClose(
                    "Invitation Code",
                    "Consult your residential association committee to get the invitation code to join your residential community on NeighbourHub!",
                    onDismissFun = { openDialog.value = false },
                    btnCloseClick = { openDialog.value = false })
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