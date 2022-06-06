package com.example.neighbourhub.screens

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.neighbourhub.screens.residents.Menu
import com.example.neighbourhub.screens.residents.VisitorRegistration
import com.example.neighbourhub.screens.residents.bulletin.BulletinBoard
import com.example.neighbourhub.utils.NavigationRoutes
import com.example.neighbourhub.viewmodel.HomeViewModel

@Composable
fun Home(
    navHome: () -> Unit,
    navOut: () -> Unit,
    navBulletinCreation: (id: String) -> Unit,
    navController: NavController,
    vm: HomeViewModel = viewModel()
) {
    var currentRoute by rememberSaveable { mutableStateOf(NavigationRoutes.Bulletin) }
    val currentUser = vm.currentUser.collectAsState()
    val scaffoldState = rememberScaffoldState()

//    if (currentUser.value.status == Constants.PendingStatus) {
//        navController.navigate(NavigationRoutes.UserProfile)
//    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "NeighbourHub") })
        },
        bottomBar = {

            BottomAppBar {
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
//                NavigationRoutes.Chatroom -> Chatroom()
//                NavigationRoutes.Marketplace -> Marketplace()
                NavigationRoutes.VisitorRegRoute -> VisitorRegistration(
                    padding = padding,
                    navHome = navHome,
                    scaffoldState = scaffoldState
                )
                NavigationRoutes.Bulletin -> BulletinBoard(
                    padding = padding,
                    navCreation = navBulletinCreation
                )
                NavigationRoutes.Menu -> Menu(
                    navOut,
                    navController,
                    userRole = currentUser.value.userRole
                )
            }

        })

}


//@Preview(showBackground = true)
//@Preview(
//    showBackground = true,
//    uiMode = Configuration.UI_MODE_NIGHT_YES,
//    name = "Dark Preview"
//)
//@Composable
//fun HomePreview() {
//    NeighbourHubTheme {
//        Surface {
//            Home({}, {}, HomeViewModel())
//        }
//    }
//}
