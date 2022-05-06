package com.example.neighbourhub.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.neighbourhub.ui.theme.NeighbourHubTheme
import com.example.neighbourhub.ui.widgets.CustomButton

//Testing Page for navigation demonstration
@Composable
fun Registration(navControl: () -> Unit, name: String) {
    NeighbourHubTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Text(text = "Hello $name!")
            CustomButton(btnText = "Back", onClickFun = navControl)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NeighbourHubTheme {
//        Registration("Android")
    }
}