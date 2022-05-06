package com.example.neighbourhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.neighbourhub.screens.Login
import com.example.neighbourhub.screens.Registration
import com.example.neighbourhub.ui.theme.NeighbourHubTheme
import com.example.neighbourhub.utils.NavigationRoutes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NeighbourHubTheme(darkTheme = false) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavigationController()
                }
            }
        }
    }
}

@Composable
fun NavigationController() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable(route = NavigationRoutes.Login) { Login(
            toRegistration = {navController.navigate(NavigationRoutes.Registration)}
        ) }
        composable(route = NavigationRoutes.Registration) {
            Registration(
                navControl = { navController.popBackStack() },
                "Test"
            )
        }
    }
}

