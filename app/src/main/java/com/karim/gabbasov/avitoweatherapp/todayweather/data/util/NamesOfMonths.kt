package com.karim.gabbasov.avitoweatherapp.todayweather.data.util

/**
 * List of names of the days of the week.
 */
enum class NamesOfMonths(val monthName: String) {
    January("January"),
    February("February"),
    March("March"),
    April("April"),
    May("May"),
    June("June"),
    July("July"),
    August("August"),
    September("September"),
    October("October"),
    November("November"),
    December("December");

    /**
     * Gets the [NamesOfMonths.monthName] from a given Calendar index.
     */
    companion object {
        fun Int.toMonthName(): String {
            return when (this) {
                0 -> January.monthName
                1 -> February.monthName
                2 -> March.monthName
                3 -> April.monthName
                4 -> May.monthName
                5 -> June.monthName
                6 -> July.monthName
                7 -> August.monthName
                8 -> September.monthName
                9 -> October.monthName
                10 -> November.monthName
                11 -> December.monthName
                else -> {
                    WRONG_NUMBER
                }
            }
        }
    }
}
