package com.example.neighbourhub.screens.residents

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.neighbourhub.models.Users
import com.example.neighbourhub.screens.Home
import com.example.neighbourhub.ui.theme.NeighbourHubTheme
import com.google.firebase.firestore.auth.User

@Composable
fun BulletinBoard(user: Users?) {
    Column() {
        Text("Bulletin Board")
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Preview")
@Composable
fun BulletinBoardPreview() {
    NeighbourHubTheme {
        Surface {
            BulletinBoard(Users())
        }
    }
}
