package com.stayspotter.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import com.stayspotter.Constant
import com.stayspotter.R
import com.stayspotter.ui.theme.NavBarTheme

@Composable
fun GenericFormButton(text: String, color: Color, onClick: () -> Unit = {}) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(Constant.STD_LENGTH, Constant.STD_HEIGHT),
        colors = ButtonDefaults.buttonColors(backgroundColor = color),
        shape = RoundedCornerShape(Constant.CORNER_RADIUS)
    ) {
        Text(
            text = text,
            fontFamily = FontFamily(Font(R.font.inter)),
            fontSize = Constant.STD_FONT_SIZE
        )
    }
}

@Composable
fun GenericSquircleButton(color: Color, icon: @Composable () -> Unit, onClick: () -> Unit = {}) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(Constant.STD_HEIGHT, Constant.STD_HEIGHT),
        colors = ButtonDefaults.buttonColors(backgroundColor = color),
        shape = RoundedCornerShape(Constant.CORNER_RADIUS)
    ) {
        icon()
    }
}


@Composable
fun FormField(
    placeholder: String, field: String, setField: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {

    TextField(
        modifier = Modifier
            .size(Constant.STD_LENGTH, Constant.STD_HEIGHT),
        value = field,
        onValueChange = {
            setField(it)
        },
        shape = RoundedCornerShape(Constant.CORNER_RADIUS),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = Constant.PETRIFIED_BLUE,
            textColor = Constant.TEXT_GRAY
        ),
        placeholder = {
            Text(text = placeholder, color = Constant.TEXT_GRAY, fontSize = Constant.STD_FONT_SIZE)
        },
        textStyle = TextStyle.Default.copy(fontSize = Constant.STD_FONT_SIZE),
        visualTransformation = visualTransformation
    )
}

@Composable
fun IconField(
    placeholder: String, field: String, setField: (String) -> Unit,
    icon: @Composable () -> Unit
) {

    TextField(
        modifier = Modifier
            .size(Constant.STD_LENGTH, Constant.STD_HEIGHT),
        value = field,
        onValueChange = {
            setField(it)
        },
        shape = RoundedCornerShape(Constant.CORNER_RADIUS),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = Constant.PETRIFIED_BLUE,
            textColor = Constant.TEXT_GRAY
        ),
        placeholder = {
            Text(text = placeholder, color = Constant.TEXT_GRAY, fontSize = Constant.STD_FONT_SIZE)
        },
        leadingIcon = icon,
        textStyle = TextStyle.Default.copy(fontSize = Constant.STD_FONT_SIZE)
    )
}

@Composable
fun NavigationBar() {
    NavBarTheme {
        androidx.compose.material3.NavigationBar(
            modifier = Modifier.size(Constant.STD_LENGTH * 2, Constant.NAVBAR_HEIGHT),
        ) {
            val (selected, setSelected) = remember { mutableIntStateOf(0) }

            NavigationBarItem(selected = selected == 0, onClick = { setSelected(0) }, icon = {
                Icon(
                    Icons.Outlined.FavoriteBorder,
                    contentDescription = "Profile",
                    tint = Constant.TEXT_GRAY
                )
            }, colors = NavigationBarItemDefaults.colors(
                indicatorColor = Constant.PETRIFIED_BLUE,
            )
            )
            NavigationBarItem(selected = selected == 1, onClick = { setSelected(1) }, icon = {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = Constant.TEXT_GRAY
                )
            }, colors = NavigationBarItemDefaults.colors(
                indicatorColor = Constant.PETRIFIED_BLUE,
            )
            )
            NavigationBarItem(selected = selected == 2, onClick = { setSelected(2) }, icon = {
                Icon(
                    Icons.Outlined.Person,
                    contentDescription = "Profile",
                    tint = Constant.TEXT_GRAY
                )
            }, colors = NavigationBarItemDefaults.colors(
                indicatorColor = Constant.PETRIFIED_BLUE,
            )
            )
        }
    }
}

@Composable
fun GenericButton(length: Dp, height: Dp, color: Color, text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(length, height),
        colors = ButtonDefaults.buttonColors(backgroundColor = color),
        shape = RoundedCornerShape(Constant.CORNER_RADIUS),
    ) {
        Text(text = text, color = Constant.TEXT_GRAY, fontSize = Constant.STD_FONT_SIZE)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chip(text: String) {
    InputChip(
        modifier = Modifier.width(Constant.STD_LENGTH),
        shape = RoundedCornerShape(Constant.CORNER_RADIUS),
        selected = true, onClick = { /*TODO*/ },
        label = { Text(text, color = Constant.TEXT_GRAY, modifier = Modifier.fillMaxWidth(0.9f)) },
        trailingIcon = {
            Icon(
                Icons.Default.Close,
                contentDescription = "Close icon",
                tint = Constant.TEXT_GRAY,
                modifier = Modifier.size(InputChipDefaults.IconSize)
            )
        },
        colors = InputChipDefaults.inputChipColors(
            selectedContainerColor = Constant.DARK_GRAY,
        )
    )
}
