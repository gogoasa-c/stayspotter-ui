package com.stayspotter

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class Constant {

    companion object {
        // colours:
        public val BACKGROUND_COLOR: Color = Color(0xFF202020)
        public val PETRIFIED_BLUE: Color = Color(0xFF124966)
        public val EDGE_BLUE: Color = Color(0xFF2EB4FC)
        public val LIGHT_EDGE_BLUE: Color = Color(0xFFABE1FE)
        public val TEXT_GRAY: Color = Color(0xFFEEEEEE)
        // sizes in dp
        public val CORNER_RADIUS: Dp = 16.dp
        public val STD_LENGTH: Dp = 360.dp
        public val STD_HEIGHT: Dp = 50.dp
        public val STD_PADDING: Dp = 10.dp
        public val STD_SQUARE_ICON_LENGTH = 30.dp
        // sizes in sp
        public val STD_FONT_SIZE: TextUnit = 12.sp
        public val STD_TITLE_FONT_SIZE: TextUnit = 24.sp
        public val STD_SUBTITLE_FONT_SIZE: TextUnit = 18.sp
    }
}