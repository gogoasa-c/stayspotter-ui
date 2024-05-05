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
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.stayspotter.Constant
import com.stayspotter.search.EmbeddedSearch
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
    val viewModel = DefaultActivityViewModel()
    viewModel.jsonWebToken = intent.getStringExtra(Constant.INTENT_KEY_JWT)
        ?: throw AccessDeniedException("No JWT provided")


    when (viewModel.selected) {
        Constant.NAVBAR_ITEM_FAV -> {

        }

        Constant.NAVBAR_ITEM_SEARCH -> {
            EmbeddedSearch(viewModel.jsonWebToken)
        }

        Constant.NAVBAR_ITEM_PROFILE -> {
            EmbeddedProfile()
        }
    }

    Navbar(viewModel.selected) { viewModel.selected = it }
}

class DefaultActivityViewModel : ViewModel() {
    var selected by mutableIntStateOf(Constant.NAVBAR_ITEM_SEARCH)

    var jsonWebToken by mutableStateOf("")
}