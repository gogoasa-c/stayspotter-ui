package com.stayspotter.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.times
import com.stayspotter.Constant
import com.stayspotter.R
import com.stayspotter.common.FormField
import com.stayspotter.common.GenericFormButton
import com.stayspotter.search.SearchActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen()
        }
    }
}


@Composable
fun TitleParagraph() {
    val tagline = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Constant.TEXT_GRAY)) {
            append("Welcome to ")
        }
        withStyle(style = SpanStyle(color = Constant.LIGHT_EDGE_BLUE)) {
            append("Stay")
        }
        withStyle(style = SpanStyle(color = Constant.EDGE_BLUE)) {
            append("Spotter")
        }
        withStyle(style = SpanStyle(color = Constant.TEXT_GRAY)) {
            append("!")
        }
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = tagline,
            fontFamily = FontFamily(Font(R.font.inter_semibold)),
            fontSize = Constant.STD_TITLE_FONT_SIZE,
            modifier = Modifier.padding(Constant.STD_PADDING)
        )
    }
}

@Composable
fun ParagraphSubtitle() {
    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Constant.TEXT_GRAY)) {
            append("The only app you'll ever need to find the perfect ")
        }
        withStyle(style = SpanStyle(color = Constant.LIGHT_EDGE_BLUE)) {
            append("stay")
        }
        withStyle(style = SpanStyle(color = Constant.TEXT_GRAY)) {
            append("!")
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(Constant.STD_PADDING)
    ) {
        Text(
            text = text,
            fontFamily = FontFamily(Font(R.font.inter_semibold)),
            fontSize = Constant.STD_SUBTITLE_FONT_SIZE,
            modifier = Modifier.padding(Constant.STD_PADDING),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        )
    }
}

@Composable
@Preview
fun LoginScreen() {
    val (username, setUsername) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }

    val context = LocalContext.current

    val loginOnClick: () -> Unit = loginOnClick@{
        if (username != "admin" && password != "admin") {
            Toast.makeText(context, "Incorrect username or password", Toast.LENGTH_SHORT).show()
            return@loginOnClick
        }

        context.startActivity(Intent(context, SearchActivity::class.java))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Constant.BACKGROUND_COLOR),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TitleParagraph()
        ParagraphSubtitle()
        Spacer(modifier = Modifier.padding(4 * Constant.STD_PADDING))

        FormField("Username", username, setUsername)
        Spacer(modifier = Modifier.padding(Constant.STD_PADDING))
        FormField("Password", password, setPassword, PasswordVisualTransformation())
        Spacer(modifier = Modifier.padding(Constant.STD_PADDING))
        GenericFormButton("Login", Constant.EDGE_BLUE, loginOnClick)
        Spacer(modifier = Modifier.padding(Constant.STD_PADDING))
        GenericFormButton("Don't have an account? Register now!", Constant.LIGHT_EDGE_BLUE)
    }
}
