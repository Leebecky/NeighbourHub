package com.example.neighbourhub.screens.setup

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.neighbourhub.ui.theme.NeighbourHubTheme
import com.example.neighbourhub.ui.widgets.CustomButton
import com.example.neighbourhub.ui.widgets.CustomIconButton
import com.example.neighbourhub.ui.widgets.CustomOutlinedTextField
import com.example.neighbourhub.ui.widgets.CustomTextField

@Composable
fun RaInvitation(navProfile: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        CustomOutlinedTextField(
            labelText = "RA Invitation Code",
            textValue = "vm.invCode",
            onValueChangeFun = {},
            trailingIcon = {
                CustomIconButton(
                    onClickFun = { /*TODO*/ },
                    icon = Icons.Filled.Help
                )
            })
        CustomButton(btnText = "Join", onClickFun = navProfile)
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Preview")
@Composable
fun RaInvitationPreview() {
    NeighbourHubTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            RaInvitation({})
        }
    }
}