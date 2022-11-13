package com.karim.gabbasov.avitoweatherapp.todayweather.presentation.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.karim.gabbasov.avitoweatherapp.R
import com.karim.gabbasov.avitoweatherapp.todayweather.data.util.DownloadImageUtil
import com.karim.gabbasov.avitoweatherapp.todayweather.data.util.InternetLinks
import com.karim.gabbasov.avitoweatherapp.databinding.ItemWeatherForHourBinding
import com.karim.gabbasov.avitoweatherapp.todayweather.domain.model.WeatherForHourModel

/**
 * Adapter for use displaying the weather forecast by hours.
 */
class HourlyWeatherAdapter(
    private val forecastForDay: List<WeatherForHourModel>,
    private val downloadImageUtil: DownloadImageUtil
) :
    RecyclerView.Adapter<HourlyWeatherAdapter.HourlyWeatherViewHolder>() {

    inner class HourlyWeatherViewHolder(
        binding: ItemWeatherForHourBinding,
        val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private val time = binding.tvTime
        private val weatherIcon = binding.ivWeather
        private val temperature = binding.tvTemperature

        fun bind(weatherForHourModel: WeatherForHourModel) {
            time.text =
                context.getString(R.string.hour, weatherForHourModel.hour, weatherForHourModel.date)
            temperature.text =
                context.getString(R.string.temperature_in_Celsius, weatherForHourModel.temperature)
            downloadImageUtil.getImage(
                context.getString(
                    R.string.weather_icon_src,
                    InternetLinks.WEATHER_IMAGE_URL.link,
                    weatherForHourModel.weatherIcon
                ),
                weatherIcon
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWeatherForHourBinding.inflate(
            inflater,
            parent,
            false
        )
        return HourlyWeatherViewHolder(binding, parent.context)
    }

    override fun getItemCount(): Int {
        return forecastForDay.size
    }

    override fun onBindViewHolder(holder: HourlyWeatherViewHolder, position: Int) {
        holder.bind(forecastForDay[position])
    }
}
