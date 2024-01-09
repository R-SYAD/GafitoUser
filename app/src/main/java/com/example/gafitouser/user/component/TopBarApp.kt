package com.example.gafitouser.user.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gafitouser.user.component.ui.theme.GafitoUserTheme

@Composable
fun TopBarAtas(screen: String, navController: NavController) {
//    val navController = naturalOrder<>()
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
//            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clickable {
                        navController.popBackStack()
                    },
                tint = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.padding(end = 16.dp))
            Text(
                text = screen,
//                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}

@Preview
@Composable
fun TopBarAppPreview() {
    GafitoUserTheme {
        val navController = rememberNavController()
        TopBarAtas(screen = "Gafito", navController)
    }
}