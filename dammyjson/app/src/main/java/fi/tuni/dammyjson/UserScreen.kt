package fi.tuni.dammyjson

import FetchTools
import User
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun UserScreen(navController: NavController, userId: Int? = null) {
    // Fetch data from server
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

    // Show data if it is not null else show loading screen
    if (data != null) {
        UserFound(navController, data!!)
    } else {
        ProcessView(navController)
    }
}

@Composable
fun ProcessView(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun UserFound(navController: NavController, user: User) {
    Box(modifier = Modifier.fillMaxSize()) {
        IconButton(Icons.Filled.ArrowBack, "Back", Modifier.align(Alignment.TopStart)) {
            navController.popBackStack()
        }
        IconButton(Icons.Filled.Edit, "Back", Modifier.align(Alignment.TopEnd) ) {
            navController.navigate("AddUser/${user.id}")
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

//Create own textarea where you can put label and text
@Composable
fun TextAreaWithLabel(label: String, text: String) {
    Column(modifier = Modifier
        .padding(10.dp)
        .background(
            if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray,
            RoundedCornerShape(10.dp)
        )
        .fillMaxWidth()
        .padding(10.dp))
    {
        Text(label, fontWeight = FontWeight.Bold, modifier = Modifier.padding(2.dp))
        Text(text, modifier = Modifier.padding(2.dp))
    }
}


@Composable
fun IconButton(icon: ImageVector, description: String, modifier: Modifier, action: () -> Unit) {
        FloatingActionButton(
            modifier = modifier,
            backgroundColor = if (isSystemInDarkTheme()) Color.Transparent else Color.White,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp
            ),
            onClick = {
                action()
            }
        ) {
            Icon(icon, contentDescription = description)
        }

}