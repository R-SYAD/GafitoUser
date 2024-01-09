package com.example.gafitouser.user.component

import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gafitouser.data.LaporanData
import com.example.gafitouser.main.CommonProgressSpinner
import com.example.gafitouser.main.LaporanImageCard
import com.example.gafitouser.ui.theme.Warning
import java.text.SimpleDateFormat


@Composable
fun PhotoLaporan(imageUrl: String?) {
    Box {
        LaporanImageCard(
            laporanImage = imageUrl,
            modifier = Modifier
                .padding(8.dp)
                .size(64.dp)
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = CircleShape
                ),
        )
    }
}

@Composable
fun ListLaporanView(
    modifier: Modifier,
    isContextLoading: Boolean,
    laporansLoading: Boolean,
    laporans: List<LaporanData>,
    onLaporanClick: (LaporanData) -> Unit
) {
    if (laporansLoading) {
        CommonProgressSpinner()
    } else if (laporans.isEmpty()) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (!isContextLoading) Text(text = "Tidak ada laporan tersedia", color=colorScheme.onSecondary)
        }
    } else {
        LazyColumn(modifier = Modifier.padding(vertical = 4.dp)){
            items(items = laporans) { laporan ->
                CardLaporanView(laporan = laporan, onLaporanClick = onLaporanClick)
            }
        }
    }
}

//data class LaporanRow(

@Composable
fun CardLaporanView(
    laporan: LaporanData,
    modifier: Modifier = Modifier,
    onLaporanClick: (LaporanData) -> Unit
) {

    val laporanImageDisplay = laporan?.laporanImage ?: ""
    val nomorPolisiDisplay = laporan?.nomorPolisi ?: ""
    val merekDisplay = laporan?.merek ?: ""
    val warnaDisplay = laporan?.warna ?: ""
    val tanggalLaporan = laporan?.time ?: ""

    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
    val tanggal = simpleDateFormat.format(tanggalLaporan)

    val expanded = rememberSaveable { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        if (expanded.value) 4.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    val timePadding by animateDpAsState(
        if (expanded.value) extraPadding + 40.dp else 0.dp
    )
    Box(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            .clickable {
                expanded.value = !expanded.value
            }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            PhotoLaporan(imageUrl = laporanImageDisplay)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(
                    text = nomorPolisiDisplay,
                    modifier = modifier,
                    color = colorScheme.onSecondary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = merekDisplay,
                    modifier = modifier,
                    color =  colorScheme.onSecondary
                )
                if (expanded.value) {
                    Text(
                        text = "$warnaDisplay",
                        color =  colorScheme.onSecondary,
                    )
                    Text(
                        text = "$tanggal",
                        color =  colorScheme.onSecondary,
                    )
                    Spacer(modifier = Modifier.padding(top = 16.dp))
                    Button(
                        onClick = { laporan?.let { laporan -> onLaporanClick(laporan)
                            Log.d("laporan", "laporan nya adalah $laporan")}},
                        colors = ButtonDefaults.buttonColors(Warning),
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Details")
                    }
                }
            }
        }
        if (!expanded.value) {
            Text(
                text = "$tanggal",
                color =  colorScheme.onSecondary,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(16.dp)
//                .padding(bottom = timePadding.coerceAtLeast(0.dp))
            )
        }
    }
}

