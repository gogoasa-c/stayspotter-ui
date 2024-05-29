package com.stayspotter.stays

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.stayspotter.Constant
import com.stayspotter.common.FavouritedStayCard
import com.stayspotter.common.SimpleText
import com.stayspotter.common.StayCard
import com.stayspotter.common.api.ApiClient
import com.stayspotter.model.FavouriteStay
import com.stayspotter.model.Stay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavouriteStaysActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        var stays = listOf<FavouriteStay>()

        setContent {
            FavouriteStaysEmbedded(
                setStays = {
                    stays = it
                }
            )
        }
    }
}

@Preview
@Composable
fun FavouriteStaysEmbedded(
    isFirstOpening: MutableState<Boolean> = mutableStateOf(true), jwt: String = "jwt",
    setStays: (List<FavouriteStay>) -> Unit = {}, stays: List<FavouriteStay> = listOf(
        FavouriteStay(
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
        ),
        FavouriteStay(
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
        ),
        FavouriteStay(
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
        ),
    )
) {
    val scrollState = rememberScrollState()

    val context = LocalContext.current

//    if (isFirstOpening.value) {
//        loadStays(setStays, jwt, context)
//        isFirstOpening.value = false
//    }
    loadStays(setStays, jwt, context)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Constant.BACKGROUND_COLOR)
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(Constant.STD_NOTIFICATION_BAR_PADDING))
        TitleBar()
        Spacer(modifier = Modifier.height(Constant.STD_PADDING))

        stays.forEach {
            Divider(color = Constant.TEXT_GRAY)
            FavouritedStayCard(it)
        }
        Spacer(modifier = Modifier.height(Constant.PADDING_STAYS))
    }
}

fun loadStays(setStayList: (List<FavouriteStay>) -> Unit, jwt: String, context: Context) {
    val call = ApiClient.apiService.getFavourites("Bearer $jwt")

    call.enqueue(object : Callback<List<FavouriteStay>> {
        override fun onResponse(
            call: Call<List<FavouriteStay>>,
            response: Response<List<FavouriteStay>>
        ) {
            if (response.isSuccessful) {
                setStayList(response.body() ?: emptyList())

                return
            }

            Toast.makeText(
                context, "Error while fetching favourite stays...",
                Toast.LENGTH_SHORT
            ).show()
        }

        override fun onFailure(call: Call<List<FavouriteStay>>, t: Throwable) {
            Toast.makeText(
                context, "Error while fetching favourite stays...",
                Toast.LENGTH_SHORT
            ).show()

            Log.e("FavouriteStaysActivity", "Error while fetching favourite stays...", t)
        }
    })
}

@Preview
@Composable
private fun TitleBar() {
    Row(
        Modifier
            .fillMaxWidth()
            .background(Constant.BACKGROUND_COLOR)
    ) {
        SimpleText(
            text = "Favourite Stays",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            fontSize = Constant.STD_TITLE_FONT_SIZE
        )
    }
}
