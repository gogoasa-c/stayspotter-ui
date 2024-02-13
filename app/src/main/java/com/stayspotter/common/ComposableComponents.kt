package com.stayspotter.common

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.stayspotter.Constant
import com.stayspotter.R

@Composable
fun GenericButton(text: String, color: Color, onClick: () -> Unit = {}) {
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
fun FormField(placeholder: String, field: String, setField: (String) -> Unit) {

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
        }
    )
}