package com.example.gafitouser.frontend

import android.annotation.SuppressLint
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
import com.example.gafitouser.main.CommonProgressSpinner
import com.example.gafitouser.user.component.BottomBar
import com.example.gafitouser.user.component.DetailProfil
import com.example.gafitouser.user.component.TopBar
import com.example.gafitouser.user.component.ui.theme.GafitoUserTheme
import com.example.gafitouser.user.models.BottomBarItem
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(navController: NavController, vm: GafitoViewModel) {

    val isLoading = vm.inProgress.value

    Scaffold(
        topBar = { TopBar() },
        bottomBar = {
            BottomBar(
                selectedItem = BottomBarItem.PROFILE,
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
                .background(MaterialTheme.colorScheme.secondary)
        ) {
            DetailProfil(navController, vm)
        }
    }
    if (isLoading)
        CommonProgressSpinner()
}

@Preview(showBackground = true)
@Composable
fun ProfilePagePreview() {
    GafitoUserTheme {
    }
}