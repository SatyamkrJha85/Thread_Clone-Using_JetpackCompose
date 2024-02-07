package com.jetpackcomposethe.thread.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.jetpackcomposethe.thread.ItemView.ThreadItem
import com.jetpackcomposethe.thread.R
import com.jetpackcomposethe.thread.model.UserModel
import com.jetpackcomposethe.thread.navigation.Routes
import com.jetpackcomposethe.thread.utils.SharedPref
import com.jetpackcomposethe.thread.viewmodel.AuthViewModel
import com.jetpackcomposethe.thread.viewmodel.UserViewModel


@Composable
fun OtherUser(navHostController: NavHostController, uId: String) {

    val authViewModel: AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)


    val userViewModel: UserViewModel = viewModel()
    val threads by userViewModel.threads.observeAsState(null)
    val users by userViewModel.users.observeAsState(null)
    val followerList by userViewModel.followerList.observeAsState(null)
    val followingList by userViewModel.followingList.observeAsState(null)

    var currentUserId=""
    if(FirebaseAuth.getInstance().currentUser!=null)
        currentUserId=FirebaseAuth.getInstance().currentUser!!.uid


    val context = LocalContext.current



    userViewModel.fetchThreads(uId)
    userViewModel.fetchUser(uId)
    userViewModel.getFollowers(uId)
    userViewModel.getFollowing(uId)





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
                    text = users!!.name,
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
                        model = users!!.imgUrl ?: R.drawable.man
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(shape = CircleShape)
                        .constrainAs(logo) {
                            top.linkTo(parent.top)
                            start.linkTo(text.end, margin = 100.dp)
                        }
                        .clickable { /* Handle click if needed */ }
                )

                // Username Display

                Text(
                    text = users!!.username,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.Default, modifier = Modifier.constrainAs(username) {
                        top.linkTo(text.bottom)
                        start.linkTo(parent.start)
                    }
                )

                Text(
                    text = users!!.bio,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.Monospace, modifier = Modifier.constrainAs(bio) {
                        top.linkTo(username.bottom)
                        start.linkTo(parent.start)
                    }
                )

                Text(
                    text = "${followerList?.size} Followers",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Monospace, modifier = Modifier.constrainAs(followers) {
                        top.linkTo(bio.bottom)
                        start.linkTo(parent.start)
                    }
                )

                Text(
                    text = "${followingList?.size} Following",
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

                    if(currentUserId!="")
                    userViewModel.followUsers(uId,currentUserId)

                }) {
                    Text(text = if(followerList!=null && followerList!!.isNotEmpty() && followerList!!.contains(currentUserId))"Following" else "Follow")
                }


            }
        }

        if (threads != null && users != null) {


            items(threads ?: emptyList()) { pair ->

                ThreadItem(
                    thread = pair,
                    users = users!!,
                    navController = navHostController,
                    userID = SharedPref.getUserName(context)
                )
            }

        }
    }


}