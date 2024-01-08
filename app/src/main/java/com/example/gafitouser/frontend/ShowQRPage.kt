package com.example.gafitouser.frontend

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gafitouser.GafitoViewModel
import com.example.gafitouser.user.component.BottomBar
import com.example.gafitouser.user.component.PhotoProfile
import com.example.gafitouser.user.component.QrCodeImage
import com.example.gafitouser.user.component.TopBar
import com.example.gafitouser.user.component.ui.theme.GafitoUserTheme
import com.example.gafitouser.user.models.BottomBarItem


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowQrPage(navController: NavController, vm: GafitoViewModel) {
    val userData = vm.userData.value
    val noPolisiDisplay = userData?.noPolisi ?: ""

        GafitoUserTheme {
        Scaffold(
            topBar = { TopBar() },
            bottomBar = {
                BottomBar(
                    selectedItem = BottomBarItem.HOME,
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
                //                .padding(bottom = 32.dp)

            ) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .border(1.dp, Color.Black, shape = RoundedCornerShape(32.dp))
                        .background(color = Color.White, shape = RoundedCornerShape(32.dp))
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp)
                    ) {
                        PhotoProfile(userData?.imageUrl) {}
                        Spacer(modifier = Modifier.height(16.dp))
                        QrCodeImage(content = noPolisiDisplay, size = 300.dp)

                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ShowQrPagePreview() {
    GafitoUserTheme {
    }
}