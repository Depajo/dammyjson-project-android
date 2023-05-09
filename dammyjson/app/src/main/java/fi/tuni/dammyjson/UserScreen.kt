package fi.tuni.dammyjson

import FetchTools
import User
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun UserScreen(navController: NavController, userId: Int? = null) {
    val fetch = FetchTools()
    val data: User? = null

    if (userId != null) {
        fetch.getData("https://dummyjson.com/users/${userId}", {
            println(it)

        }, {
            println(it)
        })
    }
    if (data != null) {
        UserFound(navController, data)
    } else {
        UserNotFound(navController)
    }
}
@Composable
fun UserNotFound(navController: NavController) {
    Column() {
        Text("User not found")
        Button(
            onClick = {
                // Tee jotain uuden kohteen kanssa
                navController.popBackStack()
            }
        ) {
            Text("Back")
        }
    }

}

@Composable
fun UserFound(navController: NavController, userData: User) {
    Column {
        Text("Hello")
        Button(
            onClick = {
                // Tee jotain uuden kohteen kanssa
                navController.navigate("home")
            }
        ) {
            Text("Back")
        }
    }

}