package com.stayspotter.user

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stayspotter.Constant
import com.stayspotter.R
import com.stayspotter.common.GenericButton
import com.stayspotter.common.Navbar

class UserProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            UserProfile()
        }
    }
}

@Preview
@Composable
private fun UserProfile() {
    val username = "john_doe_2054"
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Constant.BACKGROUND_COLOR)
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = Constant.STD_BIG_PADDING)
        ) {
            UserIcon()
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = username,
                fontFamily = FontFamily(Font(R.font.inter_semibold)),
                fontSize = Constant.STD_TITLE_FONT_SIZE,
                textAlign = TextAlign.Center,
                color = Constant.TEXT_GRAY
            )
            Spacer(modifier = Modifier.size(10.dp))
            UserStats()

            Navbar(1)
        }

        SettingsButton()
    }
}

@Preview
@Composable
fun EmbeddedProfile() {
    val username = "john_doe_2054"
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Constant.BACKGROUND_COLOR)
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = Constant.STD_BIG_PADDING)
        ) {
            UserIcon()
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = username,
                fontFamily = FontFamily(Font(R.font.inter_semibold)),
                fontSize = Constant.STD_TITLE_FONT_SIZE,
                textAlign = TextAlign.Center,
                color = Constant.TEXT_GRAY
            )
            Spacer(modifier = Modifier.size(10.dp))
            UserStats()
        }

        SettingsButton()
    }
}

@Preview
@Composable
private fun UserIcon() {
    Box(
        modifier = Modifier
            .size(Constant.STD_HEIGHT)
            .background(Constant.TRANSPARENT)
            .border(2.dp, Constant.LIGHT_EDGE_BLUE, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Person, contentDescription = "User Icon",
            modifier = Modifier.scale(2.25f)
        )
    }

}

@Preview
@Composable
private fun SettingsButton() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(Constant.OVERLAY_BUTTON_OFFSET))
        GenericButton(
            length = Constant.STD_LENGTH,
            height = Constant.STD_HEIGHT,
            color = Constant.LIGHT_GRAY,
            text = "Settings"
        ) {}
    }
}

@Preview
@Composable
private fun UserStats(staysNo: Int = 10, topPercentage: Double = 50.0) {
    val userStats = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Constant.TEXT_GRAY)) {
            append("You've checked out: ")
        }
        withStyle(style = SpanStyle(color = Constant.EDGE_BLUE)) {
            append("$staysNo")
        }
        withStyle(style = SpanStyle(color = Constant.TEXT_GRAY)) {
            append(" stays.\n")
        }

        withStyle(style = SpanStyle(color = Constant.TEXT_GRAY)) {
            append("Impressive!\n")
        }

        withStyle(style = SpanStyle(color = Constant.TEXT_GRAY)) {
            append("You're in the top")
        }
        withStyle(style = SpanStyle(color = Constant.EDGE_BLUE)) {
            append(" $topPercentage% ")
        }
        withStyle(style = SpanStyle(color = Constant.TEXT_GRAY)) {
            append("of ")
        }
        withStyle(style = SpanStyle(color = Constant.LIGHT_EDGE_BLUE)) {
            append("Stay")
        }
        withStyle(style = SpanStyle(color = Constant.EDGE_BLUE)) {
            append("Spotter")
        }
        withStyle(style = SpanStyle(color = Constant.TEXT_GRAY)) {
            append(" users!")
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(Constant.STD_PADDING)
    ) {
        Text(
            text = userStats,
            fontFamily = FontFamily(Font(R.font.inter_semibold)),
            fontSize = Constant.STD_SUBTITLE_FONT_SIZE,
            modifier = Modifier.padding(Constant.STD_PADDING),
            textAlign = TextAlign.Center,
        )
    }
}