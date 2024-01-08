package com.example.gafitouser.frontend

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.gafitouser.R

@Composable
fun PermissionScreen(
    onPermissionGranted: () -> Unit
) {
    // A variable to track whether the permission request has been launched
    var permissionRequestLaunched by remember { mutableStateOf(false) }

    // Create a launcher for requesting location permissions
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        var isGranted = true
        permissions.entries.forEach {
            if (!it.value) {
                isGranted = false
                return@forEach
            }
        }

        if (isGranted) {
            onPermissionGranted()
        }
    }

    // Use LaunchedEffect to run the permission request when the composable is first launched
    LaunchedEffect(Unit) {
        if (!permissionRequestLaunched) {
            // Launch the permission request
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                )
            )
            // Set the flag to true to avoid launching the permission request again
            permissionRequestLaunched = true
        }
    }

    // UI components
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // You can customize the UI as needed
        Image(painter = painterResource(id = R.drawable.gafito), contentDescription = null)
    }
}