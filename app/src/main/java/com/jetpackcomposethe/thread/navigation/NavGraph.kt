package com.jetpackcomposethe.thread.navigation

import Home
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jetpackcomposethe.thread.screens.AddThreads
import com.jetpackcomposethe.thread.screens.BottomNav
import com.jetpackcomposethe.thread.screens.Login
import com.jetpackcomposethe.thread.screens.Notification
import com.jetpackcomposethe.thread.screens.OtherUser
import com.jetpackcomposethe.thread.screens.Profile
import com.jetpackcomposethe.thread.screens.Register
import com.jetpackcomposethe.thread.screens.Search
import com.jetpackcomposethe.thread.screens.Splash

@Composable
fun NavGraph(navController: NavHostController){

    NavHost(navController = navController, startDestination = Routes.Splash.routes){
        composable(Routes.Splash.routes){
            Splash(navController = navController)
        }
        composable(Routes.Home.routes){
            Home(navController)
        }
        composable(Routes.Notification.routes){
            Notification()
        }
        composable(Routes.Search.routes){
            Search(navController)
        }
        composable(Routes.Profile.routes){
            Profile(navController)
        }
        composable(Routes.AddThread.routes){
            AddThreads(navController)
        }
        composable(Routes.BottomNav.routes){
            BottomNav(navController)
        }
        composable(Routes.Login.routes){
            Login(navController)
        }
        composable(Routes.Register.routes){
            Register(navController)
        }

        composable(Routes.OtherUsers.routes) { backStackEntry ->
            val data = backStackEntry.arguments?.getString("data")
            OtherUser(navController, data!!)
        }
    }
}
