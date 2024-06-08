package com.stayspotter.user

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
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
import com.stayspotter.common.LoadingIndicator
import com.stayspotter.common.Navbar
import com.stayspotter.common.SimpleText
import com.stayspotter.common.api.ApiClient
import com.stayspotter.model.UserStatsDto
import com.stayspotter.ui.theme.StaySpotterTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    }
}

@Preview
@Composable
fun EmbeddedProfile(jwt: String = "jwt") {
    val (isLoading, setIsLoading) = remember { mutableStateOf(true) }
    val (userStats, setUserStats) = remember { mutableStateOf(UserStatsDto("User", 0, 0.0)) }

    getUserStats(jwt, LocalContext.current, setIsLoading, setUserStats)
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
                text = userStats.username,
                fontFamily = FontFamily(Font(R.font.inter_semibold)),
                fontSize = Constant.STD_TITLE_FONT_SIZE,
                textAlign = TextAlign.Center,
                color = Constant.TEXT_GRAY
            )
            Spacer(modifier = Modifier.size(10.dp))
            UserStats(userStats.numberOfSearches, userStats.topPercentage)
        }

        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = Constant.STD_PADDING * 2)
        ) {
            ThemeToggle()
        }
    }
    LoadingIndicator(isLoading)
}

private fun getUserStats(
    jwt: String,
    context: Context,
    setIsLoading: (Boolean) -> Unit,
    setUserStats: (UserStatsDto) -> Unit
) {
    val call = ApiClient.apiService.getUserStats("Bearer $jwt")

    call.enqueue(object : Callback<UserStatsDto> {
        override fun onResponse(
            call: Call<UserStatsDto>,
            response: Response<UserStatsDto>
        ) {
            if (response.isSuccessful) {
                setUserStats(
                    response.body()
                        ?: UserStatsDto("User", 0, 0.0)
                )
                setIsLoading(false)
                return
            }

            setIsLoading(false)
            Toast.makeText(
                context, "Error while fetching favourite stays...",
                Toast.LENGTH_SHORT
            ).show()
            Log.e("UserProfileActivity", "Error while fetching favourite stays...")
        }

        override fun onFailure(call: Call<UserStatsDto>, t: Throwable) {
            setIsLoading(false)
            Toast.makeText(
                context, "Error while fetching favourite stays...",
                Toast.LENGTH_SHORT
            ).show()
            Log.e("UserProfileActivity", "Error while fetching favourite stays...", t)
        }
    })
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
            imageVector = Icons.Default.Person,
            contentDescription = "User Icon",
            modifier = Modifier.scale(2.2f),
            tint = Constant.LIGHT_EDGE_BLUE
        )
    }
}

@Composable
@Preview
private fun ThemeToggle() {
    val (isDarkTheme, setIsDarkTheme) = remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(Constant.STD_PADDING)
            .background(Constant.BACKGROUND_COLOR)
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = Constant.STD_PADDING)
        ) {
            StaySpotterTheme {
                Switch(checked = isDarkTheme, onCheckedChange = { setIsDarkTheme(!isDarkTheme) })
            }
        }
        SimpleText(
            text = "Dark Mode",
        )
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