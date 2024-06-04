package com.stayspotter.search

import android.content.Context
import android.content.Intent
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.OutlinedFlag
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
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
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.stayspotter.Constant
import com.stayspotter.R
import com.stayspotter.common.Chip
import com.stayspotter.common.FormField
import com.stayspotter.common.GenericSquircleButton
import com.stayspotter.common.GenericButton
import com.stayspotter.common.IconField
import com.stayspotter.common.IconFieldV2
import com.stayspotter.common.LoadingIndicator
import com.stayspotter.common.api.ApiClient
import com.stayspotter.helper.convertEpochToDate
import com.stayspotter.model.Stay
import com.stayspotter.model.StayRequestDto
import com.stayspotter.stays.StaysFoundActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStreamReader

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            EmbeddedSearch()
        }
    }


}

@Composable
private fun ButtonSpacer() {
    // not actually 100% aligned but looks better
    val spacing = Constant.STD_LENGTH / 4 - Constant.STD_HEIGHT + 10.dp

    Spacer(modifier = Modifier.size(spacing))
}

@Preview
@Composable
fun EmbeddedSearch(jwt: String = "jwt") {
    val viewModel = remember { SearchActivityViewModel() }
    val (isLoading, setIsLoading) = remember { mutableStateOf(false) }
    viewModel.jsonWebToken.value = jwt

    val (destination, setDestination) = remember { mutableStateOf("") }
    val (country, setCountry) = remember { mutableStateOf("") }

    val filters = remember {
        mutableStateMapOf(
            Constant.FILTER_KEY_CITY to "",
            Constant.FILTER_KEY_ADULTS to "",
            Constant.FILTER_KEY_ROOMS to "",
            Constant.FILTER_KEY_PRICE_RANGE to "",
            Constant.FILTER_KEY_STAY_PERIOD to "",
        )
    }

    val context = LocalContext.current

    if (viewModel.cities.value.isEmpty()) {
        LoadCities({ viewModel.countries.value = it }, { viewModel.countriesMap = it })
    }

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

//        SearchBar(destination, setDestination)
        SearchBarV2(destination, setDestination, viewModel.cities.value,
            icon = Icons.Default.LocationCity, placeholder = "City...")
        Spacer(modifier = Modifier.size(Constant.STD_PADDING))
        SearchBarV2(country, setCountry, viewModel.countries.value, "Country...",
            icon = Icons.Default.OutlinedFlag) {
            viewModel.cities.value = viewModel.countriesMap[country] ?: emptyList()
        }

        Filters(filters, viewModel)

        Spacer(modifier = Modifier.size(Constant.STD_PADDING))

        FilterChips(filters)
    }
    OverlayButton(context, viewModel, destination, setIsLoading)
    LoadingIndicator(isLoading)
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
private fun FilterChips(
    filters: SnapshotStateMap<String, String>
) {
    filters.forEach {
        if (it.value == "") return@forEach

        Chip(text = it.value) {
            filters.replace(it.key, "")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Filters(filters: MutableMap<String, String>, viewModel: SearchActivityViewModel) {

//    val setSelectedStartDate: (Long) -> Unit = {
//        viewModel.selectedStartDate.longValue = it
//    }
//
//    val setSelectedEndDate: (Long) -> Unit = {
//        viewModel.selectedEndDate.longValue = it
//    }

    val setSelectedStartDate: (String) -> Unit = {
        viewModel.selectedStartDate.value = it
    }

    val setSelectedEndDate: (String) -> Unit = {
        viewModel.selectedEndDate.value = it
    }

    val setShowCalendarDialog: (Boolean) -> Unit = {
        viewModel.showCalendarDialog.value = it
    }

    val setNumberOfPeople: (String) -> Unit = {
        viewModel.numberOfPeople.value = it
    }

    val setShowNumberOfPeopleDialog: (Boolean) -> Unit = {
        viewModel.showNumberOfPeopleDialog.value = it
    }

    val setMinPrice: (String) -> Unit = {
        viewModel.minPrice.value = it
    }

    val setMaxPrice: (String) -> Unit = {
        viewModel.maxPrice.value = it
    }

    val setShowPriceRangeDialog: (Boolean) -> Unit = {
        viewModel.showPriceRangeDialog.value = it
    }

    val datePickerState = rememberDateRangePickerState()

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

        if (viewModel.showCalendarDialog.value) {
            CalendarDialog(
                setShowCalendarDialog, datePickerState, setSelectedStartDate, setSelectedEndDate,
                filters
            )
        }

        if (viewModel.showNumberOfPeopleDialog.value) {
            NumberOfPeopleDialog(
                setShowNumberOfPeopleDialog,
                viewModel.numberOfPeople.value,
                setNumberOfPeople,
                filters,
            )
        }

        if (viewModel.showPriceRangeDialog.value) {
            PriceRangeDialog(
                setShowPriceRangeDialog,
                viewModel.minPrice.value,
                setMinPrice,
                viewModel.maxPrice.value,
                setMaxPrice,
                filters,
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
    setSelectedStartDate: (String) -> Unit = {},
    setSelectedEndDate: (String) -> Unit = {},
    filters: MutableMap<String, String> = mutableMapOf(),
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

                            val checkInDate = datePickerState.selectedStartDateMillis?.let {
                                convertEpochToDate(it)
                            }!!

                            val checkOutDate = datePickerState.selectedEndDateMillis?.let {
                                convertEpochToDate(it)
                            }!!

                            filters[Constant.FILTER_KEY_STAY_PERIOD] =
                                "Stay period: $checkInDate - $checkOutDate"

                            setSelectedStartDate(checkInDate)
                            setSelectedEndDate(checkOutDate)

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

@Preview
@Composable
private fun SearchBar(destination: String = "Milano", setDestination: (String) -> Unit = {}) {
    IconField(
        placeholder = "Your destination...",
        field = destination, setField = setDestination
    ) {
        Icon(Icons.Default.Search, contentDescription = "Search", tint = Constant.TEXT_GRAY)
    }
}

@Composable
private fun SearchBarV2(
    destination: String = "Milano",
    setDestination: (String) -> Unit = {},
    cityList: List<String> = emptyList(),
    placeholder: String = "Your destination...",
    icon: ImageVector = Icons.Default.Search,
    dropdownMenuItemOnClick: () -> Unit = {}
) {
    IconFieldV2(
        placeholder = placeholder,
        field = destination, setField = setDestination,
        suggestions = cityList,
        dropdownItemOnClick = dropdownMenuItemOnClick
    ) {
        Icon(icon, contentDescription = "Search", tint = Constant.TEXT_GRAY)
    }
}

@Composable
private fun LoadCities(setCountries: (List<String>) -> Unit = {},
                       setCountriesMap: (MutableMap<String, List<String>>) -> Unit = {}) {
    Log.i("SearchActivity", "Loading cities list...")

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            val reader = BufferedReader(InputStreamReader(context
                .resources.openRawResource(R.raw.new_countries)))
            val fileContents = reader.use { it.readText() }
            val countriesType = object : TypeToken<Map<String, List<String>>>() {}.type
            val countries: Map<String, List<String>> = Gson().fromJson(fileContents, countriesType)

            launch(Dispatchers.Main) {
                setCountries(countries.keys.toList())
                setCountriesMap(countries.toMutableMap())
            }
        }
    }
}

@Composable
private fun OverlayButton(
    context: Context,
    viewModel: SearchActivityViewModel,
    destination: String,
    setIsLoading: (Boolean) -> Unit
) {
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
        ) {
            setIsLoading(true)
            findStays(context, viewModel, destination, setIsLoading)
        }
    }
}

private fun findStays(
    context: Context,
    viewModel: SearchActivityViewModel,
    destination: String,
    setIsLoading: (Boolean) -> Unit
): Unit {
    val stayRequest = StayRequestDto()


    stayRequest.city = destination
    stayRequest.adults = viewModel.numberOfPeople.value.toIntOrNull() ?: 2
    stayRequest.priceRangeStart = viewModel.minPrice.value.toIntOrNull() ?: 0
    stayRequest.priceRangeEnd = viewModel.maxPrice.value.toIntOrNull() ?: 1000

    stayRequest.checkIn = viewModel.selectedStartDate.value
    stayRequest.checkOut = viewModel.selectedEndDate.value

    val call = stayRequest.let {
        ApiClient.apiService.findStay(it, "Bearer ${viewModel.jsonWebToken.value}")
    }
    val intent = Intent(context, StaysFoundActivity::class.java)
    intent.putExtra(Constant.INTENT_KEY_CITY, destination)
    intent.putExtra(Constant.INTENT_KEY_STAY_SEARCH_REQUEST, stayRequest)
    intent.putExtra(Constant.INTENT_KEY_JWT, viewModel.jsonWebToken.value)

    call.enqueue(object : retrofit2.Callback<List<Stay>> {
        override fun onResponse(call: Call<List<Stay>>, response: Response<List<Stay>>) {
            if (response.isSuccessful) {
                val stayList = response.body()!!

                intent.putExtra(Constant.INTENT_KEY_STAYS, stayList.toTypedArray())
                setIsLoading(false)
                context.startActivity(intent)
                return
            }

            setIsLoading(false)
            Toast.makeText(
                context, "Error while calling scraper...",
                Toast.LENGTH_SHORT
            ).show()
        }

        override fun onFailure(call: Call<List<Stay>>, t: Throwable) {
            setIsLoading(false)
            Toast.makeText(
                context, "Error while calling scraper...",
                Toast.LENGTH_SHORT
            ).show()
            Log.e("SearchActivity", "Error while calling scraper...", t)
        }
    })
}

@Preview
@Composable
private fun NumberOfPeopleDialog(
    setShowDialog: (Boolean) -> Unit = {}, number: String = "2",
    setNumber: (String) -> Unit = {},
    filters: MutableMap<String, String> = mutableMapOf(),
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
                        if (it.isDigitsOnly()) {
                            setNumber(it)
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
                            when (number) {
                                "1" -> filters[Constant.FILTER_KEY_ADULTS] = "$number person"
                                else -> filters[Constant.FILTER_KEY_ADULTS] = "$number people"
                            }

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
    filters: MutableMap<String, String> = mutableMapOf(),
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
                            if (it.isDigitsOnly()) {
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
                            filters[Constant.FILTER_KEY_PRICE_RANGE] =
                                "Price range: $$minPrice - $$maxPrice"

                            setShowDialog(false)
                        }
                    )
                }
            }
        }
    }
}

class SearchActivityViewModel : ViewModel() {
    val jsonWebToken = mutableStateOf("")

    // filters + destination
//    val destination = mutableStateOf("")

    //    val selectedStartDate = mutableLongStateOf(0L)
//    val selectedEndDate = mutableLongStateOf(0L )
    val selectedStartDate = mutableStateOf("")
    val selectedEndDate = mutableStateOf("")
    val showCalendarDialog = mutableStateOf(false)

    val numberOfPeople = mutableStateOf("")
    val showNumberOfPeopleDialog = mutableStateOf(false)

    val minPrice = mutableStateOf("")
    val maxPrice = mutableStateOf("")
    val showPriceRangeDialog = mutableStateOf(false)

    val cities = mutableStateOf<List<String>>(emptyList())
    val countries = mutableStateOf<List<String>>(emptyList())

    var countriesMap = mutableMapOf<String, List<String>>()
}
