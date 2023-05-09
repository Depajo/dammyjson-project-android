package fi.tuni.dammyjson

import FetchTools
import User
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.navigation.NavController

@Composable
fun UserScreen(navController: NavController, userId: Int? = null) {
    val fetch = FetchTools()
    var data: User? by remember { mutableStateOf(null) }

    if (userId != null) {
        fetch.getData("https://dummyjson.com/users/${userId}", {
            println(it)
            data = fetch.parseOneUserData(it)
        }, {
            println(it)
        })
    }
    if (data != null) {
        UserFound(navController, data!!)
    } else {
        UserNotFound(navController)
    }
}

@Composable
fun UserNotFound(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            CircularProgressIndicator()
            Button(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Text("Back")
            }
        }
    }

}

@Composable
fun UserFound(navController: NavController, user: User) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text("Hello ${user.lastName}")
            Button(
                onClick = {
                    navController.navigate("home")
                }
            ) {
                Text("Back")
            }
        }

    }

}