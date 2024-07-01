package com.stayspotter.common

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.gson.Gson
import com.stayspotter.Constant
import com.stayspotter.common.api.notifications.AvailabilityChecker
import com.stayspotter.model.FavouriteStay
import com.stayspotter.search.EmbeddedSearch
import com.stayspotter.stays.FavouriteStaysEmbedded
import com.stayspotter.stays.loadStays
import com.stayspotter.user.EmbeddedProfile
import java.nio.file.AccessDeniedException

class DefaultActivity : AppCompatActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            CommonActivity(intent)
//            ScheduleJob()
        }
    }
    @Composable
    private fun ScheduleJob() {
        var stayList = listOf<FavouriteStay>()
        val setStayList = { list: List<FavouriteStay> -> stayList = list }

        // todo for some reason even tho call is successful, it's not passing the if check
        loadStays(setStayList, intent.getStringExtra(Constant.INTENT_KEY_JWT)
            ?: throw AccessDeniedException("No JWT provided"), LocalContext.current) {}

//        val jobScheduler = this.getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
//        val jobName = ComponentName(this, AvailabilityChecker::class.java)
//        val jobInfo = JobInfo.Builder(0, jobName)
//            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
//            .setPeriodic(24 * 60 * 60 * 1000)
//            .setPeriodic(1000 * 60 * 15)
//            .setMinimumLatency(1000)
//            .setOverrideDeadline(1000)
//            .setPersisted(true)
//            .setExtras(PersistableBundle().apply {
//                putString(Constant.INTENT_KEY_JWT, intent.getStringExtra(Constant.INTENT_KEY_JWT))
//                putString(Constant.INTENT_KEY_STAY_LIST, Gson().toJson(stayList))
//            })
//            .build()
        Log.i("DefaultActivity", "Job scheduled")
//        val result = jobScheduler.schedule(jobInfo)

//        if (result == JobScheduler.RESULT_SUCCESS) {
//            println("Job scheduled successfully")
//        } else {
//            println("Job scheduling failed")
//        }
    }
}

@Composable
@Preview
private fun CommonActivity(intent: Intent = Intent()) {
    val (selected, setSelected) = remember { mutableIntStateOf(Constant.NAVBAR_ITEM_SEARCH) }
    val jsonWebToken = remember { mutableStateOf("")}
    val (stays, setStays) = remember { mutableStateOf(listOf<FavouriteStay>()) }
    jsonWebToken.value = intent.getStringExtra(Constant.INTENT_KEY_JWT)
        ?: throw AccessDeniedException("No JWT provided")

    when (selected) {
        Constant.NAVBAR_ITEM_FAV -> {
            FavouriteStaysEmbedded(jwt = jsonWebToken.value, setStays = setStays, stays = stays)
        }

        Constant.NAVBAR_ITEM_SEARCH -> {
            EmbeddedSearch(jsonWebToken.value)
        }

        Constant.NAVBAR_ITEM_PROFILE -> {
            EmbeddedProfile(jsonWebToken.value)
        }
    }

    Navbar(selected, setSelected)
}