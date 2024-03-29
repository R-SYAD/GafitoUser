package com.example.gafitouser.frontend.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gafitouser.DestinationScreen
import com.example.gafitouser.GafitoViewModel
import com.example.gafitouser.R
import com.example.gafitouser.main.CheckSignedIn
import com.example.gafitouser.main.CommonProgressSpinner
import com.example.gafitouser.main.navigateTo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(navController: NavController, vm: GafitoViewModel) {

    CheckSignedIn(navController = navController, vm = vm)

    val focus = LocalFocusManager.current

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .verticalScroll(
                rememberScrollState()
            ),
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            val noPolisiState = remember { mutableStateOf(TextFieldValue()) }
            val emailState = remember { mutableStateOf(TextFieldValue()) }
            val passState = remember { mutableStateOf(TextFieldValue()) }
            val jenisMotorState = remember { mutableStateOf(TextFieldValue()) }
            val nameState = remember { mutableStateOf(TextFieldValue()) }
            val noHPState = remember { mutableStateOf(TextFieldValue()) }

            Image(
                painter = painterResource(id = R.drawable.gafito),
                contentDescription = null,
                modifier = Modifier
                    .width(250.dp)
                    .padding(top = 16.dp)
                    .padding(8.dp)
            )
            Text(
                text = "Sign Up",
                modifier = Modifier.padding(8.dp),
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif
            )
            OutlinedTextField(
                value = noPolisiState.value,
                onValueChange = { noPolisiState.value = it },
                modifier = Modifier.padding(8.dp),
                label = { Text(text = "No Polisi") }
            )
            OutlinedTextField(
                value = emailState.value,
                onValueChange = { emailState.value = it },
                modifier = Modifier.padding(8.dp),
                label = { Text(text = "Email") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                )
            )
            OutlinedTextField(
                value = passState.value,
                onValueChange = { passState.value = it },
                modifier = Modifier.padding(8.dp),
                label = { Text(text = "Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            OutlinedTextField(
                value = nameState.value,
                onValueChange = { nameState.value = it },
                modifier = Modifier.padding(8.dp),
                label = { Text(text = "Nama") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                )
            )
            OutlinedTextField(
                value = jenisMotorState.value,
                onValueChange = { jenisMotorState.value = it },
                modifier = Modifier.padding(8.dp),
                label = { Text(text = "Merek Motor") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                )
            )
            OutlinedTextField(
                value = noHPState.value,
                onValueChange = { noHPState.value = it },
                modifier = Modifier.padding(8.dp),
                label = { Text(text = "No HP") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                )
            )
            Button(
                onClick = {
                    focus.clearFocus(force = true)
                          vm.onSignup(
                              noPolisiState.value.text,
                              emailState.value.text,
                              passState.value.text,
                              nameState.value.text,
                              jenisMotorState.value.text,
                              noHPState.value.text
                          )
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Sign Up")
            }
            Text(text = "Already have an account?", color = Color.Blue, modifier = Modifier
                .padding(8.dp)
                .clickable {
                    navigateTo(navController, DestinationScreen.Login)
                }
            )
        }

        val isLoading = vm.inProgress.value
        if (isLoading) {
            CommonProgressSpinner()
        }
    }
}