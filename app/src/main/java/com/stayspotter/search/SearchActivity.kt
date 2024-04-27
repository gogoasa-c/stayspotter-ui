package com.stayspotter.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.PeopleAlt
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.text.isDigitsOnly
import com.stayspotter.Constant
import com.stayspotter.R
import com.stayspotter.common.Chip
import com.stayspotter.common.FormField
import com.stayspotter.common.GenericSquircleButton
import com.stayspotter.common.GenericButton
import com.stayspotter.common.IconField
import com.stayspotter.common.Navbar
import com.stayspotter.helper.convertEpochToDate
import com.stayspotter.stays.StaysFoundActivity

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            PreviewSearch()
        }
    }


}

@Composable
private fun ButtonSpacer() {
    // not actually 100% aligned but looks better
    val spacing = Constant.STD_LENGTH / 4 - Constant.STD_HEIGHT + 10.dp

    Spacer(modifier = Modifier.size(spacing))
}

@Composable
@Preview
private fun PreviewSearch() {
    val (destination, setDestination) = remember { mutableStateOf("") }
    val (filters, setFilters) = remember { mutableStateOf(listOf("", "", "", "", "", "", "", "")) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Constant.BACKGROUND_COLOR),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(Constant.STD_PADDING))
        Logo()

        Spacer(modifier = Modifier.padding(Constant.STD_PADDING * 4))

        SearchBar(destination, setDestination)
        Filters(filters, setFilters)

        Spacer(modifier = Modifier.size(Constant.STD_PADDING))

        FilterChips(filters, setFilters)
    }
    OverlayButton(context)
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
        if (it == "") return@forEach

        Chip(text = it) {
            setFilters(filters.toMutableList().apply {
                remove(it)
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Filters(filters: List<String>, setFilters: (List<String>) -> Unit) {
    val (selectedStartDate, setSelectedStartDate) = remember { mutableLongStateOf(0L) }
    val (selectedEndDate, setSelectedEndDate) = remember { mutableLongStateOf(0L) }
    val (showCalendarDialog, setShowCalendarDialog) = remember { mutableStateOf(false) }
    val datePickerState = rememberDateRangePickerState()

    val (numberOfPeople, setNumberOfPeople) = remember { mutableIntStateOf(0) }
    val (showNumberOfPeopleDialog, setShowNumberOfPeopleDialog) = remember { mutableStateOf(false) }

    val (minPrice, setMinPrice) = remember { mutableStateOf("") }
    val (maxPrice, setMaxPrice) = remember { mutableStateOf("") }
    val (showPriceRangeDialog, setShowPriceRangeDialog) = remember { mutableStateOf(false) }

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
            setShowCalendarDialog(true)
        })
        ButtonSpacer()

        GenericSquircleButton(Constant.PETRIFIED_BLUE, icon = {
            Icon(
                Icons.Outlined.PeopleAlt, contentDescription = "Persons",
                tint = Constant.TEXT_GRAY,
            )
        }) { setShowNumberOfPeopleDialog(true) }
        ButtonSpacer()

        GenericSquircleButton(Constant.PETRIFIED_BLUE, icon = {
            Icon(
                Icons.Default.AttachMoney, contentDescription = "Price",
                tint = Constant.TEXT_GRAY,
            )
        }) { setShowPriceRangeDialog(true) }
        ButtonSpacer()

        GenericSquircleButton(Constant.PETRIFIED_BLUE, icon = {
            Icon(
                Icons.Outlined.RemoveRedEye, contentDescription = "Sights",
                tint = Constant.TEXT_GRAY,
            )
        })

        if (showCalendarDialog) {
            CalendarDialog(
                setShowCalendarDialog, datePickerState, setSelectedStartDate, setSelectedEndDate,
                filters, setFilters
            )
        }

        if (showNumberOfPeopleDialog) {
            NumberOfPeopleDialog(
                setShowNumberOfPeopleDialog,
                numberOfPeople.toString(),
                setNumberOfPeople,
                filters,
                setFilters
            )
        }

        if (showPriceRangeDialog) {
            PriceRangeDialog(
                setShowPriceRangeDialog,
                minPrice,
                setMinPrice,
                maxPrice,
                setMaxPrice,
                filters,
                setFilters
            )
        }
    }
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CalendarDialog(
    setShowDialog: (Boolean) -> Unit = {},
    datePickerState: DateRangePickerState = rememberDateRangePickerState(),
    setSelectedStartDate: (Long) -> Unit = {},
    setSelectedEndDate: (Long) -> Unit = {},
    filters: List<String> = listOf(),
    setFilters: (List<String>) -> Unit = {}
) {
    ModalBottomSheet(
        onDismissRequest = { setShowDialog(false) },
        containerColor = Constant.BACKGROUND_COLOR,
        contentColor = Constant.BACKGROUND_COLOR,
    )
    {
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
                            val date = "${
                                datePickerState.selectedStartDateMillis?.let {
                                    convertEpochToDate(it)
                                }
                            } - ${
                                datePickerState.selectedEndDateMillis?.let {
                                    convertEpochToDate(it)
                                }
                            }"

                            setSelectedStartDate(datePickerState.selectedStartDateMillis!!)
                            setSelectedEndDate(datePickerState.selectedEndDateMillis!!)

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
                            modifier = Modifier.padding(start = Constant.STD_PADDING * 3),
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
                    ),
                )
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
private fun OverlayButton(context: Context) {
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
            onClick = {
                context.startActivity(Intent(context, StaysFoundActivity::class.java))
            }
        )
    }
}


@Preview
@Composable
private fun NumberOfPeopleDialog(
    setShowDialog: (Boolean) -> Unit = {}, number: String = "2",
    setNumber: (Int) -> Unit = {},
    filters: List<String> = listOf(),
    setFilters: (List<String>) -> Unit = {}
) {

    Dialog(
        onDismissRequest = { setShowDialog(false) },
    ) {
        Card(
            modifier = Modifier
                .clip(RoundedCornerShape(Constant.CORNER_RADIUS)),
            shape = RoundedCornerShape(Constant.CORNER_RADIUS),
            colors = CardDefaults.cardColors(
                containerColor = Constant.BACKGROUND_COLOR,
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Constant.BACKGROUND_COLOR),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.size(Constant.STD_PADDING))

                Text(
                    text = "Number of people:",
                    color = Constant.TEXT_GRAY,
                    fontSize = Constant.STD_FONT_SIZE,
                    fontFamily = Constant.FONT_FAMILY,
                )

                Spacer(modifier = Modifier.size(Constant.STD_PADDING))

                FormField(
                    placeholder = "2", field = number, setField = {
                        if (it != "" && it.isDigitsOnly()) {
                            setNumber(it.toInt())
                        }
                    },
                    VisualTransformation.None, Constant.SMALL_BUTTON_LENGTH, Constant.STD_HEIGHT,
                    TextAlign.Center, KeyboardType.Number
                )

                Row(
                    modifier = Modifier.padding(Constant.STD_PADDING),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GenericButton(
                        length = Constant.SMALL_BUTTON_LENGTH,
                        height = Constant.STD_HEIGHT,
                        color = Constant.PETRIFIED_BLUE,
                        text = Constant.CONFIRM_MESSAGE,
                        onClick = {
                            setFilters(filters.toMutableList().apply {
                                if (number == "1") {
                                    set(1, "$number person")
                                } else {
                                    set(1, "$number people")
                                }
                            })

                            setShowDialog(false)
                        }
                    )
                }
            }
        }


    }
}

@Preview
@Composable
private fun PriceRangeDialog(
    setShowDialog: (Boolean) -> Unit = {},
    minPrice: String = "",
    setMinPrice: (String) -> Unit = {},
    maxPrice: String = "",
    setMaxPrice: (String) -> Unit = {},
    filters: List<String> = listOf(),
    setFilters: (List<String>) -> Unit = {}
) {
    Dialog(
        onDismissRequest = { setShowDialog(false) },
    ) {
        Card(
            modifier = Modifier
                .clip(RoundedCornerShape(Constant.CORNER_RADIUS)),
            shape = RoundedCornerShape(Constant.CORNER_RADIUS),
            colors = CardDefaults.cardColors(
                containerColor = Constant.BACKGROUND_COLOR,
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.size(Constant.STD_PADDING))
                Text(
                    text = "Price range:",
                    color = Constant.TEXT_GRAY,
                    fontSize = Constant.STD_FONT_SIZE,
                    fontFamily = Constant.FONT_FAMILY,
                )

                Spacer(modifier = Modifier.size(Constant.STD_PADDING))

                Row(
                    modifier = Modifier.padding(Constant.STD_PADDING),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FormField(
                        placeholder = "0",
                        field = minPrice,
                        setField = {
                            if (it != "" && it.isDigitsOnly()) {
                                setMinPrice(it)
                            }
                        },
                        VisualTransformation.None,
                        Constant.LARGE_BUTTON_LENGTH,
                        Constant.STD_HEIGHT,
                        TextAlign.Center,
                        KeyboardType.Number
                    )

                    Spacer(modifier = Modifier.size(Constant.STD_PADDING))

                    FormField(
                        placeholder = "1000",
                        field = maxPrice,
                        setField = {
                            if (it != "" && it.isDigitsOnly()) {
                                setMaxPrice(it)
                            }
                        },
                        VisualTransformation.None,
                        Constant.LARGE_BUTTON_LENGTH,
                        Constant.STD_HEIGHT,
                        TextAlign.Center,
                        KeyboardType.Number
                    )
                }

                Row(
                    modifier = Modifier.padding(Constant.STD_PADDING),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GenericButton(
                        length = Constant.SMALL_BUTTON_LENGTH,
                        height = Constant.STD_HEIGHT,
                        color = Constant.PETRIFIED_BLUE,
                        text = Constant.CONFIRM_MESSAGE,
                        onClick = {
                            val priceRange = "$$minPrice - $$maxPrice"

                            setFilters(filters.toMutableList().apply {
                                set(2, priceRange)
                            })

                            setShowDialog(false)
                        }
                    )
                }
            }
        }
    }
}
