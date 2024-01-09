package com.example.gafitouser

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.gafitouser.notification.PushNotification
import com.example.gafitouser.notification.RetrofitInstance
import com.example.gafitouser.user.component.checkForPermission
import com.example.gafitouser.user.component.ui.theme.GafitoUserTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
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
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
    }
}


private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
    try {
        val response = RetrofitInstance.api.postNotification(notification)
        if (response.isSuccessful) {
            Log.d(TAG, "Response: ${Gson().toJson(response)}")
        } else {
            Log.e(TAG, response.errorBody().toString() )
        }

    } catch (e: Exception) {
        Log.e(TAG, e.toString())
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