package fi.tuni.dammyjson

import FetchTools
import User
import android.util.Log
import android.view.RoundedCorner
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation


@Composable
fun MainScreen(navControlle: NavController) {
    Box {
        Column {
            SearchField()
            CreateUserList(navControlle)
        }
        AddButton(navControlle, modifier = Modifier
            .padding(bottom = 20.dp, end = 20.dp)
            .align(Alignment.BottomEnd))
    }
}

@Composable
fun CreateUserList(navController: NavController) {
    val fetch = FetchTools()
    var userData by remember { mutableStateOf(listOf<User>()) }
    fetch.getData(url = "https://dummyjson.com/users", response = {
        Log.d("test", it)
        userData = fetch.parseDatatoObject(it) ?: emptyList()
    }, failure = {
        println(it)
    })
    if (userData.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    } else {
        UsersList(users = userData, navController)
    }
}

@Composable
fun UsersList(users: List<User>, navController: NavController) {
    LazyColumn {
        items(users) { user ->
            Button(onClick = {
                navController.navigate( "UserScreen/${user.id}")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(1.5.dp)
                ,
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

@Composable
fun SearchField() {
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = { newText -> text = newText},
        placeholder = { Text("Hae") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(
                RoundedCornerShape(25.dp)
            ),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent, // Aseta viivan väriksi läpinäkyvä
            unfocusedIndicatorColor = Color.Transparent // Aseta viivan väriksi läpinäkyvä
        )


    )
}

@Composable
fun MyTextField(placeholder: String, value: String, keyboardType: KeyboardType, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        placeholder = { Text(placeholder) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),

        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = keyboardType

        ),
        visualTransformation = VisualTransformation.None
    )
}

@Composable
fun AddButton(navControlle: NavController, modifier: Modifier = Modifier) {
    FloatingActionButton(
        onClick = { navControlle.navigate("AddUser/${null}") },
        modifier = modifier
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add Button")
    }
}