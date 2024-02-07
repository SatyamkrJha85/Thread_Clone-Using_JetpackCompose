import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.jetpackcomposethe.thread.ItemView.ThreadItem
import com.jetpackcomposethe.thread.R
import com.jetpackcomposethe.thread.navigation.Routes
import com.jetpackcomposethe.thread.utils.SharedPref
import com.jetpackcomposethe.thread.viewmodel.AuthViewModel
import com.jetpackcomposethe.thread.viewmodel.HomeViewModel

@Composable
fun Home(navController: NavController) {
    val homeViewModel: HomeViewModel = viewModel()
    val threadAndUsers by homeViewModel.threadsAndUsers.observeAsState(null)
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.thpng),
            contentDescription = null,
            modifier = Modifier.size(70.dp).padding(10.dp)
        )

        LazyColumn {
            items(threadAndUsers ?: emptyList()) { pairs ->
                ThreadItem(
                    thread = pairs.first,
                    users = pairs.second,
                    navController,
                    FirebaseAuth.getInstance().currentUser!!.uid
                )
            }
        }

    }


}
