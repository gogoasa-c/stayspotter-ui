package com.stayspotter.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import com.stayspotter.Constant
import com.stayspotter.R
import com.stayspotter.search.ButtonSpacer
import com.stayspotter.ui.theme.NavBarTheme
import com.stayspotter.ui.theme.StaySpotterTheme

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
        textStyle = TextStyle.Default.copy(fontSize = Constant.STD_FONT_SIZE * 2)
    )
}

@Composable
fun NavigationBar() {
    NavBarTheme {
        androidx.compose.material3.NavigationBar(
            modifier = Modifier.size(Constant.STD_LENGTH * 2, Constant.NAVBAR_HEIGHT),
        ) {
            NavigationBarItem(selected = false, onClick = { /*TODO*/ }, icon = {
                Icon(Icons.Outlined.FavoriteBorder, contentDescription = "Profile", tint = Constant.TEXT_GRAY)
            }, colors = NavigationBarItemDefaults.colors(
                indicatorColor = Constant.PETRIFIED_BLUE,
            ))
            NavigationBarItem(selected = true, onClick = { /*TODO*/ }, icon = {
                Icon(Icons.Filled.Search, contentDescription = "Search", tint = Constant.TEXT_GRAY)
            }, colors = NavigationBarItemDefaults.colors(
                indicatorColor = Constant.PETRIFIED_BLUE,
            ))
            NavigationBarItem(selected = false, onClick = { /*TODO*/ }, icon = {
                Icon(Icons.Outlined.Person, contentDescription = "Profile", tint = Constant.TEXT_GRAY)
            }, colors = NavigationBarItemDefaults.colors(
                indicatorColor = Constant.PETRIFIED_BLUE,
            ))
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