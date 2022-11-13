package com.karim.gabbasov.avitoweatherapp.todayweather.data.util

/**
 * List of internet link of Yandex Api.
 */
enum class InternetLinks(val link: String) {
    BASE_WEATHER_URL("https://api.weather.yandex.ru/"),
    BASE_LOCATION_URL("https://suggestions.dadata.ru/"),
    WEATHER_IMAGE_URL("https://yastatic.net/weather/i/icons/funky/dark/")
}
