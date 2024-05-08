package com.stayspotter.login

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.times
import com.stayspotter.Constant
import com.stayspotter.R
import com.stayspotter.common.FormField
import com.stayspotter.common.GenericFormButton
import com.stayspotter.common.LoadingIndicator
import com.stayspotter.common.api.ApiClient
import com.stayspotter.model.UserRegisterDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            Register()
        }
    }
}

@Preview
@Composable
private fun Register() {
    val (username, setUsername) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    val (passwordAgain, setPasswordAgain) = remember { mutableStateOf("") }
    val (passwordsMatch, setPasswordsMatch) = remember { mutableStateOf(false) }

    val (isLoading, setIsLoading) = remember { mutableStateOf(false) }

    val context = LocalContext.current

    val registerOnClick: () -> Unit = {
        setIsLoading(true)
        val userRegisterDto = UserRegisterDto(username, password)

        val call = ApiClient.apiService.register(userRegisterDto)

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    setIsLoading(false)
                    Toast.makeText(context, "Account created!", Toast.LENGTH_SHORT).show()
                }

                setIsLoading(false)
                Toast.makeText(context, "Error creating account...", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                setIsLoading(false)
                Toast.makeText(context, "Error creating account...", Toast.LENGTH_SHORT).show()
                Log.e("LoginActivity", "Error creating account", t)
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
        RegisterPageTitle()
        Spacer(modifier = Modifier.padding(4 * Constant.STD_PADDING))
        FormField("Enter username...", username, setUsername)
        Spacer(modifier = Modifier.padding(Constant.STD_PADDING))
        FormField(
            "Enter password...", password, setPassword,
            PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.padding(Constant.STD_PADDING))
//        if (!passwordsMatch) {
//            Row {
//                SimpleText(
//                    text = "Passwords do not match!",
//                    textAlign = TextAlign.Start
//                )
//            }
//        }
        FormField(
            "Enter password again...", passwordAgain, setPasswordAgain,
            PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.padding(Constant.STD_PADDING))
        GenericFormButton("Create account!", Constant.EDGE_BLUE) {}

    }

    LoadingIndicator(isLoading)
}

@Preview
@Composable
private fun RegisterPageTitle() {
    val tagline = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Constant.TEXT_GRAY)) {
            append("Create an account with ")
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
            modifier = Modifier.padding(Constant.STD_PADDING),
            textAlign = TextAlign.Center
        )
    }
}
