package com.example.neighbourhub.screens

import android.content.res.Configuration
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.neighbourhub.screens.residents.bulletin.BulletinBoard
import com.example.neighbourhub.screens.residents.Chatroom
import com.example.neighbourhub.screens.residents.Marketplace
import com.example.neighbourhub.screens.residents.Menu
import com.example.neighbourhub.ui.theme.NeighbourHubTheme
import com.example.neighbourhub.utils.NavigationRoutes
import com.example.neighbourhub.viewmodel.HomeViewModel

@Composable
fun Home(navBack: () -> Unit, navBulletinCreation:()->Unit, vm: HomeViewModel = viewModel()) {
    var currentRoute by remember { mutableStateOf(NavigationRoutes.Bulletin) }

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
                        selectedContentColor = MaterialTheme.colors.secondary,
                        unselectedContentColor = MaterialTheme.colors.onPrimary

                    )
                }
            }

        },
        content = { padding ->
            when (currentRoute) {
                NavigationRoutes.Chatroom -> Chatroom()
                NavigationRoutes.Marketplace -> Marketplace()
//                NavigationRoutes.Bulletin -> BulletinCreation(navBack = navBack)
                NavigationRoutes.Bulletin -> BulletinBoard(padding = padding, navCreation = navBulletinCreation)
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
            Home({}, {}, HomeViewModel())
        }
    }
}
