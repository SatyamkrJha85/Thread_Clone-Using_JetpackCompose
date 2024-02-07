package com.jetpackcomposethe.thread.screens

import Home
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jetpackcomposethe.thread.model.BottomNavItem
import com.jetpackcomposethe.thread.navigation.Routes

@Composable
fun BottomNav(navController: NavHostController) {

    val navController1 = rememberNavController()

    Scaffold(
        bottomBar = { MyBottomBar(navController1) }
    ) { innerPadding ->

        NavHost(
            navController = navController1, startDestination = Routes.Home.routes,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Routes.Home.routes) {
                Home(navController1)
            }
            composable(Routes.Notification.routes) {
                Notification()
            }
            composable(Routes.Search.routes) {
                Search(navController)
            }
            composable(Routes.Profile.routes) {
                Profile(navController)
            }
            composable(Routes.AddThread.routes) {
                AddThreads(navController1)
            }
        }
    }
}


@Composable
fun MyBottomBar(navController1: NavHostController) {

    val backStackEntry = navController1.currentBackStackEntryAsState()

    val list = listOf(
        BottomNavItem(
            "Home",
            Routes.Home.routes,
            Icons.Rounded.Home
        ),
        BottomNavItem(
            "Search",
            Routes.Search.routes, // Corrected route for Search destination
            Icons.Rounded.Search
        ),
        BottomNavItem(
            "AddThread",
            Routes.AddThread.routes, // Corrected route for AddThread destination
            Icons.Rounded.Add
        ),
        BottomNavItem(
            "Notification",
            Routes.Notification.routes, // Corrected route for Notification destination
            Icons.Rounded.Notifications
        ),
        BottomNavItem(
            "Profile",
            Routes.Profile.routes, // Corrected route for Profile destination
            Icons.Rounded.Person
        )
    )

    BottomAppBar {
        list.forEach {
            val selected = it.route == backStackEntry?.value?.destination?.route

            NavigationBarItem(
                modifier = Modifier.background(color = Color.Transparent)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                selected = selected,
                onClick = {
                    navController1.navigate(it.route) {
                        popUpTo(navController1.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                alwaysShowLabel = true,
                icon = {
                    Icon(
                        modifier = Modifier.size(27.dp),
                        imageVector = it.icon,
                        contentDescription = it.title
                    )
                }
            )
        }
    }
}
