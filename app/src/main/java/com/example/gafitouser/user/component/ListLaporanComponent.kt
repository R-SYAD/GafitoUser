package com.example.gafitouser.user.component

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gafitouser.R
import com.example.gafitouser.ui.theme.GafitoUserTheme
import com.example.gafitouser.ui.theme.Warning

@Composable
fun ListLaporanView(
    modifier: Modifier = Modifier,
    daftar: List<String> = List(15){ "Honda Vario 125" }
) {
    val mContext = LocalContext.current
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)){
        items(items = daftar) { name ->
            CardLaporanView(name = name)

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListLaporanPreview() {
    GafitoUserTheme {
        ListLaporanView()
    }
}

@Composable
fun CardLaporanView(name: String = "Lorem",modifier: Modifier = Modifier) {
    val expanded = rememberSaveable { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        if (expanded.value) 4.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    val timePadding by animateDpAsState(
        if (expanded.value) extraPadding + 40.dp else 0.dp
    )
    Box(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            .clickable {
                expanded.value = !expanded.value
            }
    ) {
        Row(
            modifier =  Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Image(
                painter = painterResource(R.drawable.motor),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .padding(8.dp)
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(
                    text = "BA 7777 SIU",
                    modifier = modifier,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$name",
                    modifier = modifier,
                    color = Color.White
                )
                if (expanded.value){
                    Text(
                        text = "00:00",
                        color = Color.White,
                    )
                    Spacer(modifier = Modifier.padding(top = 16.dp))
                    Button(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(Warning),
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Details")
                    }
                }
            }
        }
        if (!expanded.value){
            Text(
                text = "00:00",
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(16.dp)
//                .padding(bottom = timePadding.coerceAtLeast(0.dp))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardLaporanPreview() {
    GafitoUserTheme {
        CardLaporanView()
    }
}