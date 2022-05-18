package com.example.neighbourhub.screens.residents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.neighbourhub.ui.theme.PrimaryDark
import com.example.neighbourhub.ui.theme.PrimaryLight
import com.example.neighbourhub.viewmodel.MenuViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun Menu(navOut: () -> Unit, navController: NavController, vm: MenuViewModel = viewModel()) {
    Column() {
//        CustomButton(btnText = "Logout", onClickFun = {
//            Firebase.auth.signOut()
//            navOut()
//        })

        vm.menuItemList.forEach { item ->
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
                            Firebase.auth.signOut()
                            navOut()
                        } else {
                            navController.navigate(item.route)
                        }
                    })
            )
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
