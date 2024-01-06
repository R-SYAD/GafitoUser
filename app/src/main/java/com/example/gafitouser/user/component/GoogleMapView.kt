package com.example.gafitouser.user.component

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gafitouser.ui.theme.GafitoUserTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import java.util.Locale

@Composable
fun MainScreen() {
    var latitude by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }
    var isLocationLoaded by remember { mutableStateOf(false) }
    var currentLocation by remember { mutableStateOf(LatLng(0.0, 0.0)) }
    val context = LocalContext.current
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context as ComponentActivity)

    // Variabel untuk menyimpan lokasi yang akan ditandai
    var markedLatitude by remember { mutableStateOf("") }
    var markedLongitude by remember { mutableStateOf("") }
    var markedLocationName by remember { mutableStateOf("") }

    DisposableEffect(context) {
        if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            // Periksa apakah layanan lokasi telah diaktifkan
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location ->
                        if (location != null) {
                            currentLocation = LatLng(location.latitude, location.longitude)
                            isLocationLoaded = true
                        } else {
                            Log.e("MainScreen", "Last known location is null")
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.e("MainScreen", "Error getting location", e)
                    }
            } else {
                // Jika layanan lokasi tidak diaktifkan, tampilkan dialog untuk mengaktifkannya
                context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
        } else {
            Log.e("MainScreen", "Location permission not granted")
        }
        onDispose {
            // Cleanup, if needed
        }
    }


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Current Location:",
                    style = typography.labelMedium,
                    modifier = Modifier.weight(1f)
                )
                if (isLocationLoaded) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    ) {
                        Text(
                            text = "Lat: ${currentLocation.latitude}",
                            style = typography.bodyMedium
                        )
                        Text(
                            text = "Long: ${currentLocation.longitude}",
                            style = typography.bodyMedium
                        )
                    }
                } else {
                    Text(
                        text = "Loading...",
                        style = typography.bodyMedium,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )
                }

            }

            // Tombol "Mark Location"
            Button(
                onClick = {
                    // Menyimpan data latitude dan longitude yang ditandai
                    markedLatitude = currentLocation.latitude.toString()
                    markedLongitude = currentLocation.longitude.toString()
                    markedLocationName = getLocationName(context, currentLocation.latitude, currentLocation.longitude)


                    // Tambahkan logika lain jika diperlukan
                    Log.d("MarkLocation", "Location marked!")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Mark Location")
            }

            // Menampilkan latitude dan longitude yang ditandai
            if (isLocationLoaded) {
                Text(
                    text = "Location : $markedLocationName",
                    style = typography.bodyMedium,
                    modifier = Modifier
                        .padding(top = 8.dp)
                )
            }

// Tombol "Find My Location"
            Button(
                onClick = {
                    if (isLocationLoaded && markedLatitude.isNotEmpty() && markedLongitude.isNotEmpty()) {
                        // Membuka aplikasi Google Maps dan menavigasikan ke lokasi yang ditandai
                        val uri = "geo:$markedLatitude,$markedLongitude?q=$markedLatitude,$markedLongitude"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                        context.startActivity(intent)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Find My Location on Maps")
            }

        }
    }

}

private fun getLocationName(context: Context, latitude: Double, longitude: Double): String {
    val geocoder = Geocoder(context, Locale.getDefault())
    val addresses = geocoder.getFromLocation(latitude, longitude, 1)

    return addresses?.takeIf { it.isNotEmpty() }?.get(0)?.getAddressLine(0) ?: "Unknown Location"
}




@Composable
fun GafitoLocation() {
    GafitoUserTheme {
        MainScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    GafitoLocation()
}