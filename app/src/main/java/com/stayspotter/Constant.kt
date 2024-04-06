package com.stayspotter

import androidx.compose.ui.graphics.Color
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

        // sizes in dp
        val CORNER_RADIUS: Dp = 16.dp
        val STD_LENGTH: Dp = 360.dp
        val STD_HEIGHT: Dp = 50.dp
        val NAVBAR_HEIGHT: Dp = 75.dp
        val STD_PADDING: Dp = 10.dp
        val STD_SQUARE_ICON_LENGTH = 30.dp
        val OVERLAY_BUTTON_OFFSET = 400.dp
        // sizes in sp
        val STD_FONT_SIZE: TextUnit = 12.sp
        val STD_TITLE_FONT_SIZE: TextUnit = 24.sp
        val STD_SUBTITLE_FONT_SIZE: TextUnit = 18.sp
    }
}