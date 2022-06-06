package com.example.neighbourhub.screens.setup

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.neighbourhub.ui.widgets.CustomButton

@Composable
fun UserWelcome(navInvitation: () -> Unit) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.primary) {
        Column(modifier = Modifier.padding(top = 30.dp, start = 8.dp)) {
            Text(text = "Welcome \nto", style = MaterialTheme.typography.h2)
            Text(text = "Neighbour\nHub", style = MaterialTheme.typography.h2)
            Spacer(Modifier.weight(1f))
            CustomButton(
                btnText = "Continue",
                onClickFun = navInvitation,
                btnColor = MaterialTheme.colors.secondary,
                btnTextColor = Color.Black,
                modifier = Modifier
                    .padding(bottom = 30.dp)
                    .height(80.dp)
                    .width(200.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.weight(1f))
        }
    }
}

/*
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Preview")
@Composable
fun UserWelcomePreview() {
    NeighbourHubTheme {
        Surface {
            UserWelcome(navInvitation = {})
        }
    }
}*/
