package com.stayspotter.search

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stayspotter.Constant
import com.stayspotter.R
import com.stayspotter.common.FormField
import com.stayspotter.common.IconField

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PreviewSearch()
        }
    }


}

@Composable
@Preview
fun PreviewSearch() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Constant.BACKGROUND_COLOR),

        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val staySpotterLogo = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Constant.LIGHT_EDGE_BLUE)) {
                append("Stay")
            }
            withStyle(style = SpanStyle(color = Constant.EDGE_BLUE)) {
                append("Spotter")
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(top = Constant.STD_PADDING * 2),
                text = staySpotterLogo,
                fontFamily = FontFamily(Font(R.font.inter_semibold)),
                fontSize = Constant.STD_FONT_SIZE * 2
            )
        }

        val (destination, setDestination) = remember { mutableStateOf("") }

        Spacer(modifier = Modifier.padding(Constant.STD_PADDING * 4))
        IconField(placeholder = "Your destination...", field = destination,
            setField = setDestination) {
            Icon(Icons.Default.Search, contentDescription = "Search", tint = Constant.TEXT_GRAY)
        }

    }
}