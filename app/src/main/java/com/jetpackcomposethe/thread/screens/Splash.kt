package com.jetpackcomposethe.thread.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import com.google.firebase.auth.FirebaseAuth
import com.jetpackcomposethe.thread.R
import com.jetpackcomposethe.thread.navigation.Routes
import kotlinx.coroutines.delay


@Composable
fun Splash(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(120.dp),
            painter = painterResource(id = R.drawable.threads),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(20.dp),
            text = "Developed by Satyam Jha",
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            fontFamily = FontFamily.Companion.SansSerif
        )

    }

    LaunchedEffect(true) {
        delay(1000)

        if(FirebaseAuth.getInstance().currentUser!=null){
            navController.navigate(Routes.BottomNav.routes) {
                popUpTo(0)
                launchSingleTop=true
            }
        }
        else{
            navController.navigate(Routes.Login.routes) {
                popUpTo(0)
                launchSingleTop=true
            }
        }

    }
}