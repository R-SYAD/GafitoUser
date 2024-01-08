@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.gafitorsatpam.component.laporanComp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gafitouser.R
import com.example.gafitouser.ui.theme.Warning
import com.example.gafitouser.user.component.ui.theme.GafitoUserTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailLaporan() {
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
            Image(
                painter = painterResource(id = R.drawable.motor),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(16.dp)
                    .size(128.dp)
                    .clip(CircleShape)
            )
            TextField(
                value = "F 1 KRI",
                label = { Text(text = "Nomor Polisi") },
                enabled = false,
                onValueChange = {},
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )
            TextField(
                value = "Honda Vario 1000 CC",
                label = { Text(text = "Merek Kendaraan") },
                enabled = false,
                onValueChange = {},
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )
            TextField(
                value = "Maroon Metalic",
                label = { Text(text = "Warna Kendaraan") },
                enabled = false,
                onValueChange = {},
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )
            TextField(
                value = "19/10/2023, 04:43 PM",
                label = { Text(text = "Tanggal Laporan") },
                enabled = false,
                onValueChange = {},
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()

            )
            TextField(
                value = "Laporan Kunci Tertinggal",
                label = { Text(text = "Deskripsi Laporan") },
                enabled = false,
                onValueChange = {},
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()

            )
            Row(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(Warning),
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Ubah")
                }
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(Color.Red),
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Hapus")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailLaporaPreview() {
    GafitoUserTheme {
        DetailLaporan()
    }
}