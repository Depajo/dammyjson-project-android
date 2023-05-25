package fi.tuni.dammyjson

import FetchTools
import User
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

/**
 * MainScreen is a composable function that creates the main screen of the app.
 * It contains the search field, the list of users and add user button.
 *
 * @param navControlle is used to navigate to the user screen.
 */
@Composable
fun MainScreen(navControlle: NavController) {
    var search by remember { mutableStateOf("") }
    Box {
        Column {
            SearchField() {
                search = it
            }
            CreateUserList(search, navControlle)

        }
        AddButton(navControlle, modifier = Modifier
            .padding(bottom = 20.dp, end = 20.dp)
            .align(Alignment.BottomEnd))
    }
}

/**
 * CreateUserList is a composable function that creates a list of users.
 * It also fetches the data from the API. If the search string is empty, it will fetch all users.
 * If the search string is not empty, it will fetch users that match the search string.
 *
 * @param search is a string that is used to search for users.
 * @param navController is used to navigate to the user screen.
 */
@Composable
fun CreateUserList(search: String, navController: NavController) {
    val fetch = FetchTools()
    var userData by remember { mutableStateOf(listOf<User>()) }
    var url = "https://dummyjson.com/users?limit=0"
    if (search.isNotEmpty()) {
        url = "https://dummyjson.com/users/search?q=$search"
    }
    fetch.getData(url = url, response = {
        Log.d("test", it)
        userData = fetch.parseAllUserDataToObject(it).sortedBy { it.firstName } ?: emptyList()
    }, failure = {
        println(it)
    })
    Box {
        if (userData.isEmpty() && search.isEmpty()) {
            ProcessView(navController)
        } else if (userData.isEmpty() && search.isNotEmpty()) {
            Text(
                "No results found",
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        } else {
            UsersList(users = userData, navController)
        }
    }
}

/**
 * UserList is a composable function that creates a list of users. It also navigates to the user
 * screen when a user is clicked.
 *
 * @param users is a list of users that is used to create the list.
 * @param navController is used to navigate to the user screen.
 */
@Composable
fun UsersList(users: List<User>, navController: NavController) {
    LazyColumn {
        items(users) { user ->
            Button(onClick = {
                navController.navigate( "UserScreen/${user.id}")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(1.5.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (isSystemInDarkTheme()) Color.Black else Color.White
            ),
            elevation = ButtonDefaults.elevation(defaultElevation = 0.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        "${user.firstName} ${user.lastName}",
                        Modifier
                            .padding(vertical = 10.dp),
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal
                    )

                }
            }
        }
    }
}

/**
 * SearchField is a composable that creates a search field. It also calls the callback function
 * when the text in the search field changes. The callback function is used to update the search
 * string.
 *
 * @param callback is a function that is called when the text in the search field changes.
 */
@Composable
fun SearchField(callback: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = {
            text = it
            callback(it)
        },
        placeholder = { Text("Search") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(
                RoundedCornerShape(25.dp)
            ),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )


    )
}

/**
 * AddButton is a composable that creates a floating action button. It also navigates to the
 * AddUser screen when the button is clicked.
 *
 * @param navController is a NavController that is used to navigate to the AddUser screen.
 * @param modifier is a Modifier that is used to modify the floating action button.
 */
@Composable
fun AddButton(navController: NavController, modifier: Modifier = Modifier) {
    FloatingActionButton(
        onClick = { navController.navigate("AddUser") },
        modifier = modifier
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add Button")
    }
}