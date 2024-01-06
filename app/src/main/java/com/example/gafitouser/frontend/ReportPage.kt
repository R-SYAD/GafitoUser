package com.example.gafitouser.frontend

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.gafitouser.user.component.BottomBar
import com.example.gafitouser.user.component.ListLaporanView
import com.example.gafitouser.user.component.TopBar
import com.example.gafitouser.user.component.ui.theme.GafitoUserTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportPage() {
    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomBar() }
    ) {
            paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.White)
//                .padding(bottom = 32.dp)

        )  {
            ListLaporanView()

        }
    }
}

@Preview
@Composable
fun ReportPagePreview() {
    GafitoUserTheme {
    ReportPage()
    }
}