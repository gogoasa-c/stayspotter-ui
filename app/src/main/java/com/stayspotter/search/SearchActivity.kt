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
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.PeopleAlt
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stayspotter.Constant
import com.stayspotter.R
import com.stayspotter.common.GenericSquircleButton
import com.stayspotter.common.GenericButton
import com.stayspotter.common.IconField
import com.stayspotter.common.NavigationBar

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PreviewSearch()
        }
    }


}

@Composable
fun ButtonSpacer() {
    // not actually 100% aligned but looks better
    val spacing = Constant.STD_LENGTH / 4 - Constant.STD_HEIGHT + 10.dp

    Spacer(modifier = Modifier.size(spacing))
}

@Composable
fun Navbar() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom, // Add this line
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NavigationBar()
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
        
        Logo()

        val (destination, setDestination) = remember { mutableStateOf("") }

        Spacer(modifier = Modifier.padding(Constant.STD_PADDING * 4))
        SearchBar(destination, setDestination)

        Filters()
    }

    OverlayButton()

    Navbar()
}

@Composable
private fun Logo() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        val staySpotterLogo = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Constant.LIGHT_EDGE_BLUE)) {
                append("Stay")
            }
            withStyle(style = SpanStyle(color = Constant.EDGE_BLUE)) {
                append("Spotter")
            }
        }

        Text(
            modifier = Modifier.padding(top = Constant.STD_PADDING * 2),
            text = staySpotterLogo,
            fontFamily = FontFamily(Font(R.font.inter_semibold)),
            fontSize = Constant.STD_FONT_SIZE * 2
        )
    }
}

@Composable
private fun Filters() {
    Row(
        modifier = Modifier.padding(top = Constant.STD_PADDING * 4),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        GenericSquircleButton(Constant.PETRIFIED_BLUE, icon = {
            Icon(
                Icons.Default.CalendarMonth, contentDescription = "Calendar",
                tint = Constant.TEXT_GRAY,
            )
        })
        ButtonSpacer()

        GenericSquircleButton(Constant.PETRIFIED_BLUE, icon = {
            Icon(
                Icons.Outlined.PeopleAlt, contentDescription = "Persons",
                tint = Constant.TEXT_GRAY,
            )
        })
        ButtonSpacer()

        GenericSquircleButton(Constant.PETRIFIED_BLUE, icon = {
            Icon(
                Icons.Default.AttachMoney, contentDescription = "Price",
                tint = Constant.TEXT_GRAY,
            )
        })
        ButtonSpacer()

        GenericSquircleButton(Constant.PETRIFIED_BLUE, icon = {
            Icon(
                Icons.Outlined.RemoveRedEye, contentDescription = "Sights",
                tint = Constant.TEXT_GRAY,
            )
        })
    }
}

@Composable
private fun SearchBar(destination: String, setDestination: (String) -> Unit) {
    IconField(
        placeholder = "Your destination...",
        field = destination, setField = setDestination
    ) {
        Icon(Icons.Default.Search, contentDescription = "Search", tint = Constant.TEXT_GRAY)
    }
}

@Composable
private fun OverlayButton() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(Constant.OVERLAY_BUTTON_OFFSET))
        GenericButton(
            length = Constant.STD_LENGTH,
            height = Constant.STD_HEIGHT,
            color = Constant.PETRIFIED_BLUE,
            text = "Spot your stay!",
            onClick = {}
        )
    }
}