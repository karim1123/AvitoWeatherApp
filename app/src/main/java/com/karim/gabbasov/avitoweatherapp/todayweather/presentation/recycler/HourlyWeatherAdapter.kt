package com.karim.gabbasov.avitoweatherapp.todayweather.presentation.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.karim.gabbasov.avitoweatherapp.R
import com.karim.gabbasov.avitoweatherapp.todayweather.data.util.DownloadImageUtil
import com.karim.gabbasov.avitoweatherapp.todayweather.data.util.InternetLinks
import com.karim.gabbasov.avitoweatherapp.todayweather.domain.model.HourlyWeatherRecyclerModel
import java.util.Locale

const val INVALID_VIEW = "Invalid view type"
const val PERCENT = "%"

/**
 * Adapter for use displaying the weather forecast by hours.
 */
class HourlyWeatherAdapter(
    private val forecastForDay: List<HourlyWeatherRecyclerModel>,
    private val downloadImageUtil: DownloadImageUtil
) :
    RecyclerView.Adapter<HourlyWeatherAdapter.HourlyWeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherViewHolder {
        val layout = when (viewType) {
            TYPE_TEMPERATURE_DETAILS -> R.layout.item_today_weather_details
            TYPE_WEATHER_FOR_HOUR_WITH_TITLE -> R.layout.item_weather_for_hour_with_title
            TYPE_WEATHER_FOR_HOUR -> R.layout.item_weather_for_hour
            else -> throw IllegalArgumentException(INVALID_VIEW)
        }
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layout, parent, false)
        return HourlyWeatherViewHolder(parent.context, view)
    }

    override fun onBindViewHolder(holder: HourlyWeatherViewHolder, position: Int) {
        holder.bind(forecastForDay[position])
    }

    override fun getItemCount(): Int = forecastForDay.size

    override fun getItemViewType(position: Int): Int {
        return when (forecastForDay[position]) {
            is HourlyWeatherRecyclerModel.HourlyWeatherRecyclerDetails -> TYPE_TEMPERATURE_DETAILS
            is HourlyWeatherRecyclerModel.WeatherForHourWithTitleRecycler -> TYPE_WEATHER_FOR_HOUR_WITH_TITLE
            else -> TYPE_WEATHER_FOR_HOUR
        }
    }

    companion object {
        private const val TYPE_TEMPERATURE_DETAILS = 0
        private const val TYPE_WEATHER_FOR_HOUR_WITH_TITLE = 1
        private const val TYPE_WEATHER_FOR_HOUR = 2
    }

    inner class HourlyWeatherViewHolder(
        val context: Context,
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private fun bindWeatherDetails(item: HourlyWeatherRecyclerModel.HourlyWeatherRecyclerDetails) {
            itemView.findViewById<AppCompatTextView>(R.id.tvWindSpeedAndDirection)?.text =
                context.getString(
                    R.string.wind_speed_and_direction,
                    item.windSpeed.toString(),
                    item.windDirection.uppercase(Locale.getDefault())
                )
            itemView.findViewById<AppCompatTextView>(R.id.tvPressureInMM)?.text =
                context.getString(
                    R.string.pressureInMM,
                    item.pressureInMM
                )
            itemView.findViewById<AppCompatTextView>(R.id.tvHumidityInPercents)?.text =
                context.getString(R.string.humidityInPercents, item.humidity, PERCENT)
        }

        private fun bindWeatherForHourWithTitle(item: HourlyWeatherRecyclerModel.WeatherForHourWithTitleRecycler) {
            itemView.findViewById<AppCompatTextView>(R.id.tvTime)?.text =
                context.getString(R.string.hour, item.hour)
            itemView.findViewById<AppCompatTextView>(R.id.tvTimeTitle)?.text = item.date
            downloadImageUtil.getImage(
                context.getString(
                    R.string.weather_icon_src,
                    InternetLinks.WEATHER_IMAGE_URL.link,
                    item.weatherIcon
                ),
                itemView.findViewById<AppCompatImageView>(R.id.ivWeather)
            )
            itemView.findViewById<AppCompatTextView>(R.id.tvTemperature)?.text =
                context.getString(R.string.temperature_in_Celsius, item.temperature)
        }

        private fun bindWeatherForHour(item: HourlyWeatherRecyclerModel.WeatherForHourRecycler) {
            itemView.findViewById<AppCompatTextView>(R.id.tvTime)?.text =
                context.getString(R.string.hour, item.hour)
            downloadImageUtil.getImage(
                context.getString(
                    R.string.weather_icon_src,
                    InternetLinks.WEATHER_IMAGE_URL.link,
                    item.weatherIcon
                ),
                itemView.findViewById<AppCompatImageView>(R.id.ivWeather)
            )
            itemView.findViewById<AppCompatTextView>(R.id.tvTemperature)?.text =
                context.getString(R.string.temperature_in_Celsius, item.temperature)
        }

        fun bind(dataModel: HourlyWeatherRecyclerModel) {
            when (dataModel) {
                is HourlyWeatherRecyclerModel.HourlyWeatherRecyclerDetails -> bindWeatherDetails(
                    dataModel
                )
                is HourlyWeatherRecyclerModel.WeatherForHourWithTitleRecycler -> bindWeatherForHourWithTitle(
                    dataModel
                )
                is HourlyWeatherRecyclerModel.WeatherForHourRecycler -> bindWeatherForHour(
                    dataModel
                )
            }
        }
    }
}
