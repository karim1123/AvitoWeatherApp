package com.karim.gabbasov.avitoweatherapp.data.util

/**
 * List of names of the days of the week.
 */
enum class NamesOfDayOfWeek(val dayOfWeekName: String) {
    Monday("Monday"),
    Tuesday("Tuesday"),
    Wednesday("Wednesday"),
    Thursday("Thursday"),
    Friday("Friday"),
    Saturday("Saturday"),
    Sunday("Sunday"),
    Today("Today");

    /**
     * Gets the [NamesOfDayOfWeek.dayOfWeekName] from a given Calendar index.
     */
    companion object {
        fun Int.toDayOfWeekName(): String {
            return when (this) {
                2 -> Monday.dayOfWeekName
                3 -> Tuesday.dayOfWeekName
                4 -> Wednesday.dayOfWeekName
                5 -> Thursday.dayOfWeekName
                6 -> Friday.dayOfWeekName
                7 -> Saturday.dayOfWeekName
                1 -> Sunday.dayOfWeekName
                else -> {
                    WRONG_NUMBER
                }
            }
        }
    }
}
