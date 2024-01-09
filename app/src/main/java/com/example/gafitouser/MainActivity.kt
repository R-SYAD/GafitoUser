package com.example.gafitouser

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gafitouser.data.LaporanData
import com.example.gafitouser.frontend.DetailLaporanScreen
import com.example.gafitouser.frontend.LocationPage
import com.example.gafitouser.frontend.PermissionScreen
import com.example.gafitouser.frontend.ProfilePage
import com.example.gafitouser.frontend.ReportPage
import com.example.gafitouser.frontend.ShowQrPage
import com.example.gafitouser.frontend.auth.LoginScreen
import com.example.gafitouser.frontend.auth.SignupScreen
import com.example.gafitouser.main.NotificationMessage
import com.example.gafitouser.user.component.checkForPermission
import com.example.gafitouser.user.component.ui.theme.GafitoUserTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GafitoUserTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Use a mutable State variable for read-write access
                    var hasLocationPermission by remember { mutableStateOf(checkForPermission(this)) }

                    if (hasLocationPermission) {
                        GafitoApp()
                    } else {
                        PermissionScreen {
                            // Update the state properly within a composable
                            hasLocationPermission = true
                        }
                    }
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
    object ListLaporan: DestinationScreen("list_laporan")
    object DetailLaporan: DestinationScreen("detail_laporan")
}

@RequiresApi(Build.VERSION_CODES.O)
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
        composable(DestinationScreen.ListLaporan.route) {
            ReportPage(navController = navController, vm = vm)
        }
        composable(DestinationScreen.DetailLaporan.route) {
//            val laporanData = navController.currentBackStackEntry?.arguments?.getParcelable<LaporanData>("laporan")
            val laporanData = navController.previousBackStackEntry?.savedStateHandle?.get<LaporanData>("laporan")
            Log.d("laporan", "Argumen navigasi: ${navController.currentBackStackEntry?.arguments}")
            // Periksa apakah laporan adalah null
            if (laporanData == null) {
                // Tampilkan pesan kesalahan
                Log.e("laporan", "laporan tidak ditemukan nih, kosong")
            }
            val test = navController.currentBackStackEntry
            Log.d("laporanData", "laporanData to $laporanData")
            Log.d("Tst", "ada ga $test")

            laporanData?.let {
                DetailLaporanScreen(
                    navController = navController,
                    vm = vm,
                    laporan = laporanData
                )
            }
//                ?: run {
//                Text(text = "Laporan tidak ditemukan")
//            }
            // Print for debugging
            println("laporanData: $laporanData")
            println("laporanData: $test")
        }
    }
}