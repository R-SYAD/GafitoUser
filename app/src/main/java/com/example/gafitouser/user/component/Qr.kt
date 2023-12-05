package com.example.gafitouser.user.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gafitouser.R
import com.example.gafitouser.user.component.ui.theme.GafitoUserTheme

@Composable
fun Qr() {

    Image(
        painter = painterResource(id = R.drawable.qr_code),
        contentDescription = null,
        modifier = Modifier.size(360.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun QrPreview() {
    GafitoUserTheme {
        Qr()
    }
}