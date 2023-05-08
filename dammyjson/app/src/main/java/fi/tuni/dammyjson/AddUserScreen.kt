package fi.tuni.dammyjson

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun AddUserScreen(navController: NavController) {
    Text("Hello")
    Button(
        onClick = {
            // Tee jotain uuden kohteen kanssa
            navController.popBackStack()
        }
    ) {
        Text("Add item")
    }
}