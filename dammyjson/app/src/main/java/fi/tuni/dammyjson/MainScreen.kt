package fi.tuni.dammyjson

import FetchTools
import User
import android.util.Log
import android.view.RoundedCorner
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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


@Composable
fun MainScreen(navControlle: NavController) {
    Box {
        Column {
            SearchField()
            CreateUserList()
        }
        AddButton(navControlle, modifier = Modifier
            .padding(bottom = 20.dp, end = 20.dp)
            .align(Alignment.BottomEnd))
    }
}

@Composable
fun CreateUserList() {
    val fetch = FetchTools()
    var userData by remember { mutableStateOf(listOf<User>()) }
    fetch.getData(url = "https://dummyjson.com/users", response = {
        Log.d("test", it)
        userData = fetch.parseDatatoObject(it) ?: emptyList()
    }, failure = {
        println(it)
    })

    UsersList(users = userData)
}

@Composable
fun UsersList(users: List<User>) {
    LazyColumn {
        items(users) { user ->
            Button(onClick = {
                
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
                .border(0.4.dp, Color.Gray),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            elevation = ButtonDefaults.elevation(defaultElevation = 0.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        "${user.firstName} ${user.lastName}",
                        Modifier
                            .padding(2.dp),
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
            .border(1.dp, Color.Gray)
            .clip(
                RoundedCornerShape(10.dp)
            )
    )
}

@Composable
fun AddButton(navControlle: NavController, modifier: Modifier = Modifier) {
    FloatingActionButton(
        onClick = { navControlle.navigate("AddUser") },
        backgroundColor = Color.Cyan,
        modifier = modifier
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add Button")
    }
}