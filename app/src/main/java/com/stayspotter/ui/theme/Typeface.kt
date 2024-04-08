package com.stayspotter.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.stayspotter.Constant
import com.stayspotter.R

val Typography = Typography(
    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter)),
        fontSize = Constant.STD_FONT_SIZE,
    ),
)