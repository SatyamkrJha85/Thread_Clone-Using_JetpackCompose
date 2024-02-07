package com.jetpackcomposethe.thread.screens

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.jetpackcomposethe.thread.R
import com.jetpackcomposethe.thread.navigation.Routes
import com.jetpackcomposethe.thread.viewmodel.AuthViewModel


@Composable
fun Register(navController: NavHostController) {


    val authViewModel: AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)

    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }

    var isLoading1 by rememberSaveable { mutableStateOf(false) }


    var name by rememberSaveable {
        mutableStateOf("")
    }

    var bio by rememberSaveable {
        mutableStateOf("")
    }

    var username by rememberSaveable {
        mutableStateOf("")
    }

    val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        android.Manifest.permission.READ_MEDIA_IMAGES
    } else {
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val context = LocalContext.current

    var imageUri by rememberSaveable {
        mutableStateOf<Uri?>(null)
    }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }

    val permissionlauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {

            } else {

            }
        }


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
            modifier = Modifier.padding(bottom = 8.dp),
            text = "Register Here",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )

        // Profile
        Spacer(modifier = Modifier.height(10.dp))

        Image(
            modifier = Modifier
                .size(98.dp)
                .clip(shape = CircleShape)
                .background(Color.DarkGray)
                .border(2.dp, Color.DarkGray, shape = CircleShape)
                .clickable {

                    val isGranted = ContextCompat.checkSelfPermission(
                        context, permissionToRequest
                    ) == PackageManager.PERMISSION_GRANTED

                    if (isGranted) {
                        launcher.launch("image/*")
                    } else {
                        permissionlauncher.launch(permissionToRequest)
                    }

                }, contentScale = ContentScale.Crop,
            painter = if (imageUri == null) painterResource(id = R.drawable.man)
            else rememberAsyncImagePainter(model = imageUri),
            contentDescription = null
        )

        //Name

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 20.dp),
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Enter Your Name") },
            leadingIcon = { Icon(imageVector = Icons.Rounded.Person, contentDescription = null) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )

        // Username

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 10.dp),
            value = username,
            onValueChange = { username = it },
            label = { Text(text = "Enter Your Username") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.AccountCircle,
                    contentDescription = null
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )

        // Bio

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 10.dp),
            value = bio,
            onValueChange = { bio = it },
            label = { Text(text = "Enter Your Bio") },
            leadingIcon = { Icon(imageVector = Icons.Rounded.Create, contentDescription = null) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 10.dp),
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
                .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 20.dp),
            value = password,
            onValueChange = { password = it },
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

                if (name.isEmpty() || email.isEmpty() || bio.isEmpty() || password.isEmpty() || username.isEmpty() || imageUri == null) {
                    Toast.makeText(context, "Please fill all details", Toast.LENGTH_LONG)
                        .show()
                } else {
                    authViewModel.Register(
                        email,
                        password,
                        name,
                        bio,
                        username,
                        imageUri!!,
                        context
                    )
                    isLoading1 = true


                    Toast.makeText(context,"Register Successful",Toast.LENGTH_SHORT).show()

                }
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp, top = 10.dp)
        ) {
            Text(
                text = "Register",
                fontSize = 19.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.SansSerif
            )
        }
        TextButton(onClick = {
            navController.navigate(Routes.Login.routes) {
                popUpTo(0)
                launchSingleTop = true
            }
        }) {
            Text(
                text = "Already have a account? Login here",
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.SansSerif
            )

        }

    }

    if (isLoading1) {
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