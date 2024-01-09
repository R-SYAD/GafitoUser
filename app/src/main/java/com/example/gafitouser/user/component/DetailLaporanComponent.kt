@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.gafitorsatpam.component.laporanComp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gafitouser.GafitoViewModel
import com.example.gafitouser.data.LaporanData
import com.example.gafitouser.main.CommonImage
import com.example.gafitouser.user.component.ui.theme.GafitoUserTheme
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailLaporan(navController: NavController, vm: GafitoViewModel, laporan: LaporanData) {
    val waktuLaporan = laporan?.time ?: ""

    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
    val waktu = simpleDateFormat.format(waktuLaporan)


    Box(
        //            color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(8.dp)
            )

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)

        ) {
            CommonImage(
                data = laporan.laporanImage,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(16.dp)
                    .size(128.dp)
                    .clip(CircleShape)
            )
            TextField(
                value = laporan.nomorPolisi ?: "",
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onSecondary),
                label = { Text(text = "Nomor Polisi", color =  MaterialTheme.colorScheme.onSecondary) },
                enabled = false,
                onValueChange = {},
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )
            TextField(
                value = laporan.merek ?: "",
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onSecondary),
                label = { Text(text = "Merek Kendaraan", color =  MaterialTheme.colorScheme.onSecondary) },
                enabled = false,
                onValueChange = {},
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )
            TextField(
                value = laporan.warna ?: "",
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onSecondary),
                label = { Text(text = "Warna Kendaraan", color =  MaterialTheme.colorScheme.onSecondary) },
                enabled = false,
                onValueChange = {},
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )
            TextField(
                value = waktu,
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onSecondary),
                label = { Text(text = "Tanggal Laporan", color =  MaterialTheme.colorScheme.onSecondary) },
                enabled = false,
                onValueChange = {},
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()

            )
            TextField(
                value = laporan.description ?: "",
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onSecondary),
                label = { Text(text = "Deskripsi Laporan", color =  MaterialTheme.colorScheme.onSecondary) },
                enabled = false,
                onValueChange = {},
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailLaporaPreview() {
    GafitoUserTheme {
    }
}