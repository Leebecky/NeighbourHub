package com.example.neighbourhub.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.neighbourhub.R
import com.example.neighbourhub.screens.residents.Menu
import com.example.neighbourhub.screens.residents.VisitorRegistration
import com.example.neighbourhub.screens.residents.bulletin.BulletinBoard
import com.example.neighbourhub.utils.NavigationRoutes
import com.example.neighbourhub.viewmodel.HomeViewModel

private var backPressedTime: Long = 0

@ExperimentalComposeUiApi
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
    val context = LocalContext.current
    var backPressedTime: Long = 0
    var backInterceptor by rememberSaveable { mutableStateOf(true) }


    BackHandler(enabled = backInterceptor, onBack = {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            Toast.makeText(context, "Tap Back once (1) to exit.", Toast.LENGTH_SHORT).show()
            backInterceptor = false
            return@BackHandler
        } else {
            Toast.makeText(context, "Tap Back twice (2) to exit.", Toast.LENGTH_SHORT).show()
            backInterceptor = true
        }
        backPressedTime = System.currentTimeMillis()
    })

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(R.string.app_name)) })
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
                    navHome = navHome
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
