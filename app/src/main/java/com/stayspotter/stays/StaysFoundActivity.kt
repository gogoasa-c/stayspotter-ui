package com.stayspotter.stays

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.stayspotter.Constant
import com.stayspotter.common.FormField
import com.stayspotter.common.Navbar
import com.stayspotter.common.SimpleText
import com.stayspotter.model.Stay

class StaysFoundActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
//            StaysFound()
        }
    }
}

@Preview
@Composable
private fun StaysFound(
    stayList: List<Stay> = listOf(
        Stay(
            "Magnificent Hotel",
            "Booking",
            "$100",
            listOf()
        ),
        Stay(
            "Magnificent Hotel",
            "Booking",
            "$100",
            listOf()
        ),
        Stay(
            "Magnificent Hotel",
            "Booking",
            "$100",
            listOf()
        ),
    )
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .background(Constant.BACKGROUND_COLOR)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        TopBar()

        Spacer(modifier = Modifier.height(Constant.STD_PADDING))

        stayList.forEach { stay ->
            Divider(color = Constant.TEXT_GRAY)
            StayCard(stay)
        }
    }
    Navbar()
}

@Preview
@Composable
private fun TopBar() {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .background(Constant.BACKGROUND_COLOR)
                .fillMaxWidth()
                .padding(Constant.STD_PADDING)
                .align(Alignment.CenterStart),
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Constant.TEXT_GRAY
                )
            }
            FormField(length = Constant.SMALL_SEARCHBAR_LENGTH)
        }
    }
}

@Preview
@Composable
private fun StayCard(
    stay: Stay = Stay("Magnificent Hotel", "Booking", "$100", listOf())
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
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SubcomposeAsyncImage(
                        model = "https://cf.bstatic.com/xdata/images/hotel/max1280x900/29306268.jpg?k=da6334b40587fdce085e592101ed31fb9efbf82c497c602827a8fe17431c0969&o=&hp=1",
                        contentDescription = "Picture of a stay",
                        contentScale = ContentScale.Crop,
                        loading = {
                            CircularProgressIndicator(
                                trackColor = Constant.LIGHT_EDGE_BLUE,
                                color = Constant.EDGE_BLUE,
                            )
                        }
                    )
                }

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    SimpleText(
                        text = stay.stayName,
                        modifier = Modifier.padding(
                            start = Constant.STD_PADDING,
                            top = Constant.STD_PADDING
                        ),
                        fontSize = Constant.STD_TITLE_FONT_SIZE
                    )
                    SimpleText(
                        text = "Found on ${stay.foundOn}",
                        modifier = Modifier.padding(start = Constant.STD_PADDING),
                        fontSize = Constant.STD_FONT_SIZE
                    )
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.Start
                ) {
                    SimpleText(
                        text = "${stay.pricePerNight} / Night",
                        modifier = Modifier.padding(Constant.STD_PADDING)
                    )
                }
            }
        }

    }

}