package com.stayspotter.common.api.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.CallSuper
import com.stayspotter.Constant

private const val ACTION_START_ALARM_LOOPER = "alarm.loopers.START"

fun Context.setAlarmLooper(loopReceiverClass: Class<out BroadcastReceiver>, jsonWebToken: String) {
    sendBroadcast(Intent(this, loopReceiverClass).apply {
        action = ACTION_START_ALARM_LOOPER
        putExtra(Constant.INTENT_KEY_JWT, jsonWebToken)
    })

}

abstract class NotificationReceiver : BroadcastReceiver() {
    abstract val loopPeriod: Long
    abstract val loopAction: String
    abstract val alarmManager: AlarmManager

    @CallSuper
    override fun onReceive(context: Context, intent: Intent) {
        val isStartAction = intent.action == ACTION_START_ALARM_LOOPER
                || intent.action == Intent.ACTION_BOOT_COMPLETED

        val isLoopStarted = getLoopIntent(context, true) != null

        if (isStartAction && !isLoopStarted) {
            loop(getLoopIntent(context, false)!!, {})
        }
    }

    protected fun stop(context: Context) { // <9>
        getLoopIntent(context, false)?.let { alarmManager.cancel(it) }
    }


    protected fun loop(intent: PendingIntent, loopAction: () -> Unit) {
        Log.i("NotificationReceiver", "Looping")
        loopAction()
        if (alarmManager.canScheduleExactAlarms()) {
            vintageLoopInternal(intent)
        } else {
            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + loopPeriod,
                intent
            )
        }
    }

    private fun vintageLoopInternal(intent: PendingIntent) { // <7>
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + loopPeriod,
            intent
        )
    }

    protected fun getLoopIntent(context: Context, noCreate: Boolean): PendingIntent? =
        PendingIntent.getBroadcast(
            context,
            0,
            Intent(context, this::class.java).apply { action = loopAction },
            if (noCreate) PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_NO_CREATE else PendingIntent.FLAG_IMMUTABLE
        )
}
