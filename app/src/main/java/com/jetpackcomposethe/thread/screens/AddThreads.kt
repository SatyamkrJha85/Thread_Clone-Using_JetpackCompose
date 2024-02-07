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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.jetpackcomposethe.thread.R
import com.jetpackcomposethe.thread.navigation.Routes
import com.jetpackcomposethe.thread.ui.theme.PurpleGrey40
import com.jetpackcomposethe.thread.utils.SharedPref
import com.jetpackcomposethe.thread.viewmodel.AddThreadViewModel


//@Preview(showSystemUi = true)
@Composable
fun AddThreads(navHostController: NavHostController) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        val (crossPic, text, logo, username, editText, attachmedia, replyText, button, imageBox) = createRefs()

        val context = LocalContext.current

        val threadViewModel:AddThreadViewModel= viewModel()
        val isPosted by threadViewModel.isposted.observeAsState(null)

        val thread = remember {
            mutableStateOf("")
        }
        var loading by remember { mutableStateOf(false) }


        var imageUri by rememberSaveable {
            mutableStateOf<Uri?>(null)
        }
        val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            android.Manifest.permission.READ_MEDIA_IMAGES
        } else {
            android.Manifest.permission.READ_EXTERNAL_STORAGE
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

        // launcher effect

        LaunchedEffect(isPosted) {
            isPosted?.let {
                loading = false // Reset loading state
                if (it) {
                    thread.value = ""
                    imageUri = null
                    Toast.makeText(context, "Thread Added", Toast.LENGTH_SHORT).show()
                    navHostController.navigate(Routes.Home.routes) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                } else {
                    Toast.makeText(context, "Failed to add thread", Toast.LENGTH_SHORT).show()
                }
            }
        }


        // Cut Icon

        Icon(imageVector = Icons.Rounded.Close, contentDescription = null,
            modifier = Modifier
                .constrainAs(crossPic) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .clickable {
                    navHostController.navigate(Routes.Home.routes){
                        popUpTo(0){
                            inclusive=true
                        }
                        launchSingleTop=true

                    }
                })

        // Text

        Text(
            text = "Add Threads",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily.SansSerif, modifier = Modifier.constrainAs(text) {
                top.linkTo(crossPic.top)
                start.linkTo(crossPic.end, margin = 15.dp)
                bottom.linkTo(crossPic.bottom)
            }
        )

        // User Logo

        Image(
            painter = rememberAsyncImagePainter(
                model = SharedPref.getImage(context) ?: R.drawable.man
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(45.dp)
                .clip(shape = CircleShape)
                .constrainAs(logo) {
                    top.linkTo(text.bottom, margin = 20.dp)
                    start.linkTo(parent.start)
                }
                .clickable { /* Handle click if needed */ }
        )

        // Username Display

        Text(
            text = SharedPref.getUserName(context),
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.Monospace, modifier = Modifier.constrainAs(username) {
                top.linkTo(logo.top)
                start.linkTo(logo.end, margin = 12.dp)
                bottom.linkTo(logo.bottom)
            }
        )

        Basictextfieldwithhint(
            hint = "Start a Thread....",
            value = thread.value,
            onValueChange = { thread.value = it },


            modifier = Modifier
                .constrainAs(editText) {
                    top.linkTo(username.bottom)
                    start.linkTo(username.start)
                    end.linkTo(parent.end)
                }
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth())

        // attach icon

        if(imageUri==null){
            Image(painter = painterResource(id = R.drawable.attachment), contentDescription = null,
                modifier = Modifier
                    .constrainAs(attachmedia) {
                        top.linkTo(editText.bottom)
                        start.linkTo(editText.start)
                    }
                    .clickable {


                        val isGranted = ContextCompat.checkSelfPermission(
                            context, permissionToRequest
                        ) == PackageManager.PERMISSION_GRANTED

                        if (isGranted) {
                            launcher.launch("image/*")
                        } else {
                            permissionlauncher.launch(permissionToRequest)
                        }
                    })
        }
        else{
            Box(modifier = Modifier
                .background(Color.Transparent)
                .border(2.dp, Color.DarkGray, shape = RoundedCornerShape(5.dp))
                .padding(10.dp)
                .padding(end = 5.dp)
                .constrainAs(imageBox) {
                    top.linkTo(editText.bottom)
                    start.linkTo(editText.start)
                    end.linkTo(parent.end)
                }
                .height(250.dp)){

                Image(
                    painter = rememberAsyncImagePainter(
                        model = imageUri),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,

                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clip(shape = RoundedCornerShape(5.dp))
                )

                Icon(imageVector = Icons.Rounded.Close, contentDescription ="Remove image"
                , modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clickable {
                            imageUri = null
                        }
                )

            }
        }

        Text(
            text = "Anyone can reply",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.Serif, modifier = Modifier.constrainAs(replyText) {
                start.linkTo(parent.start, margin = 12.dp)
                bottom.linkTo(parent.bottom, margin = 12.dp)
            }
        )

        TextButton(onClick = {
            if (imageUri == null) {
                loading = true // Set loading state

                threadViewModel.saveData(thread.value, FirebaseAuth.getInstance().currentUser!!.uid, "")
            } else {
                loading = true // Set loading state
                threadViewModel.saveImage(thread.value, imageUri!!,FirebaseAuth.getInstance().currentUser!!.uid)
            }
        }, modifier = Modifier.constrainAs(button){
            end.linkTo(parent.end, margin = 12.dp)
            bottom.linkTo(parent.bottom, margin = 12.dp)
        }

        ) {
            if (loading) {
                CircularProgressIndicator(modifier = Modifier.size(25.dp).border(1.dp, Color.DarkGray
                , shape = CircleShape)) // Show loading indicator
            } else {
                Text(
                    text = "Post",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.Serif
                )
            }
        }

    }

}

@Composable
fun Basictextfieldwithhint(
    hint: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier
) {
    Box(modifier = modifier) {
        if (value.isEmpty()) {
            Text(text = hint)
        }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                fontWeight = FontWeight.SemiBold,
                color = PurpleGrey40
            )
        )
    }

}