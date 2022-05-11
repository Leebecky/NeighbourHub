package com.example.neighbourhub.screens.residents

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.neighbourhub.screens.Home
import com.example.neighbourhub.ui.theme.NeighbourHubTheme

@Composable
fun Marketplace() {
    Column() {
        Text("Marketplace")
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Preview")
@Composable
fun MarketplacePreview() {
    NeighbourHubTheme {
        Surface {
            Marketplace()
        }
    }
}
