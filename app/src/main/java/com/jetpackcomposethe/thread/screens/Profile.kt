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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.jetpackcomposethe.thread.ItemView.ThreadItem
import com.jetpackcomposethe.thread.R
import com.jetpackcomposethe.thread.model.UserModel
import com.jetpackcomposethe.thread.navigation.Routes
import com.jetpackcomposethe.thread.utils.SharedPref
import com.jetpackcomposethe.thread.viewmodel.AddThreadViewModel
import com.jetpackcomposethe.thread.viewmodel.AuthViewModel
import com.jetpackcomposethe.thread.viewmodel.UserViewModel

@Composable
fun Profile(navController: NavController) {

    val authViewModel: AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)


    val userViewModel: UserViewModel = viewModel()
    val threads by userViewModel.threads.observeAsState(null)
    val context = LocalContext.current

    val followerList by userViewModel.followerList.observeAsState(null)
    val followingList by userViewModel.followingList.observeAsState(null)

    var currentUserId=""
    if(FirebaseAuth.getInstance().currentUser!=null)
        currentUserId=FirebaseAuth.getInstance().currentUser!!.uid


if (currentUserId!="") {
    userViewModel.getFollowers(currentUserId)
    userViewModel.getFollowing(currentUserId)
}

    val user = UserModel(
        name = SharedPref.getName(context),
        username = SharedPref.getUserName(context),
        imgUrl = SharedPref.getImage(context)
    )

//    userViewModel.fetchThreads(FirebaseAuth.getInstance().currentUser!!.uid)

    FirebaseAuth.getInstance().currentUser?.uid?.let { userId ->
        userViewModel.fetchThreads(userId)
    }




    LazyColumn() {
        item {

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                val (text, logo, username, bio, followers, following, button) = createRefs()


                // Text

                Text(
                    text = SharedPref.getName(context),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold, fontStyle = FontStyle.Normal,
                    fontFamily = FontFamily.Default, modifier = Modifier.constrainAs(text) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
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
                        .size(100.dp)
                        .clip(shape = CircleShape)
                        .constrainAs(logo) {
                            top.linkTo(parent.top)
                            start.linkTo(text.end, margin = 90.dp)
                        }
                        .clickable { /* Handle click if needed */ }
                )

                // Username Display

                Text(
                    text = SharedPref.getUserName(context),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.Default, modifier = Modifier.constrainAs(username) {
                        top.linkTo(text.bottom)
                        start.linkTo(parent.start)
                    }
                )

                Text(
                    text = SharedPref.getBio(context),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.Monospace, modifier = Modifier.constrainAs(bio) {
                        top.linkTo(username.bottom)
                        start.linkTo(parent.start)
                    }
                )

                Text(
                    text = "${followerList!!.size} Followers",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Serif, modifier = Modifier.constrainAs(followers) {
                        top.linkTo(bio.bottom)
                        start.linkTo(parent.start)
                    }
                )

                Text(
                    text = "${followingList!!.size} Following",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Monospace, modifier = Modifier.constrainAs(following) {
                        top.linkTo(followers.bottom)
                        start.linkTo(parent.start)
                    }
                )

                ElevatedButton(modifier = Modifier.constrainAs(button) {
                    top.linkTo(following.bottom, margin = 10.dp)
                    start.linkTo(parent.start)
                }, onClick = {
                    authViewModel.logout()
                    navController.navigate(Routes.Login.routes) {
                        popUpTo(0)
                        launchSingleTop = true
                    }

                }) {
                    Text(text = "Logout")
                }


            }
        }

        items(threads ?: emptyList()) { pair ->

            ThreadItem(
                thread = pair,
                users = user,
                navController = navController,
                userID = SharedPref.getUserName(context)
            )
        }
    }


}