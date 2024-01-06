package com.example.gafitouser.frontend

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
import androidx.navigation.NavController
import com.example.gafitouser.GafitoViewModel
import com.example.gafitouser.user.component.BottomBar
import com.example.gafitouser.user.component.TopBar
import com.example.gafitouser.user.models.BottomBarItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportPage(navController: NavController, vm: GafitoViewModel) {
    Scaffold(
        topBar = { TopBar() },
        bottomBar = {
            BottomBar(
                selectedItem = BottomBarItem.REPORT,
                navController = navController
            )
        }
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
//                .padding(bottom = 32.dp)

        ) {

        }
    }
}

@Preview
@Composable
fun ReportPagePreview() {
}