package com.stayspotter.common

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.stayspotter.Constant
import com.stayspotter.model.FavouriteStay
import com.stayspotter.search.EmbeddedSearch
import com.stayspotter.stays.FavouriteStaysEmbedded
import com.stayspotter.user.EmbeddedProfile
import java.nio.file.AccessDeniedException

class DefaultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            CommonActivity(intent)
        }
    }
}

@Composable
@Preview
private fun CommonActivity(intent: Intent = Intent()) {
    val (selected, setSelected) = remember { mutableIntStateOf(Constant.NAVBAR_ITEM_SEARCH) }
//    val (jsonWebToken, setJsonWebToken) = remember { mutableStateOf("") }
    val jsonWebToken = remember { mutableStateOf("")}
    val (stays, setStays) = remember { mutableStateOf(listOf<FavouriteStay>()) }
//    setJsonWebToken(intent.getStringExtra(Constant.INTENT_KEY_JWT)
//        ?: throw AccessDeniedException("No JWT provided"))
    jsonWebToken.value = intent.getStringExtra(Constant.INTENT_KEY_JWT)
        ?: throw AccessDeniedException("No JWT provided")

    when (selected) {
        Constant.NAVBAR_ITEM_FAV -> {
            FavouriteStaysEmbedded(jwt = jsonWebToken.value, setStays = setStays, stays = stays)
        }

        Constant.NAVBAR_ITEM_SEARCH -> {
            EmbeddedSearch(jsonWebToken.value)
        }

        Constant.NAVBAR_ITEM_PROFILE -> {
            EmbeddedProfile()
        }
    }

    Navbar(selected, setSelected)
}