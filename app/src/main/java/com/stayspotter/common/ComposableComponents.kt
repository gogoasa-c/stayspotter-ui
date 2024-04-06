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
    Row(
        modifier = Modifier
            .size(Constant.STD_LENGTH * 2, Constant.NAVBAR_HEIGHT)
            .background(color = Constant.DARK_GRAY),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom
    ) {}
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