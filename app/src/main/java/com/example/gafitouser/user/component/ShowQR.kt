package com.example.gafitouser.user.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gafitouser.R
import com.example.gafitouser.user.component.ui.theme.GafitoUserTheme


@Composable
fun ProfileButton(onClick: () -> Unit) {
    Icon(
        painter = painterResource(id = R.drawable.profile_icon), // Replace with your drawable resource ID
        contentDescription = null, // Provide a proper content description if needed
        modifier = Modifier
            .padding(16.dp)
            .size(48.dp) // Adjust the size as needed
    )
}
@Composable
fun NotificationButton(onClick: () -> Unit) {
    Icon(
        painter = painterResource(id = R.drawable.notification_icon), // Replace with your drawable resource ID
        contentDescription = null, // Provide a proper content description if needed
        modifier = Modifier
            .padding(16.dp)
            .size(48.dp) // Adjust the size as needed
    )
}

@Composable
fun QR() {

    Image(
        painter = painterResource(id = R.drawable.qr_code),
        contentDescription = null,
        modifier = Modifier.size(360.dp)
    )
}

@Composable
fun ShowQrPage() {

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ProfileButton {}
            Gafito()
            NotificationButton {}
        }
        Box(
            modifier = Modifier
                .padding(8.dp)
                .border(1.dp, Color.Black, shape = RoundedCornerShape(32.dp))
                .background(color = Color.White, shape = RoundedCornerShape(32.dp))
        )
        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
                    .padding(32.dp)
            ) {
                PhotoProfile()
                Spacer(modifier = Modifier.height(24.dp))
                QR()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun QRPreview() {
    GafitoUserTheme {
        ShowQrPage()
    }
}