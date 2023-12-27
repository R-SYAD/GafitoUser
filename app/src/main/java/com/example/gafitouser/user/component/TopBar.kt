package com.example.gafitouser.user.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(8.dp)
    ) {
        Image(
            modifier = Modifier
                .size(80.dp),
            painter = painterResource(id = R.drawable.gafito_logo_text),
            contentDescription = null
        )
    }
}


