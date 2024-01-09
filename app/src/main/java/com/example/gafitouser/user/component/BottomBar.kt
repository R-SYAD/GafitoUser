package com.example.gafitouser.user.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gafitouser.main.navigateTo
import com.example.gafitouser.user.component.ui.theme.GafitoUserTheme
import com.example.gafitouser.user.models.BottomBarItem

@Composable
fun BottomBar(selectedItem: BottomBarItem, navController: NavController) {
    GafitoUserTheme {
        NavigationBar(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color.Gray.copy(alpha = 0.5f), // Warna abu-abu yang hampir putih
                    shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp, bottomStart = 0.dp, bottomEnd = 0.dp) // Atur radius sesuai kebutuhan
                )
                .background(color=colorScheme.primary),
            contentColor = colorScheme.primary// Or specific content color from GafitoUserTheme
        ) {
            for (item in BottomBarItem.values()) {
                val isSelected = item == selectedItem
                val iconColor = if (isSelected) colorScheme.tertiary else colorScheme.secondary

                Image(
                    painter = painterResource(id = item.icon),
                    contentDescription = item.text,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(4.dp)
                        .weight(1f)
                        .align(alignment = Alignment.CenterVertically)
                        .clickable {
                            navigateTo(navController, item.navDestination)
                        },
                    colorFilter = ColorFilter.tint(iconColor)
                )
            }
        }
    }
}


@Preview
@Composable
fun BottomBarPreview() {
    GafitoUserTheme {

    }

}