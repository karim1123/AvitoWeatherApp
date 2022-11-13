package com.karim.gabbasov.avitoweatherapp.todayweather.data.util

import com.karim.gabbasov.avitoweatherapp.todayweather.data.util.NamesOfMonths.Companion.toMonthName
import java.util.Calendar

const val WRONG_NUMBER = "wrong number"
const val HOURS_IN_A_DAY = 24
const val MILLISECONDS_TO_A_SECOND = 1000

/**
 * Util to aid with convert unixTime to Calendar.
 */
object CalendarUtil {
    fun Long.unixTimeToHours(): Int {
        val calendar = this.unixTimeToCalendar()
        return calendar.get(Calendar.HOUR_OF_DAY)
    }

    fun Long.unixTimeToTomorrowDate(): String {
        val calendar = this.unixTimeToCalendar()
        calendar.add(Calendar.DATE, 1)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH).toMonthName()
        return "$day ${month.substring(0, 3)}"
    }

    fun Long.unixTimeToCalendar(): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this * MILLISECONDS_TO_A_SECOND
        return calendar
    }
}
