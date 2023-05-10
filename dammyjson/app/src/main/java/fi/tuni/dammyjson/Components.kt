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

@Composable
fun ProcessView(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            CircularProgressIndicator()
        }
    }
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