package com.stayspotter.common

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.stayspotter.Constant
import com.stayspotter.R
import com.stayspotter.common.api.ApiClient
import com.stayspotter.helper.convertStayToFavouriteStay
import com.stayspotter.model.FavouriteStay
import com.stayspotter.model.Stay
import com.stayspotter.model.StayRequestDto
import com.stayspotter.ui.theme.NavBarTheme
import retrofit2.Call
import retrofit2.Response

@Composable
fun GenericFormButton(text: String, color: Color, onClick: () -> Unit = {}) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(Constant.STD_LENGTH, Constant.STD_HEIGHT),
        colors = ButtonDefaults.buttonColors(backgroundColor = color),
        shape = RoundedCornerShape(Constant.CORNER_RADIUS)
    ) {
        Text(
            text = text,
            fontFamily = FontFamily(Font(R.font.inter)),
            fontSize = Constant.STD_FONT_SIZE
        )
    }
}

@Composable
fun GenericSquircleButton(color: Color, icon: @Composable () -> Unit, onClick: () -> Unit = {}) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(Constant.STD_HEIGHT, Constant.STD_HEIGHT),
        colors = ButtonDefaults.buttonColors(backgroundColor = color),
        shape = RoundedCornerShape(Constant.CORNER_RADIUS)
    ) {
        icon()
    }
}


@Composable
fun FormField(
    placeholder: String,
    field: String,
    setField: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text
) {

    TextField(
        modifier = Modifier
            .size(Constant.STD_LENGTH, Constant.STD_HEIGHT),
        value = field,
        onValueChange = {
            setField(it)
        },
        shape = RoundedCornerShape(Constant.CORNER_RADIUS),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = Constant.PETRIFIED_BLUE,
            textColor = Constant.TEXT_GRAY
        ),
        placeholder = {
            Text(text = placeholder, color = Constant.FADED_GRAY, fontSize = Constant.STD_FONT_SIZE)
        },
        textStyle = TextStyle.Default.copy(fontSize = Constant.STD_FONT_SIZE),
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

@Preview
@Composable
fun FormField(
    placeholder: String = "", field: String = "", setField: (String) -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    length: Dp = Constant.STD_LENGTH, height: Dp = Constant.STD_HEIGHT,
    textAlign: TextAlign = TextAlign.Start, keyboardType: KeyboardType = KeyboardType.Text
) {
    TextField(
        modifier = Modifier
            .size(length, height),
        value = field,
        onValueChange = {
            setField(it)
        },
        shape = RoundedCornerShape(Constant.CORNER_RADIUS),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = Constant.PETRIFIED_BLUE,
            textColor = Constant.TEXT_GRAY
        ),
        placeholder = {
            Text(
                text = placeholder,
                color = Constant.FADED_GRAY,
                fontSize = Constant.STD_FONT_SIZE,
                textAlign = textAlign
            )
        },
        textStyle = TextStyle.Default.copy(fontSize = Constant.STD_FONT_SIZE),
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

@Composable
fun IconField(
    placeholder: String, field: String, setField: (String) -> Unit,
    icon: @Composable () -> Unit
) {

    TextField(
        modifier = Modifier
            .size(Constant.STD_LENGTH, Constant.STD_HEIGHT),
        value = field,
        onValueChange = {
            setField(it)
        },
        shape = RoundedCornerShape(Constant.CORNER_RADIUS),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = Constant.PETRIFIED_BLUE,
            textColor = Constant.TEXT_GRAY
        ),
        placeholder = {
            Text(text = placeholder, color = Constant.FADED_GRAY, fontSize = Constant.STD_FONT_SIZE)
        },
        leadingIcon = icon,
        textStyle = TextStyle.Default.copy(fontSize = Constant.STD_FONT_SIZE)
    )
}

@Composable
fun NavigationBar(selected: Int, setSelected: (Int) -> Unit) {
    NavBarTheme {
        androidx.compose.material3.NavigationBar(
            modifier = Modifier.size(Constant.STD_LENGTH * 2, Constant.NAVBAR_HEIGHT),
        ) {
//            val (selected, setSelected) = remember { mutableIntStateOf(1) }
            NavigationBarItem(selected = selected == 0, onClick = { setSelected(0) }, icon = {
                Icon(
                    Icons.Outlined.FavoriteBorder,
                    contentDescription = "Profile",
                    tint = Constant.TEXT_GRAY
                )
            }, colors = NavigationBarItemDefaults.colors(
                indicatorColor = Constant.PETRIFIED_BLUE,
            )
            )
            NavigationBarItem(selected = selected == 1, onClick = { setSelected(1) }, icon = {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = Constant.TEXT_GRAY
                )
            }, colors = NavigationBarItemDefaults.colors(
                indicatorColor = Constant.PETRIFIED_BLUE,
            )
            )
            NavigationBarItem(selected = selected == 2, onClick = { setSelected(2) }, icon = {
                Icon(
                    Icons.Outlined.Person,
                    contentDescription = "Profile",
                    tint = Constant.TEXT_GRAY
                )
            }, colors = NavigationBarItemDefaults.colors(
                indicatorColor = Constant.PETRIFIED_BLUE,
            )
            )
        }
    }
}

@Composable
fun GenericButton(length: Dp, height: Dp, color: Color, text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(length, height),
        colors = ButtonDefaults.buttonColors(backgroundColor = color),
        shape = RoundedCornerShape(Constant.CORNER_RADIUS),
    ) {
        Text(text = text, color = Constant.TEXT_GRAY, fontSize = Constant.STD_FONT_SIZE)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chip(text: String, onClick: () -> Unit) {
    InputChip(
        modifier = Modifier.width(Constant.STD_LENGTH),
        shape = RoundedCornerShape(Constant.CORNER_RADIUS),
        selected = true,
        onClick = {},
        label = { Text(text, color = Constant.TEXT_GRAY, modifier = Modifier.fillMaxWidth(0.9f)) },
        trailingIcon = {
            Icon(
                Icons.Default.Close,
                contentDescription = "Close icon",
                tint = Constant.TEXT_GRAY,
                modifier = Modifier
                    .size(InputChipDefaults.IconSize)
                    .clickable {
                        onClick()
                    }
            )
        },
        colors = InputChipDefaults.inputChipColors(
            selectedContainerColor = Constant.DARK_GRAY,
        )
    )
}

@Composable
fun Navbar(selected: Int, setSelected: (Int) -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NavigationBar(selected, setSelected)
    }
}

@Preview
@Composable
fun SimpleText(
    modifier: Modifier = Modifier,
    text: String = "text",
    textAlign: TextAlign = TextAlign.Center,
    fontSize: TextUnit = Constant.STD_FONT_SIZE,
    color: Color = Constant.TEXT_GRAY
) {
    Text(
        text = text,
        color = color,
        fontSize = fontSize,
        fontFamily = FontFamily(Font(R.font.inter)),
        textAlign = textAlign,
        modifier = modifier
    )
}

@Preview
@Composable
fun LoadingIndicator(isLoading: Boolean = true) {
    if (isLoading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable(enabled = false) {}
                .background(Constant.BACKGROUND_COLOR.copy(alpha = 0.5f)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                color = Constant.EDGE_BLUE
            )
        }
    }
}

@Preview
@Composable
fun StayCard(
    stay: Stay = Stay(
        "Magnificent Hotel",
        "Booking",
        "https://cf.bstatic.com/xdata/images/hotel/max1024x768/519461821.jpg?k=0e907fea49d593678f35f965ccffc4b220e0f709848c14347ba8f7d9800b698d&o=&hp=1",
        "$100",
        23.54,
        12.34
    ),
    stayRequestDto: StayRequestDto = StayRequestDto(),
    jwt: String = "jwt",
    context: Context = LocalContext.current
) {
    val isFavorite = remember { mutableStateOf(false) }

    fun saveToFavourites(
        stay: Stay, stayRequestDto: StayRequestDto,
        jwt: String, context: Context
    ): Unit {
        val favouriteStay = convertStayToFavouriteStay(stay, stayRequestDto)

        val call = ApiClient.apiService.saveToFavourite(
            favouriteStay,
            "Bearer $jwt"
        )

        call.enqueue(object : retrofit2.Callback<Unit> {
            override fun onResponse(
                call: Call<Unit>,
                response: Response<Unit>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        context, "Stay saved to favourites!",
                        Toast.LENGTH_SHORT
                    ).show()

                    isFavorite.value = !isFavorite.value


                    return
                }

                Log.e("FavouriteStaysActivity", "Error while saving favourite stay...")
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Toast.makeText(
                    context, "Error while saving favourite stay...",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("FavouriteStaysActivity", "Error while saving favourite stay...", t)
            }
        })
    }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .clip(RoundedCornerShape(Constant.CORNER_RADIUS))
                .fillMaxWidth(0.95f)
                .height(Constant.PICTURE_HEIGHT)
                .padding(Constant.STD_PADDING)
                .align(Alignment.Center),
            colors = CardDefaults.cardColors(containerColor = Constant.DARK_GRAY)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                val uriHandler = LocalUriHandler.current
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { uriHandler.openUri(stay.link) },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SubcomposeAsyncImage(
                        modifier = Modifier.fillMaxWidth(),
                        model = stay.photoUrl,
                        contentDescription = "Picture of a stay",
                        contentScale = ContentScale.Crop,
                        loading = {
                            CircularProgressIndicator(
                                trackColor = Constant.LIGHT_EDGE_BLUE,
                                color = Constant.EDGE_BLUE,
                                modifier = Modifier.scale(0.1f),
                                strokeWidth = 15.dp
                            )
                        }
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Constant.BACKGROUND_COLOR.copy(alpha = 0.2f))
                ) {
                    SimpleText(
                        text = stay.name,
                        modifier = Modifier.padding(
                            start = Constant.STD_PADDING,
                            top = Constant.STD_PADDING
                        ),
                        fontSize = Constant.STD_TITLE_FONT_SIZE
                    )
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.Start
                ) {
                    SimpleText(
                        text = "${stay.price}",
                        modifier = Modifier.padding(Constant.STD_PADDING)
                    )
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.End
                ) {
                    androidx.compose.material.Icon(
                        imageVector = if(!isFavorite.value) Icons.Outlined.FavoriteBorder
                                        else Icons.Filled.Favorite,
                        contentDescription = "Favourite",
                        tint = Constant.TEXT_GRAY,
                        modifier = Modifier
                            .padding(Constant.STD_PADDING)
                            .size(Constant.STD_SQUARE_ICON_LENGTH)
                            .clickable(onClick = {
                                saveToFavourites(stay, stayRequestDto, jwt, context)
                            })
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun FavouritedStayCard(
    stay: FavouriteStay = FavouriteStay(
        "Alex Apartment Sibiu",
        "Sibiu",
        "https://www.booking.com/hotel/ro/alex-apartment-sibiu-sibiu.en-gb.html?aid=304142&label=gen173nr-1FCAQoggJCDHNlYXJjaF9zaWJpdUgzWARowAGIAQGYAQm4ARfIAQ_YAQHoAQH4AQOIAgGoAgO4AqLPmbIGwAIB0gIkNmU4NGUxMDktNDRlZC00NzVlLTgzMTItOWYxNzIwMDkzMzE52AIF4AIB&ucfs=1&arphpl=1&checkin=2024-07-20&checkout=2024-07-23&group_adults=2&req_adults=2&no_rooms=1&group_children=0&req_children=0&hpos=1&hapos=1&sr_order=popularity&nflt=price%3DUSD-40-60-1&srpvid=edf78d910f7b0166&srepoch=1715890084&all_sr_blocks=1194052401_391707672_2_0_0&highlighted_blocks=1194052401_391707672_2_0_0&matching_block_id=1194052401_391707672_2_0_0&sr_pri_blocks=1194052401_391707672_2_0_0__61300&from=searchresults",
        "https://cf.bstatic.com/xdata/images/hotel/max1024x768/549329193.jpg?k=028cf9826fa1f909f748706791d42fe5f98232a98eac09e579d6d75c9ae1fa18&o=&hp=1",
        "613",
        45.87,
        24.14,
        2,
        2,
        "2024-07-20",
        "2024-07-23"
    )
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .clip(RoundedCornerShape(Constant.CORNER_RADIUS))
                .fillMaxWidth(0.95f)
                .height(Constant.PICTURE_HEIGHT)
                .padding(Constant.STD_PADDING)
                .align(Alignment.Center),
            colors = CardDefaults.cardColors(containerColor = Constant.DARK_GRAY)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                val uriHandler = LocalUriHandler.current
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { uriHandler.openUri(stay.link) },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SubcomposeAsyncImage(
                        modifier = Modifier.fillMaxWidth(),
                        model = stay.photoUrl,
                        contentDescription = "Picture of a stay",
                        contentScale = ContentScale.Crop,
                        loading = {
                            CircularProgressIndicator(
                                trackColor = Constant.LIGHT_EDGE_BLUE,
                                color = Constant.EDGE_BLUE,
                                modifier = Modifier.scale(0.1f),
                                strokeWidth = 15.dp
                            )
                        }
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Constant.BACKGROUND_COLOR.copy(alpha = 0.2f))
                ) {
                    SimpleText(
                        text = stay.name,
                        modifier = Modifier.padding(
                            start = Constant.STD_PADDING,
                            top = Constant.STD_PADDING
                        ),
                        fontSize = Constant.STD_TITLE_FONT_SIZE
                    )
                    SimpleText(
                        text = "${stay.city}: ${stay.checkIn} — ${stay.checkOut}",
                        modifier = Modifier.padding(
                            start = Constant.STD_PADDING,
                            top = Constant.STD_PADDING
                        ),
                        fontSize = Constant.STD_SMALL_FONT_SIZE
                    )
                    SimpleText(
                        text = "${stay.adults} adults",
                        modifier = Modifier.padding(
                            start = Constant.STD_PADDING,
                            top = Constant.STD_PADDING
                        ),
                        fontSize = Constant.STD_SMALL_FONT_SIZE
                    )
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.Start
                ) {
                    SimpleText(
                        text = stay.price,
                        modifier = Modifier.padding(Constant.STD_PADDING)
                    )
                }
            }
        }

    }
}