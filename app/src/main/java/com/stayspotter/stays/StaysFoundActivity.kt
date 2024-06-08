package com.stayspotter.stays

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.stayspotter.Constant
import com.stayspotter.common.EmptyStayCardList
import com.stayspotter.common.FormField
import com.stayspotter.common.StayCard
import com.stayspotter.model.Stay
import com.stayspotter.model.StayRequestDto

class StaysFoundActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val stayArray = intent.getParcelableArrayExtra(
            Constant.INTENT_KEY_STAYS,
            Stay::class.java
        )
        val request = intent.getSerializableExtra(
            Constant.INTENT_KEY_STAY_SEARCH_REQUEST,
            StayRequestDto::class.java
        )
        val stayList = stayArray?.toList()
        setContent {
            StaysFound(
                stayList ?: listOf(),
                intent.getStringExtra(Constant.INTENT_KEY_CITY) ?: "",
                request ?: throw IllegalArgumentException("No stay request provided"),
                intent.getStringExtra(Constant.INTENT_KEY_JWT) ?: ""
            )
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
            "https://cf.bstatic.com/xdata/images/hotel/max1024x768/519461821.jpg?k=0e907fea49d593678f35f965ccffc4b220e0f709848c14347ba8f7d9800b698d&o=&hp=1",
            "$100",
            23.45,
            12.34
        ),
        Stay(
            "Magnificent Hotel",
            "Booking",
            "https://cf.bstatic.com/xdata/images/hotel/max1024x768/519461821.jpg?k=0e907fea49d593678f35f965ccffc4b220e0f709848c14347ba8f7d9800b698d&o=&hp=1",
            "$100",
            23.45,
            12.34
        ),
        Stay(
            "Magnificent Hotel",
            "Booking",
            "https://cf.bstatic.com/xdata/images/hotel/max1024x768/519461821.jpg?k=0e907fea49d593678f35f965ccffc4b220e0f709848c14347ba8f7d9800b698d&o=&hp=1",
            "$100",
            23.45,
            12.34
        ),
        Stay(
            "Magnificent Hotel",
            "Booking",
            "https://cf.bstatic.com/xdata/images/hotel/max1024x768/519461821.jpg?k=0e907fea49d593678f35f965ccffc4b220e0f709848c14347ba8f7d9800b698d&o=&hp=1",
            "$100",
            23.45,
            12.34
        ),
    ),
    searchedFor: String = "Milano",
    stayRequestDto: StayRequestDto = StayRequestDto(),
    jwt: String = "jwt"
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .background(Constant.BACKGROUND_COLOR)
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(Constant.STD_NOTIFICATION_BAR_PADDING))

        TopBar(searchedFor)

        Spacer(modifier = Modifier.height(Constant.STD_PADDING))

        stayList.forEach { stay ->
            Divider(color = Constant.TEXT_GRAY)
            StayCard(stay, stayRequestDto, jwt, stayRequest = stayRequestDto)
        }
        Spacer(modifier = Modifier.height(Constant.PADDING_STAYS))
    }

    if (stayList.isEmpty()) {
        EmptyStayCardList(text = "Looks like your search criteria didn't return any results. Try again with different criteria.")
    }
}

@Preview
@Composable
private fun TopBar(searchedFor: String = "Milano") {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Constant.STD_PADDING)
    ) {
        Row(
            modifier = Modifier
                .background(Constant.BACKGROUND_COLOR)
                .fillMaxWidth()
                .padding(top = Constant.STD_PADDING)
                .align(Alignment.CenterStart),
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Constant.TEXT_GRAY
                )
            }
            FormField(length = Constant.SMALL_SEARCHBAR_LENGTH, field = searchedFor, setField = {})
        }
    }
}
