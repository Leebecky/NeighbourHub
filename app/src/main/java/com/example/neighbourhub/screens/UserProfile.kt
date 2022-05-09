package com.example.neighbourhub.screens

import android.content.res.Configuration
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.neighbourhub.screens.setup.UserWelcome
import com.example.neighbourhub.ui.theme.NeighbourHubTheme
import com.example.neighbourhub.ui.widgets.CustomTextField

@Composable
fun UserProfile(){
    CustomTextField(labelText = "", textValue = "", onValueChangeFun = {})
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Preview")
@Composable
fun UserWelcomePreview() {
    NeighbourHubTheme {
        Surface {
            UserProfile()
        }
    }
}