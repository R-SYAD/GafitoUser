package com.example.gafitouser.user.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
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
import com.example.gafitouser.user.component.ui.theme.GafitoUserTheme

@Composable
fun PhotoProfile() {
    Image(
        painter = painterResource(id = R.drawable.motor),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clip(CircleShape)
            .size(100.dp)
    )
    Spacer(modifier = Modifier.height(32.dp))
}


@Composable
fun DetailProfil() {
    val data = listOf(
        "Nama" to "Cristiano Ronaldo",
        "NIM" to "2111527001",
        "Nomor Polisi" to "C 7 R",
        "Jenis Motor" to "Supra Bapak",
        "No Hp" to "08987654321"
    )
    Column {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp))
                .background(Color.White, shape = RoundedCornerShape(8.dp))
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
            ) {
                PhotoProfile()
                data.forEachIndexed { index, (label, value) ->
                    if (index > 0) {
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = label, color = Color.Black)
                        Text(text = value, color = Color.Black)
                    }
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun PhotoProfilePreview() {
    GafitoUserTheme {
        PhotoProfile()
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    GafitoUserTheme {
        DetailProfil()
    }
}