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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.PeopleAlt
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.stayspotter.Constant
import com.stayspotter.R
import com.stayspotter.common.Chip
import com.stayspotter.common.GenericSquircleButton
import com.stayspotter.common.GenericButton
import com.stayspotter.common.IconField
import com.stayspotter.common.NavigationBar
import com.stayspotter.helper.convertEpochToDate
import com.stayspotter.ui.theme.NavBarTheme

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
        Filters(filters, setFilters)
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
private fun Filters(filters: List<String>, setFilters: (List<String>) -> Unit) {
    val (selectedDate, setSelectedDate) = remember { mutableLongStateOf(0L) }
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    val datePickerState = rememberDateRangePickerState()

    val (numberOfPeople, setNumberOfPeople) = remember { mutableIntStateOf(0) }

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
        }) {

        }
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
            CalendarDialog(setShowDialog, datePickerState, setSelectedDate, filters, setFilters)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CalendarDialog(
    setShowDialog: (Boolean) -> Unit, datePickerState: DateRangePickerState,
    setSelectedDate: (Long) -> Unit, filters: List<String>,
    setFilters: (List<String>) -> Unit
) {
    Dialog(onDismissRequest = { setShowDialog(false) }, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Surface(
            color = Constant.BACKGROUND_COLOR,
            modifier = Modifier.fillMaxWidth(),

        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Constant.BACKGROUND_COLOR)
                        .padding(Constant.STD_PADDING),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GenericButton(
                        length = Constant.STD_LENGTH,
                        height = Constant.STD_HEIGHT,
                        color = Constant.PETRIFIED_BLUE,
                        text = "Select date range",
                        onClick = {
                            val date = "${datePickerState.selectedStartDateMillis?.let {
                                convertEpochToDate(it)
                            }} - ${datePickerState.selectedEndDateMillis?.let {
                                convertEpochToDate(it)
                            }}"

                            setFilters(filters.toMutableList().apply {
                                set(0, date)
                            })

                            setShowDialog(false)
                        }
                    )
                }
                DateRangePicker(
                    state = datePickerState,
                    title = {
                        Text(
                            text = "",
                            color = Constant.EDGE_BLUE,
                            fontSize = Constant.STD_FONT_SIZE,
                            modifier = Modifier.padding(Constant.STD_PADDING),
                            fontFamily = Constant.FONT_FAMILY,
                        )
                    },
                    headline = {
                        Text(
                            "Dates for your stay:", color = Constant.TEXT_GRAY,
                            fontSize = Constant.STD_FONT_SIZE, textAlign = TextAlign.Center,
                            modifier = Modifier.padding(Constant.STD_PADDING),
                            fontFamily = Constant.FONT_FAMILY,
                        )
                    },
                    colors = DatePickerDefaults.colors(
                        containerColor = Constant.BACKGROUND_COLOR,
                        selectedDayContentColor = Constant.EDGE_BLUE,
                        selectedDayContainerColor = Constant.LIGHT_EDGE_BLUE,
                        currentYearContentColor = Constant.TEXT_GRAY,
                        todayContentColor = Constant.EDGE_BLUE,
                        todayDateBorderColor = Constant.EDGE_BLUE,
                        titleContentColor = Constant.TEXT_GRAY,
                        dayContentColor = Constant.TEXT_GRAY,
                        weekdayContentColor = Constant.TEXT_GRAY,
                        subheadContentColor = Constant.TEXT_GRAY,
                        yearContentColor = Constant.TEXT_GRAY,
                        dayInSelectionRangeContainerColor = Constant.LIGHT_EDGE_BLUE,
                        dayInSelectionRangeContentColor = Constant.EDGE_BLUE,
                    )
                )
            }


        }

    }


//        }
//    }
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