package com.example.neighbourhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.neighbourhub.models.Users
import com.example.neighbourhub.screens.Home
import com.example.neighbourhub.screens.Login
import com.example.neighbourhub.screens.Registration
import com.example.neighbourhub.screens.UserProfile
import com.example.neighbourhub.screens.residents.*
import com.example.neighbourhub.screens.setup.RaCreation
import com.example.neighbourhub.screens.setup.RaInvitation
import com.example.neighbourhub.screens.setup.UserWelcome
import com.example.neighbourhub.ui.theme.NeighbourHubTheme
import com.example.neighbourhub.utils.NavigationRoutes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : ComponentActivity() {
    var myUser: Users? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
}

@Composable
fun NavigationController(currentUser: FirebaseUser?) {
    val startScreen: String =
        if (currentUser == null) NavigationRoutes.Login else NavigationRoutes.Home
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startScreen) {

        // Login Route
        composable(route = NavigationRoutes.Login) {
            Login(
                navRegistration = { navController.navigate(NavigationRoutes.Registration) },
                navHome = { navController.navigate(NavigationRoutes.SetupWelcome) }
            )
        }

        // Registration Route
        composable(route = NavigationRoutes.Registration) {
            Registration(
                navBack = { navController.navigateUp() },
                navHome = { navController.navigate(NavigationRoutes.Home) }
            )
        }

        // Home Route
        composable(route = NavigationRoutes.Home) {
//            val backStackEntry by navController.currentBackStackEntryAsState()
//            var currentRoute = backStackEntry?.destination?.route
//            currentRoute = currentRoute ?: ""
            Home(navBack = {navController.navigateUp()})
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
            UserProfile(navBack = { navController.navigateUp() }
            )
        }

        // Menu Route
        composable(route = NavigationRoutes.Menu) {
            Menu()
        }

//        // Bulletin Board Route
//        composable(route = NavigationRoutes.Bulletin) {
//            BulletinBoard()
//        }

        // Chatroom Route
        composable(route = NavigationRoutes.Chatroom) {
            Chatroom()
        }

        // Marketplace Route
        composable(route = NavigationRoutes.Marketplace) {
            Marketplace()
        }

        // Phonebook Route
        composable(route = NavigationRoutes.Phonebook) {
            Phonebook()
        }

        // VisitorRegistration Route
        composable(route = NavigationRoutes.VisitorReg) {
            VisitorRegistration()
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

