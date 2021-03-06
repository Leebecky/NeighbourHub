package com.example.neighbourhub.screens.residents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.neighbourhub.models.Users
import com.example.neighbourhub.utils.Constants
import com.example.neighbourhub.viewmodel.MenuViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@Composable
fun Menu(
    navOut: () -> Unit,
    navController: NavController,
    userRole: String,
    vm: MenuViewModel = viewModel()
) {

    val scope = rememberCoroutineScope()

    Column {
        vm.menuItemList.forEach { item ->
            if (item.accessLevel == Constants.ResidentRole || item.accessLevel == userRole) {
                TextField(
                    value = item.title,
                    onValueChange = {},
                    enabled = false,
                    leadingIcon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.ChevronRight,
                            contentDescription = item.title
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.surface,
                        leadingIconColor = if (isSystemInDarkTheme()) Color.White else Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {

                            if (item.title == "Logout") {
                                scope.launch {
                                    Firebase.auth.signOut()
                                    Users.currentUserId = ""
                                navOut()
                                }
                            } else {
                                navController.navigate(item.route)
                            }
                        })
                )
            }
        }
    }
}


/*
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Preview")
@Composable
fun MenuPreview() {
    NeighbourHubTheme {
        Surface {
            Menu({})
        }
    }
}
*/
