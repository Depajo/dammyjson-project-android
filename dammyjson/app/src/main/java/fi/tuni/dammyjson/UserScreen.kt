package fi.tuni.dammyjson

import FetchTools
import User
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

/**
 * UserScreen composable function. It is used to show the user data.
 * It fetches the data from the API. If the userId is not null, it will fetch the user with that id.
 *
 * @param navController is used to navigate to the edit user screen.
 * @param userId is the id of the user that is fetched from the API.
 *
 * @see UserFound
 */
@Composable
fun UserScreen(navController: NavController, userId: Int? = null) {
    // Fetch data from server
    val fetch = FetchTools()
    var data: User? by remember { mutableStateOf(null) }

    if (userId != null) {
        fetch.getData("https://dummyjson.com/users/${userId}", {
            println(it)
            data = fetch.parseOneUserDataToObject(it)
        }, {
            println(it)
        })
    }

    // Show data if it is not null else show loading screen
    if (data != null) {
        UserFound(navController, data!!)
    } else {
        ProcessView(navController)
    }
}

/**
 * UserFound is a composable function that shows the user data.
 *
 * @param navController is used to navigate to the edit user screen.
 * @param user is the user data that is shown.
 *
 * @see TextAreaWithLabel
 */
@Composable
fun UserFound(navController: NavController, user: User) {
    Box(modifier = Modifier.fillMaxSize()) {
        IconButton(Icons.Filled.ArrowBack, "Back", Modifier.align(Alignment.TopStart)) {
            navController.popBackStack()
        }
        IconButton(Icons.Filled.Edit, "Back", Modifier.align(Alignment.TopEnd) ) {
            navController.navigate("EditUser/${user.id}")
        }
        Column(modifier = Modifier
            .align(Alignment.Center)
            .padding(10.dp)) {
            TextAreaWithLabel(label = "Name:", text = "${user.firstName} ${user.lastName}")
            TextAreaWithLabel(label = "Phone:", text = user.phone)
            TextAreaWithLabel(label = "Email:", text = user.email)
            TextAreaWithLabel(label = "Age:", text = user.age.toString())
            TextAreaWithLabel(label = "Username:", text = user.username)
            TextAreaWithLabel(label = "Password:", text = user.password)

        }

    }
}
