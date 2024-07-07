package com.stayspotter.common.api.notifications

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.stayspotter.R
import com.stayspotter.common.api.ApiClient
import com.stayspotter.model.Availability
import com.stayspotter.model.AvailabilityCheckRequestDto
import com.stayspotter.model.FavouriteStay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class AvailabilityChecker(private val context: Context) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
            as NotificationManager

    private lateinit var stayList: List<FavouriteStay>

    fun getStayList(jwt: String) {
        val call = ApiClient.apiService.getFavourites("Bearer $jwt")

        call.enqueue(object : Callback<List<FavouriteStay>> {
            override fun onResponse(
                call: Call<List<FavouriteStay>>,
                response: Response<List<FavouriteStay>>
            ) {
                if (response.isSuccessful) {
                    stayList = response.body() ?: emptyList()
                    checkChanges(jwt)
                }
            }

            override fun onFailure(call: Call<List<FavouriteStay>>, t: Throwable) {
                Log.e("AvailabilityChecker", "Failed to get stay list")
            }
        })
    }

    fun checkChanges(jwt: String) {
        Log.i("AvailabilityChecker", "Checking availability")
        Log.i("AvailabilityChecker", "Stay list: $stayList")
        for (stay in stayList) {
            val call = ApiClient.apiService
                .checkAvailability(AvailabilityCheckRequestDto(stay.link), "Bearer $jwt")

            call.enqueue(object : Callback<Availability> {
                override fun onResponse(
                    call: Call<Availability>,
                    response: Response<Availability>
                ) {
                    if (response.isSuccessful) {
                        Log.i("AvailabilityChecker", "Stay availability checked " +
                                "${response.isSuccessful}")
                        val availability = response.body()
                        if (availability?.available == false) {
                            sendNotification(stay.name)
                        }
                    }
                }

                override fun onFailure(call: Call<Availability>, t: Throwable) {
                    Log.e("AvailabilityChecker", "Failed to check availability")
                }
            })
        }
    }

    fun sendNotification(stayName: String) {
        val notification = NotificationCompat.Builder(context, "availability")
            .setContentTitle("Stay no longer available")
            .setContentText("Stay $stayName is no longer available")
            .setSmallIcon(R.mipmap.app_icon)
            .build()

        notificationManager.notify(Random.nextInt(), notification)
    }
}

//class AvailabilityChecker: JobService() {
//    private val job = Job()
//    private val scope = CoroutineScope(Dispatchers.Default + job)
//    private val notificationManager by lazy {
//        getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//    }
//    private val notificationChannel = NotificationChannel(
//        "availability",
//        "Availability",
//        NotificationManager.IMPORTANCE_HIGH
//    )
//
//    override fun onStartJob(params: JobParameters?): Boolean {
//        Log.i("AvailabilityChecker", "Job started")
//
//        scope.launch {
//            if (params == null) {
//                Log.d("AvailabilityChecker", "Job cancelled")
//                return@launch
//            }
//
//            notificationManager.createNotificationChannel(notificationChannel)
//
//            val jsonStayList = params.extras.getString(Constant.INTENT_KEY_STAY_LIST)
//            val jwt = params.extras.getString(Constant.INTENT_KEY_JWT)
//            val type = object : TypeToken<List<FavouriteStay>>() {}.type
//            Log.e("AvailabilityChecker", "Stay list dies someone if not: $jsonStayList")
//            val stayList: List<FavouriteStay>? = jsonStayList?.let { Gson()
//                .fromJson(jsonStayList, type) }
//
//            for (stay in stayList ?: listOf()) {
//                val call = ApiClient.apiService.checkAvailability(stay.link,
//                    jwt ?: "")
//
//                call.enqueue(object : Callback<Availability> {
//                    override fun onResponse(
//                        call: Call<Availability>,
//                        response: Response<Availability>
//                    ) {
//                        if (response.isSuccessful) {
//                            val availability = response.body()
//                            if (availability?.available == false) {
//                                // send notif
//                                // todo check if this actually works
//                                sendNotification(stay.name)
//                                // remove from favs
//                            }
//                        }
//                    }
//
//                    override fun onFailure(call: Call<Availability>, t: Throwable) {
//                        Log.e("AvailabilityChecker", "Failed to check availability")
//                    }
//                })
//            }
//        }
////        jobFinished(params, false)
//        return true;
//    }
//
//
//
//    override fun onStopJob(params: JobParameters?): Boolean {
//        Log.i("AvailabilityChecker", "Job stopped")
//        return true
//    }
//
//    private fun sendNotification(stayName: String) {
//        val notification = NotificationCompat.Builder(this, "availability")
//            .setContentTitle("Stay no longer available")
//            .setContentText("Stay $stayName is no longer available")
//            .setSmallIcon(R.mipmap.app_icon)
//            .build()
//
//        notificationManager.notify(Random.nextInt(), notification)
//    }
//}