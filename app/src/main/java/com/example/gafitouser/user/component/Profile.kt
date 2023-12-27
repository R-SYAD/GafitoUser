package com.example.gafitouser.user.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gafitouser.GafitoViewModel
import com.example.gafitouser.main.UserImageCard
import com.example.gafitouser.user.component.ui.theme.GafitoUserTheme

@Composable
fun PhotoProfile(imageUrl: String?, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(top = 16.dp)
            .clickable { onClick.invoke() }
    ) {
        UserImageCard(
            userImage = imageUrl, modifier = Modifier
                .padding(8.dp)
                .size(100.dp),
        )
    }
//        Image(
//            painter = painterResource(id = R.drawable.photoprofile),
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .clip(CircleShape)
//                .size(100.dp)
//        )

//    Spacer(modifier = Modifier.height(32.dp))
}


@Composable
fun DetailProfil(navController: NavController, vm: GafitoViewModel) {
    val userData = vm.userData.value

    val usernameDisplay = if (userData?.username == null) "" else "@${userData.username}"
    val nameDisplay = userData?.name ?: ""
    val noPolisiDisplay = userData?.noPolisi ?: ""
    val jenisMotorDisplay = userData?.jenisMotor ?: ""
    val noHpDisplay = userData?.noHP ?: ""

    val data = listOf(
        "Nama" to nameDisplay,
        "Username" to usernameDisplay,
        "Nomor Polisi" to noPolisiDisplay,
        "Jenis Motor" to jenisMotorDisplay,
        "No Hp" to noHpDisplay
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
                PhotoProfile(userData?.imageUrl){

                }
                data.forEachIndexed { index, (label, value) ->
                    if (index > 0) {
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = label)
                        Text(text = value)
                    }
                }
            }
        }
    }
}

//@Composable
//fun NavBack() {
//    Column(modifier = Modifier.padding(16.dp)) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .width(IntrinsicSize.Min)
//                .wrapContentWidth(align = Alignment.Start),
////            verticalAlignment = Alignment.CenterVertically,
////            horizontalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            Icon(
//                imageVector = Icons.Outlined.ArrowBack,
//                contentDescription = null,
//                modifier = Modifier.size(24.dp)
//            )
//            Text(
//                text = "Back",
//                modifier = Modifier
//                    .padding(start = 8.dp)
//            )
//        }
//    }
//}

@Preview(showBackground = true)
@Composable
fun PhotoProfilePreview() {
    GafitoUserTheme {
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    GafitoUserTheme {

    }
}