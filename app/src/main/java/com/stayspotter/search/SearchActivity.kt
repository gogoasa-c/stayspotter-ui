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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
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
import com.stayspotter.common.Chip
import com.stayspotter.common.GenericSquircleButton
import com.stayspotter.common.GenericButton
import com.stayspotter.common.IconField
import com.stayspotter.common.NavigationBar
import com.stayspotter.ui.theme.NavBarTheme
import java.time.Instant
import java.time.LocalDateTime
import java.util.TimeZone

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
        verticalArrangement = Arrangement.Bottom,
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
        Spacer(modifier = Modifier.padding(Constant.STD_PADDING * 4))

        val (destination, setDestination) = remember { mutableStateOf("") }
        val (filters, setFilters) = remember {
            mutableStateOf(
                listOf(
                    "14th Feb. 2024 - 18th Feb. 2024",
                    "2 persons",
                    "€€€",
                    "Sights"
                )
            )
        }

        SearchBar(destination, setDestination)
        Filters()
        Spacer(modifier = Modifier.size(Constant.STD_PADDING))

        FilterChips(filters, setFilters)
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
private fun FilterChips(filters: List<String>, setFilters: (List<String>) -> Unit) {
    filters.forEach {
        Chip(text = it)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Filters() {
    val (selectedDate, setSelectedDate) = remember { mutableLongStateOf(0L) }
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()

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
        }, onClick = {
            setShowDialog(true)
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

        if (showDialog) {

            NavBarTheme {
                DatePickerDialog(onDismissRequest = { setShowDialog(false) }, confirmButton = {
                    datePickerState.selectedDateMillis?.let { setSelectedDate(it) }
                }) {
                    DatePicker(state = datePickerState)
                }
            }


        }
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