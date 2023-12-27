package com.example.gafitouser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gafitouser.auth.LoginScreen
import com.example.gafitouser.auth.SignupScreen
import com.example.gafitouser.main.NotificationMessage
import com.example.gafitouser.screen.LocationPage
import com.example.gafitouser.screen.ProfilePage
import com.example.gafitouser.screen.ReportPage
import com.example.gafitouser.screen.ShowQrPage
import com.example.gafitouser.ui.theme.GafitoUserTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GafitoUserTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GafitoApp()
                }
            }
        }
    }
}

sealed class DestinationScreen(val route: String) {
    object Signup: DestinationScreen("signup")
    object Login: DestinationScreen("login")
    object ShowQR: DestinationScreen("showqr")
    object Location: DestinationScreen("location")
    object Report: DestinationScreen("report")
    object Profile: DestinationScreen("profile")
}

@Composable
fun GafitoApp() {
    val vm = hiltViewModel<GafitoViewModel>()
    val navController = rememberNavController()

    NotificationMessage(vm = vm)

    NavHost(navController = navController, startDestination = DestinationScreen.Signup.route) {
        composable(DestinationScreen.Signup.route) {
            SignupScreen(navController = navController, vm = vm)
        }
        composable(DestinationScreen.Login.route) {
            LoginScreen(navController = navController, vm = vm)
        }
        composable(DestinationScreen.ShowQR.route) {
            ShowQrPage(navController = navController, vm = vm)
        }
        composable(DestinationScreen.Location.route) {
            LocationPage(navController = navController, vm = vm)
        }
        composable(DestinationScreen.Report.route) {
            ReportPage(navController = navController, vm = vm)
        }
        composable(DestinationScreen.Profile.route) {
            ProfilePage(navController = navController, vm = vm)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GafitoUserTheme {
        GafitoApp()
    }
}