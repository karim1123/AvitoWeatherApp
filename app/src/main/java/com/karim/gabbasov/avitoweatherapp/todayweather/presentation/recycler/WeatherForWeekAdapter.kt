package com.karim.gabbasov.avitoweatherapp.todayweather.presentation.recycler

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.karim.gabbasov.avitoweatherapp.R
import com.karim.gabbasov.avitoweatherapp.todayweather.data.util.DownloadImageUtil
import com.karim.gabbasov.avitoweatherapp.todayweather.data.util.InternetLinks
import com.karim.gabbasov.avitoweatherapp.todayweather.data.util.NamesOfDayOfWeek
import com.karim.gabbasov.avitoweatherapp.databinding.ItemWeatherForDayBinding
import com.karim.gabbasov.avitoweatherapp.todayweather.domain.model.WeatherForDayPartModel

const val RED_COLOR = "#CD4040"

/**
 * Adapter for use displaying the weather forecast by days.
 */
class WeatherForWeekAdapter(
    private val forecastForWeek: List<WeatherForDayPartModel>,
    private val downloadImageUtil: DownloadImageUtil,
    private val onClick: (WeatherForDayPartModel) -> Unit
) :
    RecyclerView.Adapter<WeatherForWeekAdapter.WeatherForWeekViewHolder>() {

    inner class WeatherForWeekViewHolder(
        binding: ItemWeatherForDayBinding,
        val context: Context,
        val onClick: (WeatherForDayPartModel) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private val date = binding.tvDate
        private val dayOfWeek = binding.tvDayOfWeek
        private val weatherIcon = binding.ivWeather
        private val maxTemperature = binding.tvMaxTemp
        private val minTemperature = binding.tvMinTemp
        private var currentWeatherForDayPartModel: WeatherForDayPartModel? = null

        init {
            itemView.setOnClickListener {
                currentWeatherForDayPartModel?.let {
                    onClick(it)
                }
            }
        }

        fun bind(dayForecast: WeatherForDayPartModel) {
            currentWeatherForDayPartModel = dayForecast
            date.text = dayForecast.date
            if (
                dayForecast.dayOfWeek == NamesOfDayOfWeek.Sunday.name ||
                dayForecast.dayOfWeek == NamesOfDayOfWeek.Saturday.name
            ) {
                dayOfWeek.setTextColor(Color.parseColor(RED_COLOR))
            }
            dayOfWeek.text = dayForecast.dayOfWeek

            if (dayForecast.dayOfWeek == NamesOfDayOfWeek.Today.name) {
                maxTemperature.text =
                    context.getString(
                        R.string.temperature_in_Celsius_with_Max_title,
                        dayForecast.maxTemperature
                    )
                minTemperature.text =
                    context.getString(
                        R.string.temperature_in_Celsius_with_MIn_title,
                        dayForecast.minTemperature
                    )
            } else {
                maxTemperature.text =
                    context.getString(R.string.temperature_in_Celsius, dayForecast.maxTemperature)
                minTemperature.text =
                    context.getString(R.string.temperature_in_Celsius, dayForecast.minTemperature)
            }
            downloadImageUtil.getImage(
                context.getString(
                    R.string.weather_icon_src,
                    InternetLinks.WEATHER_IMAGE_URL.link,
                    dayForecast.weatherIcon
                ),
                weatherIcon
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherForWeekViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWeatherForDayBinding.inflate(
            inflater,
            parent,
            false
        )
        return WeatherForWeekViewHolder(binding, parent.context, onClick)
    }

    override fun getItemCount(): Int {
        return forecastForWeek.size
    }

    override fun onBindViewHolder(holder: WeatherForWeekViewHolder, position: Int) {
        forecastForWeek[position].index = position
        holder.bind(forecastForWeek[position])
    }
}
