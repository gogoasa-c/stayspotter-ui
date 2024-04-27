package com.stayspotter.common

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.stayspotter.Constant
import com.stayspotter.search.EmbeddedSearch
import com.stayspotter.user.EmbeddedProfile

class DefaultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            CommonActivity()
        }
    }
}

@Composable
@Preview
private fun CommonActivity() {
    val (selected, setSelected) = remember { mutableIntStateOf(Constant.NAVBAR_ITEM_SEARCH) }

    when (selected) {
        Constant.NAVBAR_ITEM_FAV -> {

        }

        Constant.NAVBAR_ITEM_SEARCH -> {
            EmbeddedSearch()
        }

        Constant.NAVBAR_ITEM_PROFILE -> {
            EmbeddedProfile()
        }
    }

    Navbar(selected, setSelected)
}
