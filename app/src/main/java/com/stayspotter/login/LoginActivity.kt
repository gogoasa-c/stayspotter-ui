package com.stayspotter.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.ViewModel
import com.stayspotter.Constant
import com.stayspotter.R
import com.stayspotter.common.DefaultActivity
import com.stayspotter.common.FormField
import com.stayspotter.common.GenericFormButton
import com.stayspotter.common.api.ApiClient
import com.stayspotter.model.UserLoginDto
import com.stayspotter.search.SearchActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
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
    val viewModel = remember { LoginViewModel() }
    val context = LocalContext.current

    val loginOnClick: () -> Unit = loginOnClick@{
        val trimmedUsername = viewModel.username.trim()
        val trimmedPassword = viewModel.password.trim()

        val userLoginDto = UserLoginDto(trimmedUsername, trimmedPassword)

        val call = ApiClient.apiService.login(userLoginDto)

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    viewModel.jsonWebToken = response.body()!!
                    val intent  = Intent(context, DefaultActivity::class.java)
                    intent.putExtra(Constant.INTENT_KEY_JWT, viewModel.jsonWebToken)

                    context.startActivity(intent)

                    return
                }

                Toast.makeText(context, "Incorrect username or password",
                    Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(context, "Error logging in...",
                    Toast.LENGTH_SHORT).show()
                Log.e("LoginActivity", "Error logging in", t)
            }
        })
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

        FormField("Username", viewModel.username, { viewModel.username = it })
        Spacer(modifier = Modifier.padding(Constant.STD_PADDING))
        FormField(
            "Password", viewModel.password, { viewModel.password = it },
            PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.padding(Constant.STD_PADDING))
        GenericFormButton("Login", Constant.EDGE_BLUE, loginOnClick)
        Spacer(modifier = Modifier.padding(Constant.STD_PADDING))
        GenericFormButton("Don't have an account? Register now!", Constant.LIGHT_EDGE_BLUE)
    }
}

class LoginViewModel : ViewModel() {
    var username by mutableStateOf("")
    var password by mutableStateOf("")

    var jsonWebToken by mutableStateOf("")
}