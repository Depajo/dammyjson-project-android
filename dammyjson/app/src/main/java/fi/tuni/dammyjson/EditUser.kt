package fi.tuni.dammyjson

import FetchTools
import User
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun Edit(user: User, navController: NavController) {
    // Use the FetchTools class to fetch data from the API.
    val fetch = FetchTools()
    var firstName by remember { mutableStateOf(user.firstName) }
    var lastName by remember { mutableStateOf(user.lastName) }
    var age by remember { mutableStateOf(user.age.toString()) }
    var email by remember { mutableStateOf(user.email) }
    var phone by remember { mutableStateOf(user.phone) }
    var username by remember { mutableStateOf(user.username) }
    var password by remember { mutableStateOf(user.password) }

    Column {
        Box {
            IconButton(Icons.Filled.ArrowBack, "Back", Modifier.align(Alignment.TopStart)) {
                navController.popBackStack()
            }
        }

        Text("Edit User", fontFamily = FontFamily.SansSerif, fontSize = 30.sp)
        MyTextField(placeholder = "First Name", firstName, KeyboardType.Text) { firstName = it }
        MyTextField(placeholder = "Last Name", lastName, KeyboardType.Text) { lastName = it }
        MyTextField(placeholder = "Age", age, KeyboardType.Number) { age = it }
        MyTextField(placeholder = "Email", email, KeyboardType.Email) { email = it }
        MyTextField(placeholder = "Phone", phone, KeyboardType.Phone) { phone = it }
        MyTextField(placeholder = "Username", username, KeyboardType.Text) { username = it }
        MyTextField(placeholder = "Password", password, KeyboardType.Password) { password = it }
        val context = LocalContext.current
        Button(
            onClick = {
                if (age.isDigitsOnly() && age.isNotBlank()) {
                    val user = User(
                        0,
                        firstName,
                        lastName,
                        age.toInt(),
                        email,
                        phone,
                        username,
                        password
                    )
                    val userJson = fetch.parseUserDataToJson(user)
                    fetch.postData("https://dummyjson.com/users/add", userJson, {
                        println(it)
                        //This code launches a coroutine on the main thread and shows a toast message.
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(
                                context,
                                "${firstName} ${lastName} addition successful!",
                                Toast.LENGTH_SHORT
                            ).show()
                            navController.popBackStack()
                        }
                        //if something goes wrong, show a toast message.
                    }, {
                        println(it)
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(
                                context,
                                "Something wrong, try again!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                // If age is not a number, show a toast message.
                } else {
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(
                            context,
                            "Something wrong, try again!",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
            }
        ) {
            Text("Add item")
        }
    }

}