package com.example.neighbourhub.screens

import android.content.res.Configuration
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.neighbourhub.models.Users
import com.example.neighbourhub.screens.residents.BulletinBoard
import com.example.neighbourhub.screens.residents.Chatroom
import com.example.neighbourhub.screens.residents.Marketplace
import com.example.neighbourhub.screens.residents.Menu
import com.example.neighbourhub.screens.residents.bulletin.BulletinCreation
import com.example.neighbourhub.ui.theme.NeighbourHubTheme
import com.example.neighbourhub.utils.NavigationRoutes
import com.example.neighbourhub.viewmodel.HomeViewModel

@Composable
fun Home(navBack: () -> Unit, vm: HomeViewModel = viewModel()) {
    var currentRoute by remember { mutableStateOf(NavigationRoutes.Bulletin) }
    // Retrieving logged in user data
    var currentUser: Users? = null
    val
    LaunchedEffect(key1 = Unit) {
        currentUser = Users.currentUserId?.let { Users.getCurrentUser(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "NeighbourHub") })
        },
        bottomBar = {

            BottomAppBar() {
                vm.bottomNavList.forEach { item ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                        },
                        label = { Text(item.title) },
                        selected = currentRoute == item.route,
                        onClick = { currentRoute = item.route },
                        selectedContentColor = MaterialTheme.colors.primaryVariant,
                        unselectedContentColor = MaterialTheme.colors.onPrimary

                    )
                }
            }

        },
        content = {
            when (currentRoute) {
                NavigationRoutes.Chatroom -> Chatroom()
                NavigationRoutes.Marketplace -> Marketplace()
                NavigationRoutes.Bulletin -> BulletinCreation(navBack = navBack, user = currentUser)
//                NavigationRoutes.Bulletin -> BulletinBoard(currentUser)
                NavigationRoutes.Menu -> Menu()
            }

        })

}


@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Preview"
)
@Composable
fun HomePreview() {
    NeighbourHubTheme {
        Surface {
            Home({}, HomeViewModel())
        }
    }
}
