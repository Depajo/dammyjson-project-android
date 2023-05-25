package fi.tuni.dammyjson

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

/**
 * IconButton is a composable function that creates a button with an icon.
 *
 * @param icon is the icon that is shown in the button.
 * @param description is the description of the icon.
 * @param modifier is used to modify the button.
 * @param action is the action that is performed when the button is clicked.
 */
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


/**
 * TextAreaView is a composable function that creates a text area with a label.
 *
 * @param label is the label of the text area.
 * @param text is the text that is shown in the text area.
 */
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

/**
 * ProcessView is a composable function that creates a view that is shown when the app is processing
 * something. You can use it to show a loading indicator.
 *
 * @param navController is used to navigate to the user screen.
 */
@Composable
fun ProcessView(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            CircularProgressIndicator()
        }
    }
}

/**
 * MyTextField is a composable function that creates a text field.
 *
 * @param placeholder is the placeholder of the text field.
 * @param value is the value of the text field.
 * @param keyboardType is the type of the keyboard that is shown when the text field is clicked.
 * @param onValueChange is the action that is performed when the value of the text field is changed.
 */
@Composable
fun MyTextField(placeholder: String, value: String, validate: Boolean, keyboardType: KeyboardType, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        placeholder = { Text(placeholder) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        isError = !validate,

        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = keyboardType

        ),
        visualTransformation = VisualTransformation.None
    )
}