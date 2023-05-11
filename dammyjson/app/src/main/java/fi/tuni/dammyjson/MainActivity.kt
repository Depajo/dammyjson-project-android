package fi.tuni.dammyjson

import FetchTools
import User
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
                        composable("AddUser") { Add(navController) }
                        composable("EditUser/{userId}") {
                            // Get the userId from the arguments
                            val userId: String? = it.arguments?.getString("userId")
                            // Convert the userId to an Int and pass it to the GetEditData function
                            GetEditData(navController, userId!!.toInt())
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
}