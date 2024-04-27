package com.stayspotter

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class Constant {

    companion object {
        // colours:
        val BACKGROUND_COLOR: Color = Color(0xFF202020)
        val DARK_GRAY: Color = Color(0xFF121212)
        val PETRIFIED_BLUE: Color = Color(0xFF124966)
        val EDGE_BLUE: Color = Color(0xFF2EB4FC)
        val LIGHT_EDGE_BLUE: Color = Color(0xFFABE1FE)
        val TEXT_GRAY: Color = Color(0xFFEEEEEE)
        val FADED_GRAY: Color = Color(0xFF8B8B8B)
        val LIGHT_GRAY: Color = Color(0xFF616161)
        val TRANSPARENT: Color = Color.Transparent

        // sizes in dp
        val CORNER_RADIUS: Dp = 16.dp
        val STD_LENGTH: Dp = 360.dp
        val SMALL_SEARCHBAR_LENGTH: Dp = 300.dp
        val SMALL_BUTTON_LENGTH: Dp = 65.dp
        val LARGE_BUTTON_LENGTH: Dp = 100.dp
        val STD_HEIGHT: Dp = 50.dp
        val NAVBAR_HEIGHT: Dp = 75.dp
        val PICTURE_HEIGHT: Dp = 250.dp
        val STD_PADDING: Dp = 10.dp
        val STD_BIG_PADDING: Dp = 150.dp
        val PADDING_STAYS: Dp = 75.dp
        val STD_SQUARE_ICON_LENGTH = 30.dp
        val OVERLAY_BUTTON_OFFSET = 400.dp
        // sizes in sp
        val STD_FONT_SIZE: TextUnit = 16.sp
        val STD_TITLE_FONT_SIZE: TextUnit = 24.sp
        val STD_SUBTITLE_FONT_SIZE: TextUnit = 18.sp

        const val CONFIRM_MESSAGE: String = "OK"

        val FONT_FAMILY = FontFamily(Font(R.font.inter))
    }
}