package com.davinci42.androidutils

import java.util.*
import java.util.Calendar.HOUR_OF_DAY
import java.util.Calendar.MINUTE

/**
 * Created by DaVinci42 on 2018/1/8.
 */
class TimeUtil {

    companion object {

        fun timeSinceNowInMillis(hourOfDay: Int, minute: Int): Long {
            val calendar = Calendar.getInstance()
            val presentHour = calendar.get(HOUR_OF_DAY)
            val presentMinute = calendar.get(MINUTE)


            return if (hourOfDay >= presentHour && minute >= presentMinute)
                ((hourOfDay - presentHour) * 60 + (minute - presentMinute)) * 60 * 1000L
            else
                ((hourOfDay + 24 - presentHour) * 60 + (minute - presentMinute)) * 60 * 1000L
        }
    }
}