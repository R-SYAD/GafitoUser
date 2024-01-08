package com.example.gafitouser.user.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gafitouser.R
import com.example.gafitouser.user.component.ui.theme.GafitoUserTheme

@Preview
@Composable
fun TopBarPreview () {
    GafitoUserTheme {
        TopBar()
    }

}
@Composable
fun TopBar() {
    val isCondition by remember { mutableStateOf(true) }
    GafitoUserTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorScheme.primary)
                .padding(4.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {


                Image(
                    modifier = Modifier
                        .size(64.dp),
                    painter = painterResource(id = R.drawable.gafito_logo_text),
                    contentDescription = null
                )
            }

            Box(
                modifier = Modifier
                    .padding(20.dp)
                    .size(20.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Black, // Ganti warna sesuai kebutuhan
                        shape = CircleShape
                    )
                    .background(
                        if (isCondition) Color.Green else Color.Red,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                    .align(Alignment.CenterEnd)
            )
        }
    }

}




