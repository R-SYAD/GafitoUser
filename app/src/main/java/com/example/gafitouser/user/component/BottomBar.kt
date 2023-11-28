package com.example.gafitouser.user.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.gafitouser.R
import com.example.gafitouser.user.component.ui.theme.GafitoUserTheme
import com.example.gafitouser.user.models.BottomBarItem

@Composable
fun BottomBar (modifier: Modifier = Modifier){
    NavigationBar {
        val bottomNavigation = listOf(
            BottomBarItem(
                title = stringResource(id = R.string.txt_data_parkir),
                icon = painterResource(id = R.drawable.report)
            ),
            BottomBarItem(
                title = stringResource(id = R.string.txt_scan_qr),
                icon = painterResource( id = R.drawable.qr_code_scan)
            ),
            BottomBarItem(
                title = stringResource(id = R.string.txt_laporan),
                icon = painterResource(id = R.drawable.danger)
            ),

        )
        bottomNavigation.map{
            NavigationBarItem(selected = it.title == bottomNavigation[0].title,
                onClick = { /*TODO*/ },
                icon = { Icon(painter = it.icon, contentDescription = it.title) },
                label= { Text(text = it.title)}
                )
        }

    }
}

@Preview
@Composable
fun BottomBarPreview () {
    GafitoUserTheme {
        BottomBar()
    }

}