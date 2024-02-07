package com.jetpackcomposethe.thread.ItemView

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.jetpackcomposethe.thread.R
import com.jetpackcomposethe.thread.model.ThreadModel
import com.jetpackcomposethe.thread.model.UserModel
import com.jetpackcomposethe.thread.utils.SharedPref


@Composable
fun ThreadItem(
    thread:ThreadModel,
    users:UserModel,
    navController: NavController,
    userID:String
) {


    Column {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val(userImage,userName,data,time,title,image)=createRefs()

            // User Logo

            Image(
                painter = rememberAsyncImagePainter(
                    model =  users.imgUrl
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(45.dp)
                    .clip(shape = CircleShape)
                    .constrainAs(userImage) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .clickable { /* Handle click if needed */ }
            )

            // Username Display

            Text(
                text = users.username,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Monospace, modifier = Modifier.constrainAs(userName) {
                    top.linkTo(userImage.top)
                    start.linkTo(userImage.end, margin = 12.dp)
                    bottom.linkTo(userImage.bottom, margin = 8.dp)
                }
            )

            // Title

            Text(
                text =thread.thread,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Monospace, modifier = Modifier.constrainAs(title) {
                    top.linkTo(userName.bottom, margin = 8.dp)
                    start.linkTo(userName.start)
                }
            )

            // Image

            if(thread.image!=""){

                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .height(200.dp)
                        .constrainAs(image) {
                            top.linkTo(title.bottom, margin = 8.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }

                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = thread.image,
                            builder = {
                                crossfade(true)
                            }
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.Crop, // Fill the bounds of the Box
                        modifier = Modifier.fillMaxSize() // Fill the entire Box
                    )
                }

            }



        }

        Divider(color = Color.LightGray, thickness = 1.dp)
    }


}