package fi.tuni.dammyjson

import FetchTools
import User
import ValidateTools
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
 * Edit a user in the database. This function is called when the user presses the edit button.
 *
 * @param navController The NavController used to navigate between composables.
 */
@Composable
fun Edit(user: User, navController: NavController) {
    // Use the FetchTools class to fetch data from the API.
    var id by remember { mutableStateOf(user.id) }
    var firstName by remember { mutableStateOf(user.firstName) }
    var lastName by remember { mutableStateOf(user.lastName) }
    var age: String by remember { mutableStateOf(user.age) }
    var email by remember { mutableStateOf(user.email) }
    var phone by remember { mutableStateOf(user.phone) }
    var username by remember { mutableStateOf(user.username) }
    var password by remember { mutableStateOf(user.password) }
    var validateTools = ValidateTools()
    Column {
        Log.d("EditUser", "Editing User: $user")
        Box(Modifier.fillMaxWidth()) {
            // Code for the back button.
            IconButton(Icons.Filled.ArrowBack, "Back", Modifier.align(Alignment.TopStart)) {
                navController.popBackStack()
            }
            // If input is valid, show the button and allow the user to edit the user.
            if (validateTools.isUserValid(User(user.id, firstName, lastName, age, email, phone, username, password))) {
                val user = User(id, firstName, lastName, age, email, phone, username, password)
                EditUserButton(user, Modifier.align(alignment = Alignment.TopEnd), navController)
            } else {
                EditUserButton(user, Modifier.align(alignment = Alignment.TopEnd), navController, enabled = false)
            }

            Text("Edit User",
                fontFamily = FontFamily.SansSerif,
                fontSize = 30.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            MyTextField(
                "First Name",
                firstName,
                validateTools.isFirstNameValid(firstName),
                KeyboardType.Text
            ) { firstName = it }
            MyTextField(
                "Last Name",
                lastName,
                validateTools.isLastNameValid(lastName),
                KeyboardType.Text
            ) { lastName = it }
            MyTextField(
                "Age",
                age,
                validateTools.isAgeValid(age),
                KeyboardType.Number
            ) { age = it }
            MyTextField(
                "Email",
                email,
                validateTools.isEmailValid(email),
                KeyboardType.Email
            ) { email = it }
            MyTextField(
                "Phone",
                phone,
                validateTools.isPhoneValid(phone),
                KeyboardType.Phone
            ) { phone = it }
            MyTextField(
                "Username",
                username,
                validateTools.isUsernameValid(username),
                KeyboardType.Text
            ) { username = it }
            MyTextField(
                "Password",
                password,
                validateTools.isPasswordValid(password),
                KeyboardType.Password
            ) { password = it }

        }
        Spacer(modifier = Modifier.height(20.dp))
        // If input is valid, show the button and allow the user to edit the user.
        DeleteUserButton(user, Modifier.align(Alignment.CenterHorizontally), navController)
    }
}

/**
 * Get the data for the user we are editing.
 *
 * @param navController The NavController used to navigate between composables.
 * @param userId The id of the user we are editing.
 */
@Composable
fun GetEditData(navController: NavController, userId: Int) {
    var data: User? by remember { mutableStateOf(null) }
    val fetch = FetchTools()


    fetch.getData("https://dummyjson.com/users/${userId}", {
        Log.d("EditUser", "Success: $it")
        data = fetch.parseOneUserDataToObject(it)
    }, {
        Log.d("EditUser", "Error: $it")
    })

    if (data == null) {
        ProcessView(navController)
    } else {
        // If data is not null, then we have the data for the user we are editing
        data?.let { Edit(it, navController) }
    }

}

/**
 * Delete a user from the database. This function is called when the user presses the delete button
 * in user.
 *
 * @param user The user we are deleting.
 * @param modifier The modifier for the button.
 * @param navController The NavController used to navigate between composables.
 */
@Composable
fun DeleteUserButton(user: User, modifier: Modifier, navController: NavController) {
    val context = LocalContext.current
    Button(
        onClick = {
            val fetch = FetchTools()
            fetch.deleteData("https://dummyjson.com/users/${user.id}", {
                Log.d("DeleteUserButton", "Success: $it")
                //Code launch a coroutine on the main thread and shows a toast message.
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(
                        context,
                        "${user.firstName} ${user.lastName} deletion successful!",
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate("home")
                }

            }, {
                Log.d("DeleteUserButton", "Error: $it")
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(
                        context,
                        "Something wrong, try again!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        },
        modifier = modifier.padding(10.dp),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        elevation = null,
        shape = RoundedCornerShape(30.dp)) {
        Text("Delete User",  color = Color.Red)
    }

}

/**
 * Edit a user in the database. This function is called when the user presses the edit button
 * in user. This function uses the FetchTools class to fetch data from the API.
 * This function also uses the coroutine scope to launch a coroutine on the main thread and
 * shows a toast message. If the user is edited successfully, the user is navigated back to the
 * home screen. Otherwise, a toast message is shown to the user to try again.
 *
 * @param user The user we are editing.
 * @param modifier The modifier for the button.
 * @param navController The NavController used to navigate between composables.
 */
@Composable
fun EditUserButton(user: User, modifier: Modifier, navController: NavController, enabled: Boolean = true) {
    val fetch = FetchTools()
    val context = LocalContext.current
    Button(
        onClick = {
            val userJson = fetch.parseUserDataToJson(user)
            fetch.putData("https://dummyjson.com/users/${user.id}", userJson, {
                Log.d("EditUserButton", "Success: $it")
                //Code launch a coroutine on the main thread and shows a toast message.
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(
                        context,
                        "${user.firstName} ${user.lastName} edition successful!",
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.popBackStack()
                }

            }, {
                Log.d("EditUserButton", "Error: $it")
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(
                        context,
                        "Something wrong, try again!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        },
        modifier = modifier.padding(10.dp),
        colors = ButtonDefaults.buttonColors(if (isSystemInDarkTheme()) Color.White else Color.Black),
        shape = RoundedCornerShape(30.dp),
        enabled = enabled,
    ) {
        Text("Save",  color = if (isSystemInDarkTheme()) Color.Black else Color.White)
    }
}