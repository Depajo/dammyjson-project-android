package fi.tuni.dammyjson

import FetchTools
import User
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


import fi.tuni.dammyjson.ui.theme.DammyjsonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DammyjsonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") { MainScreen(navController) }
                        composable("AddUser/{userId}") {
                            // Get the userId from the arguments
                            val userId: String? = it.arguments?.getString("userId")
                            // If userId is not null, then we are editing an existing user
                            if (userId != "null") {
                                // Convert the userId to an Int and pass it to the EditOrAddScreenSelector
                                EditOrAddScreenSelector(navController, userId?.toInt())
                            } else {
                                // If userId is null, then we are adding a new user
                                EditOrAddScreenSelector(navController, null)
                            }
                        }
                        composable("UserScreen/{userId}") {
                            val userId = it.arguments?.getString("userId")
                            UserScreen(navController, userId?.toInt())

                        }
                    }
                }
            }
        }
    }

    @Composable
    fun EditOrAddScreenSelector(navController: NavController, userId: Int?) {
        var data: User? by remember { mutableStateOf(null) }
        val fetch = FetchTools()

        if (userId != null) {
            fetch.getData("https://dummyjson.com/users/${userId}", {
                println(it)
                data = fetch.parseOneUserData(it)
            }, {
                println(it)
            })
        }

        // If userId is not null, then we are editing an existing user
        if (userId != null) {
            if (data == null) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            } else {
                // If data is not null, then we have the data for the user we are editing
                data?.let { Edit(it, navController) }
            }
        } else {
            // If userId is null, then we are adding a new user
            Add(navController)
        }

    }
}