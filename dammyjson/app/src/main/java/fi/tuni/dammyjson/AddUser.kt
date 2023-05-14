package fi.tuni.dammyjson

import FetchTools
import User
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Add a user to the database. This function is called when the user presses the plus button.
 *
 *
 * @param navController The NavController used to navigate between composables.
 */
@Composable
fun Add(navController: NavController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column {
        Box(Modifier.fillMaxWidth()) {
            // Code for the back button.
            IconButton(Icons.Filled.ArrowBack, "Back", Modifier.align(Alignment.TopStart)) {
                navController.popBackStack()
            }
            // If input is valid, show the button and allow the user to add the user.
            if (age.isDigitsOnly() && age.isNotBlank()) {
                val user = User(0, firstName, lastName, age.toInt(), email, phone, username, password)
                AddUserButton(user, Modifier.align(alignment = Alignment.TopEnd), navController)
            }

            Text("Add User",
                fontFamily = FontFamily.SansSerif,
                fontSize = 30.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        MyTextField(placeholder = "First Name", firstName, KeyboardType.Text) { firstName = it }
        MyTextField(placeholder = "Last Name", lastName, KeyboardType.Text) { lastName = it}
        MyTextField(placeholder = "Age", age, KeyboardType.Number) { age = it}
        MyTextField(placeholder = "Email", email, KeyboardType.Email) { email = it }
        MyTextField(placeholder = "Phone", phone, KeyboardType.Phone) { phone = it }
        MyTextField(placeholder = "Username", username, KeyboardType.Text) { username = it }
        MyTextField(placeholder = "Password", password, KeyboardType.Password) { password = it }


    }
}

/**
 * A text field that takes a placeholder, value, keyboard type and a function to update the value.
 *
 * @param placeholder The placeholder text.
 * @param value The value of the text field.
 * @param keyboardType The keyboard type of the text field.
 * @param onValueChange The function to update the value.
 */
@Composable
fun AddUserButton(user: User, modifier: Modifier, navController: NavController) {
    val fetch = FetchTools()
    val context = LocalContext.current
    Button(
        onClick = {
            val userJson = fetch.parseUserDataToJson(user)
            fetch.postData("https://dummyjson.com/users/add", userJson, {
                println(it)
                //Code launch a coroutine on the main thread and shows a toast message.
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(
                        context,
                        "${user.firstName} ${user.lastName} addition successful!",
                        Toast.LENGTH_LONG
                    ).show()
                    navController.popBackStack()
                }

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
        },
        modifier = modifier.padding(10.dp),
        colors = ButtonDefaults.buttonColors(if (isSystemInDarkTheme()) Color.White else Color.Black),
        shape = RoundedCornerShape(30.dp)
    ) {
        Text("Save",  color = if (isSystemInDarkTheme()) Color.Black else Color.White)
    }
}