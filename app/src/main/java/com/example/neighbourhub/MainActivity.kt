package com.example.neighbourhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.neighbourhub.screens.*
import com.example.neighbourhub.screens.committee.ManagePayment
import com.example.neighbourhub.screens.committee.ManageRa
import com.example.neighbourhub.screens.committee.VisitorLog
import com.example.neighbourhub.screens.residents.Chatroom
import com.example.neighbourhub.screens.residents.Marketplace
import com.example.neighbourhub.screens.residents.Phonebook
import com.example.neighbourhub.screens.residents.bulletin.BulletinCreation
import com.example.neighbourhub.screens.setup.RaCreation
import com.example.neighbourhub.screens.setup.RaInvitation
import com.example.neighbourhub.screens.setup.UserWelcome
import com.example.neighbourhub.ui.theme.NeighbourHubTheme
import com.example.neighbourhub.utils.NavigationRoutes
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalPermissionsApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            NeighbourHubTheme(darkTheme = false) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val currentUser = FirebaseAuth.getInstance().currentUser

                    NavigationController(currentUser)
                }
            }
        }
    }
//
//    override fun onBackPressed() {
//        if (pressedTime + 2000 > System.currentTimeMillis()) {
//            super.onBackPressed()
//            finish()
//        } else {
//            Toast.makeText(this.baseContext, "Press back again to exit", Toast.LENGTH_SHORT).show()
//        }
//        pressedTime = System.currentTimeMillis()
//    }
}

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalPermissionsApi
@Composable
fun NavigationController(currentUser: FirebaseUser?) {
    val startScreen: String =
        if (currentUser == null) NavigationRoutes.Login else NavigationRoutes.Home
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationRoutes.SplashScreen) {

        // SplashScreen
        composable(route = NavigationRoutes.SplashScreen) {
            SplashScreen(navController, startUpRoute = startScreen)
        }

        // Login Route
        composable(route = NavigationRoutes.Login) {
            Login(
                navRegistration = { navController.navigate(NavigationRoutes.Registration) },
                navHome = {
                    navController.navigate(NavigationRoutes.Home) {
                        navController.backQueue.clear()
//                        popUpTo(navController.graph.findStartDestination().id) {
//                            inclusive = true
//                        }
                    }
                })
        }

        // Registration Route
        composable(route = NavigationRoutes.Registration) {
            Registration(
                navBack = { navController.navigateUp() },
                navHome = { navController.navigate(NavigationRoutes.SetupWelcome) }
            )
        }

        // Home Route
        composable(route = NavigationRoutes.Home) {

            Home(
                navHome = { //Return to Home page
                    navController.navigate(NavigationRoutes.Home) {
                        navController.backQueue.clear()
//                        popUpTo(navController.graph.findStartDestination().id) {
//                            inclusive = true
//                        }
                    }
                },
                navOut = { //Logout
                    navController.navigate(NavigationRoutes.Login) {
                        navController.backQueue.clear()
//                        popUpTo(navController.graph.findStartDestination().id) {
//                            inclusive = true
//                        }
                    }
                },
                //Bulletin board navi
                navBulletinCreation =
                { navController.navigate("${NavigationRoutes.BulletinCreation}/$it") },
                navController = navController
            )
        }

        // Setup/Welcome Route
        composable(route = NavigationRoutes.SetupWelcome) {
            UserWelcome(navInvitation = { navController.navigate(NavigationRoutes.SetupRaInvitation) })
        }

        // Setup/RaInvitation Route
        composable(route = NavigationRoutes.SetupRaInvitation) {
            RaInvitation(
                navProfile = { navController.navigate(NavigationRoutes.UserProfile) },
                navRaCreation = { navController.navigate(NavigationRoutes.RaCreation) })
        }

        // RaCreation Route
        composable(route = NavigationRoutes.RaCreation) {
            RaCreation(navBack = { navController.navigateUp() })
        }

        // User Profile Route
        composable(
            route = NavigationRoutes.UserProfile,
        ) {
            UserProfile(navBack = { navController.navigateUp() },
                navHome = {
                    navController.navigate(NavigationRoutes.Home) {
                        navController.backQueue.clear()
//                        popUpTo(navController.graph.findStartDestination().id) {
//                            inclusive = true
//                        }
                    }
                }
            )
        }

//        // Menu Route
//        composable(route = NavigationRoutes.Menu) {
//            Menu()
//        }

        // Bulletin Board Creation Route
//        composable(route = NavigationRoutes.BulletinCreation) {
//            BulletinCreation(navBack = { navController.navigateUp() })
//        }

        // Bulletin Board Edit Route [TEST]
        composable(
            route = "${NavigationRoutes.BulletinCreation}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType }
            )) { backStackEntry ->
            backStackEntry.arguments?.getString("id")?.let {
                BulletinCreation(
                    navBack = { navController.navigateUp() },
                    id = it
                )
            }
//            BulletinCreation(
//                navBack = { navController.navigateUp() },
//                id = it.arguments?.getString("id") ?: ""
//            )
        }

        // Chatroom Route
        composable(route = NavigationRoutes.Chatroom) {
            Chatroom()
        }

        // Marketplace Route
        composable(route = NavigationRoutes.Marketplace) {
            Marketplace()
        }

        // Manage Payment Route
        composable(route = NavigationRoutes.ManagePayment) {
            ManagePayment(navCreation = {}, navBack = { navController.navigateUp() })
        }

        // Manage Ra Route
        composable(route = NavigationRoutes.ManageRa) {
            ManageRa(
                navBack = { navController.navigateUp() },
                navHome = {
                    navController.navigate(NavigationRoutes.Home) {
                        navController.backQueue.clear()
//                        popUpTo(navController.graph.findStartDestination().id) {
//                            inclusive = true
//                        }
                    }
                })
        }

        // Phonebook Route
        composable(route = NavigationRoutes.Phonebook) {
            Phonebook(navBack = { navController.navigateUp() })
        }

//        // VisitorRegistration Route
//        composable(route = NavigationRoutes.VisitorRegRoute) {
//            VisitorRegistration(
//                navBack = { navController.navigateUp() },
//                navHome = {
//                    navController.navigate(NavigationRoutes.Home) {
//                        popUpTo(navController.graph.findStartDestination().id) {
//                            inclusive = true
//                        }
//                    }
//                })
//        }

        // Logout Route
        composable(route = NavigationRoutes.LogoutRoute) {
            navController.navigate(NavigationRoutes.Login) {
                navController.backQueue.clear()
//                popUpTo(navController.graph.findStartDestination().id) {
//                    inclusive = true
//                }
            }
        }

        //Visitor Log Route
        composable(route = NavigationRoutes.VisitorLog) {
            VisitorLog(navBack = { navController.navigateUp() })
        }

        /*
    // User Profile Route with Arguments
    composable(
        route = "${NavigationRoutes.UserProfile}/{fromSetup}",
        arguments = listOf(navArgument("fromSetup") { type = NavType.StringType }
        )) {
        UserProfile(
            navBack = { navController.navigateUp() },
            fromSetup = it.arguments?.getString("fromSetup") ?: ""
        )
    }
*/
    }
}

