package hu.hm.ibm_jetcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import hu.hm.ibm_jetcompose.data.model.Item
import hu.hm.ibm_jetcompose.ui.screens.details.DetailScreen
import hu.hm.ibm_jetcompose.ui.screens.list.ListScreen
import hu.hm.ibm_jetcompose.ui.screens.list.ListViewModel
import hu.hm.ibm_jetcompose.ui.theme.Ibm_jetcomposeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Ibm_jetcomposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MyApp(viewModel)
                }
            }
        }
    }
}

@Composable
fun MyApp(viewModel: ListViewModel) {

    Ibm_jetcomposeTheme {
        Scaffold(
            content = {
                MainScreen(viewModel = viewModel)
                //ListScreen(viewModel)
            }
        )
    }
}


@Composable
fun MainScreen(viewModel: ListViewModel) {
    val navController = rememberNavController()

        NavHost(navController = navController, startDestination = NavScreen.Home.route) {
            composable(NavScreen.Home.route) {
                ListScreen(
                    viewModel = viewModel,
                    navController = navController
                )
            }
            composable(
                route = NavScreen.Detail.route,
                /* // Serializable doesnt support default values
                arguments = listOf(
                    navArgument(NavScreen.Detail.argument0) {type = NavType.SerializableType(Item::class.java)}
                ) */
            ) {
                val item = it.arguments?.getParcelable<Item>(NavScreen.Detail.argument0)
                if (item != null) {
                    DetailScreen(
                        item = item,
                        navController = navController
                    )
                }
            }
        }
    }

sealed class NavScreen(val route: String) {

    object Home : NavScreen("Home")
    object Detail : NavScreen("Detail") {
        const val argument0: String = "data"
    }
}