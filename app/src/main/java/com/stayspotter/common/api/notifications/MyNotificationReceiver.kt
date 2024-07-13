package com.stayspotter.common.api.notifications

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.stayspotter.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyNotificationReceiver : NotificationReceiver() {
    private lateinit var context: Context

//    override val loopPeriod: Long = 1000 * 60 * 60 * 24
    override val loopPeriod: Long = 20 * 1000
    override val loopAction: String = "com.stayspotter.common.api.notifications.MyNotificationReceiver"
    override val alarmManager: AlarmManager
        get() = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private lateinit var availabilityChecker: AvailabilityChecker
//    private lateinit var jwt: MutableState<String>

    override fun onReceive(context: Context, intent: Intent) {
        this.context = context
        super.onReceive(context, intent)
        availabilityChecker = AvailabilityChecker(context)

        if (JWT.value == "") {
            JWT.value = intent.getStringExtra(Constant.INTENT_KEY_JWT)
                ?: throw IllegalArgumentException("No JWT provided")
        }

        if (intent.action == loopAction) {
            CoroutineScope(Dispatchers.IO).launch {
                loop(getLoopIntent(context, false)!!) {
                    availabilityChecker.getStayList(JWT.value)
//                    availabilityChecker.checkChanges(JWT.value)
                }
            }
        }
    }

    companion object {
        var JWT = mutableStateOf("")
    }

}