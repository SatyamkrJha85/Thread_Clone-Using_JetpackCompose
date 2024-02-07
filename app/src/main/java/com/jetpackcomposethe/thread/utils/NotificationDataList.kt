package com.jetpackcomposethe.thread.utils

import com.jetpackcomposethe.thread.R
import com.jetpackcomposethe.thread.model.NotificationData

class NotificationDataList {

    val notifications = listOf(
        NotificationData(
            day = "Last 7 Days",
            pic = R.drawable.pic1,
            name = "Praksh Kant",
            time = "32h Ago"
        ),
        NotificationData(
            day = "Yesterday",
            pic = R.drawable.me,
            name = "Alice Smith",
            time = "1h Ago"
        ),

        NotificationData(
            day = "Today",
            pic = R.drawable.satya,
            name = "Alice Smith",
            time = "13h Ago"
        ),
        NotificationData(
            day = "Last month",
            pic = R.drawable.logo,
            name = "John Weath",
            time = "14h Ago"
        ),
        NotificationData(
            day = "Last month",
            pic = R.drawable.threads,
            name = "Somya das",
            time = "21h Ago"
        ),
        NotificationData(
            day = "Last month",
            pic = R.drawable.khushi,
            name = "Khushi Jaiswal",
            time = "6h Ago"
        )
    )
}