package com.jetpackcomposethe.thread.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.jetpackcomposethe.thread.ItemView.ThreadItem
import com.jetpackcomposethe.thread.ItemView.UserItem
import com.jetpackcomposethe.thread.viewmodel.HomeViewModel
import com.jetpackcomposethe.thread.viewmodel.SearchViewModel

@Composable
fun Search(navHostController: NavHostController){

    val searchViewModel: SearchViewModel = viewModel()
    val Userlist by searchViewModel.userList.observeAsState(null)
    var search by rememberSaveable {
        mutableStateOf("")
    }

    Column {

        Text(
            text = "Login",
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            value = search,
            onValueChange = { search = it },
            label = { Text(text = "Search user") },
            leadingIcon = { Icon(imageVector = Icons.Rounded.Search, contentDescription = null) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        if(Userlist!=null && Userlist!!.isNotEmpty()) {
            val filterItems = Userlist?.filter { it.name!!.contains(search, ignoreCase = true) }


            LazyColumn {
                items(filterItems!!) { pairs ->
                    UserItem(users = pairs, navController = navHostController)
                }
            }
        }
    }


}