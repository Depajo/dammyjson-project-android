package fi.tuni.dammyjson

import FetchTools
import User
import android.widget.Toast
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AddUserScreen(navController: NavController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val fetch = FetchTools()
    Column {
        Text("Add User", fontFamily = FontFamily.SansSerif, fontSize = 30.sp)

        MyTextField(placeholder = "First Name", firstName, KeyboardType.Text) { firstName = it }

        MyTextField(placeholder = "Last Name", lastName, KeyboardType.Text) { lastName = it}

        MyTextField(placeholder = "Age", age, KeyboardType.Number) { age = it}

        MyTextField(placeholder = "Email", email, KeyboardType.Email) { email = it }

        MyTextField(placeholder = "Phone", phone, KeyboardType.Phone) { phone = it }

        MyTextField(placeholder = "Username", username, KeyboardType.Text) { username = it }

        MyTextField(placeholder = "Password", password, KeyboardType.Password) { password = it }
        val context = LocalContext.current
        Button(
            onClick = {
                if (age.isDigitsOnly() && age.isNotBlank()) {
                    val user = User(0, firstName, lastName, age.toInt(), email, phone, username, password )
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

private fun onSuccess(res: String, toastText: String) {

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