package com.jetpackcomposethe.thread.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.jetpackcomposethe.thread.navigation.Routes
import com.jetpackcomposethe.thread.viewmodel.AuthViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Login(navController: NavController) {

    val authViewModel: AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState()


    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }

    var isLoading by rememberSaveable { mutableStateOf(false) }



    val context = LocalContext.current

    LaunchedEffect(firebaseUser) {
        if (firebaseUser != null) {
            navController.navigate(Routes.BottomNav.routes) {
                popUpTo(0)
                launchSingleTop = true
            }

        }
    }

    // for error

    LaunchedEffect(firebaseUser) {
        if (firebaseUser != null) {
            navController.navigate(Routes.BottomNav.routes) {
                popUpTo(0)
                launchSingleTop = true
            }

        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 20.dp),
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Enter Your Email") },
            leadingIcon = { Icon(imageVector = Icons.Rounded.Email, contentDescription = null) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp), value = password, onValueChange = { password = it },
            label = { Text(text = "Enter Your Password") },
            leadingIcon = { Icon(imageVector = Icons.Rounded.Lock, contentDescription = null) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            singleLine = true

        )

        ElevatedButton(
            onClick = {
                isLoading = true

                authViewModel.login(email, password, context)
                Toast.makeText(context,"Login Successful",Toast.LENGTH_SHORT).show()

            }, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp, top = 10.dp)
        ) {
            Text(
                text = "Login",
                fontSize = 19.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.SansSerif
            )
        }
        TextButton(onClick = {
            navController.navigate(Routes.Register.routes) {
                popUpTo(0)
            }
        }) {
            Text(
                text = "New User? Create Account",
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.SansSerif
            )

        }

    }

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

}