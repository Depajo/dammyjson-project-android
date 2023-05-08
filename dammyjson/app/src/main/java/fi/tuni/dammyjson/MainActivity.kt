package fi.tuni.dammyjson

import FetchTools
import User
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fi.tuni.dammyjson.ui.theme.DammyjsonTheme

class MainActivity : ComponentActivity() {
    val fetch = FetchTools()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DammyjsonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(3.dp),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        SearchField()
                        CreateUserList()

                    }

                }
            }
        }
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
            Text("${user.firstName} ${user.lastName}", Modifier
                .padding(10.dp),
                fontSize = 25.sp
            )
        }
    }
}

@Composable
fun SearchField() {
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = { newText -> text = newText},
        placeholder = {Text("Hae")},
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray)
            .clip(
                RoundedCornerShape(10.dp)
            )
    )
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DammyjsonTheme {
        Greeting("Android")
    }
}