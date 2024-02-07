package com.jetpackcomposethe.thread.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpackcomposethe.thread.R
import com.jetpackcomposethe.thread.model.NotificationData
import com.jetpackcomposethe.thread.utils.NotificationDataList


@Composable
fun Notification() {
    val notificationDataList = NotificationDataList()

    // Access the notifications list
    val notifications = notificationDataList.notifications

    Column {
        uprpart()

        notifications .forEach { notification ->
            peopleshow(
                day = notification.day,
                pic = notification.pic,
                name = notification.name,
                time = notification.time
            )
        }
    }


}


@Composable
fun uprpart() {
    Column() {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(modifier = Modifier
                .size(30.dp),
                imageVector = Icons.Rounded.KeyboardArrowLeft, contentDescription =null )

            Text(text = "Notifications", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                 horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(modifier = Modifier
                    .size(30.dp),
                    imageVector = Icons.Rounded.ExitToApp, contentDescription =null )

            }
        }

        // Picture of Add icon
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(modifier = Modifier
                .size(40.dp)
                .border(2.dp, Color.DarkGray, shape = CircleShape)
                .padding(5.dp),
                painter = painterResource(id = R.drawable.addperson), contentDescription =null )
            Spacer(modifier = Modifier.width(15.dp))
            Column (
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                //   horizontalAlignment = Alignment.CenterHorizontally
            ){

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "Follow Reuest", fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.width(2.dp))
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "Approve or Ignore request  ", fontWeight = FontWeight.Normal)
                }

            }
        }




            }
        }



@Composable
fun peopleshow(day:String,pic:Int,name:String,time:String) {

    LazyColumn() {
        item {

            Text(
                text = day,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(10.dp)
            )

            // Picture of user
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                // horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.DarkGray, shape = CircleShape),
                    painter = painterResource(id = pic), contentDescription = null
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    //   horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(text = name, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.width(2.dp))
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(text = "Recently Posted a", fontWeight = FontWeight.Normal)
                    }


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(text = "Thread Tap to see ", fontWeight = FontWeight.Normal)
                        Text(text = time, fontWeight = FontWeight.Thin, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}



