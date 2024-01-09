package com.example.gafitouser.frontend

//import com.example.gafitorsatpam.component.laporanComp.LaporanList
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gafitouser.DestinationScreen
import com.example.gafitouser.GafitoViewModel
import com.example.gafitouser.main.NavParam
import com.example.gafitouser.main.navigateTo
import com.example.gafitouser.user.component.BottomBar
import com.example.gafitouser.user.component.ListLaporanView
import com.example.gafitouser.user.component.TopBar
import com.example.gafitouser.user.component.ui.theme.GafitoUserTheme
import com.example.gafitouser.user.models.BottomBarItem

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ReportPage(navController: NavController, vm: GafitoViewModel) {
    val userData = vm.userData.value
    val isLoading = vm.inProgress.value

    val laporansLoading = vm.refreshLaporanProgress.value
    val laporans = vm.laporans.value

    Scaffold(
        topBar = { TopBar() },
        bottomBar = {
            BottomBar(
                selectedItem = BottomBarItem.REPORT,
                navController = navController
            )
        },
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.secondary)

        ) {
//        your code compose here
            ListLaporanView(
                isContextLoading = isLoading,
                laporans = laporans,
                laporansLoading = laporansLoading,
                modifier = Modifier
                    .weight(1f)
                    .padding(1.dp)
                    .fillMaxSize()
            ) { laporan ->
                navController.currentBackStackEntry?.savedStateHandle?.set("laporan", laporan)
                navigateTo(
                    navController,
                    DestinationScreen.DetailLaporan,
                    NavParam("laporan", laporan),
                )

                Log.d("laporan", "Laporan yang dikirim: $laporan")
                Log.d("laporan", "Argumen navigasi: ${navController.currentBackStackEntry?.arguments}")
            }
            //kalau make tampilan galeri
//            LaporanList(
//                isContextLoading = isLoading,
//                laporansLoading = laporansLoading,
//                laporans = laporans,
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(1.dp)
//                    .fillMaxSize(),
//            ) { laporan ->
//                navigateTo(
//                    navController = navController,
//                    DestinationScreen.DetailLaporan,
//                    NavParam("laporan", laporan)
//                )
//            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun LaporanPreview() {
    GafitoUserTheme {
    }
}