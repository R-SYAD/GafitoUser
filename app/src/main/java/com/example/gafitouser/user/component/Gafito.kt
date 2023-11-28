package com.example.gafitouser.user.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.gafitouser.R
import com.example.gafitouser.user.component.ui.theme.GafitoUserTheme

@Composable
fun Gafito() {
    Image(painter = painterResource(id = R.drawable.gafito), contentDescription = null)
}

@Preview(showBackground = true)
@Composable
fun GafitoPreview() {
    GafitoUserTheme {
        Gafito()
    }
}