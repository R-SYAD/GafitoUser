package com.example.gafitouser.frontend

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.gafitorsatpam.component.laporanComp.DetailLaporan
import com.example.gafitouser.GafitoViewModel
import com.example.gafitouser.data.LaporanData
import com.example.gafitouser.user.component.TopBarAtas
import com.example.gafitouser.user.component.ui.theme.GafitoUserTheme


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailLaporanScreen(navController: NavController, vm: GafitoViewModel, laporan: LaporanData) {
    Scaffold(
        topBar = {TopBarAtas("Detail Laporan", navController)}
    ) {
            paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)

        ) {
//        your code compose here
            DetailLaporan(navController = navController, vm = vm, laporan = laporan)

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailLaporanPrev() {
    GafitoUserTheme {
    }
}