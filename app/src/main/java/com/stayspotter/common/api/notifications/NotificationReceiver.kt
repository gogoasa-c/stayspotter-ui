package com.stayspotter.common.api.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val availabilityChecker = context?. let { AvailabilityChecker(it) }

        availabilityChecker?.sendNotification("StayName")
    }

}