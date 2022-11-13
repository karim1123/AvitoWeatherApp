package com.karim.gabbasov.avitoweatherapp.weekforecast.presentation.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.karim.gabbasov.avitoweatherapp.R
import com.karim.gabbasov.avitoweatherapp.todayweather.data.util.DownloadImageUtil
import com.karim.gabbasov.avitoweatherapp.todayweather.data.util.InternetLinks
import com.karim.gabbasov.avitoweatherapp.weekforecast.domain.WeatherForecastForDayByDayPartsModel

const val INVALID_VIEW = "Invalid view type"

/**
 * Adapter for use displaying the weather forecast for the day.
 */
class WeekForecastAdapter(
    private val downloadImageUtil: DownloadImageUtil
) : RecyclerView.Adapter<WeekForecastAdapter.DataAdapterViewHolder>() {
    private val adapterData = mutableListOf<WeatherForecastForDayByDayPartsModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapterViewHolder {
        val layout = when (viewType) {
            TYPE_TEMPERATURE -> R.layout.item_temperature_for_day
            TYPE_OTHER_PARAMETERS -> R.layout.item_weather_parameter_for_day
            else -> throw IllegalArgumentException(INVALID_VIEW)
        }
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layout, parent, false)

        return DataAdapterViewHolder(parent.context, view)
    }

    override fun onBindViewHolder(holder: DataAdapterViewHolder, position: Int) {
        holder.bind(adapterData[position])
    }

    override fun getItemCount(): Int = adapterData.size

    override fun getItemViewType(position: Int): Int {
        return when (adapterData[position]) {
            is WeatherForecastForDayByDayPartsModel.TemperatureParameter -> TYPE_TEMPERATURE
            else -> TYPE_OTHER_PARAMETERS
        }
    }

    fun setData(data: List<WeatherForecastForDayByDayPartsModel>) {
        adapterData.apply {
            clear()
            addAll(data)
        }
    }

    companion object {
        private const val TYPE_TEMPERATURE = 0
        private const val TYPE_OTHER_PARAMETERS = 1
    }

    inner class DataAdapterViewHolder(
        val context: Context,
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private fun bindTemperature(item: WeatherForecastForDayByDayPartsModel.TemperatureParameter) {
            itemView.findViewById<AppCompatTextView>(R.id.tvMorningValue)?.text =
                context.getString(R.string.temperature_in_Celsius, item.temperatures[0])
            itemView.findViewById<AppCompatTextView>(R.id.tvDayValue)?.text =
                context.getString(R.string.temperature_in_Celsius, item.temperatures[1])
            itemView.findViewById<AppCompatTextView>(R.id.tvEveningValue)?.text =
                context.getString(R.string.temperature_in_Celsius, item.temperatures[2])
            itemView.findViewById<AppCompatTextView>(R.id.tvNightValue)?.text =
                context.getString(R.string.temperature_in_Celsius, item.temperatures[3])

            itemView.findViewById<AppCompatTextView>(R.id.tvMorningFellsTemp)?.text =
                context.getString(R.string.temperature_in_Celsius, item.feelsLikeTemperatures[0])
            itemView.findViewById<AppCompatTextView>(R.id.tvDayFeelsTemp)?.text =
                context.getString(R.string.temperature_in_Celsius, item.feelsLikeTemperatures[1])
            itemView.findViewById<AppCompatTextView>(R.id.tvEveningFeelsTemp)?.text =
                context.getString(R.string.temperature_in_Celsius, item.feelsLikeTemperatures[2])
            itemView.findViewById<AppCompatTextView>(R.id.tvNightFellsTemp)?.text =
                context.getString(R.string.temperature_in_Celsius, item.feelsLikeTemperatures[3])

            downloadImageUtil.getImage(
                context.getString(
                    R.string.weather_icon_src,
                    InternetLinks.WEATHER_IMAGE_URL.link,
                    item.weatherIcons[0]
                ),
                itemView.findViewById<AppCompatImageView>(R.id.ivMorningWeather)
            )
            downloadImageUtil.getImage(
                context.getString(
                    R.string.weather_icon_src,
                    InternetLinks.WEATHER_IMAGE_URL.link,
                    item.weatherIcons[1]
                ),
                itemView.findViewById<AppCompatImageView>(R.id.ivDayWeather)
            )
            downloadImageUtil.getImage(
                context.getString(
                    R.string.weather_icon_src,
                    InternetLinks.WEATHER_IMAGE_URL.link,
                    item.weatherIcons[2]
                ),
                itemView.findViewById<AppCompatImageView>(R.id.ivEveningWeather)
            )
            downloadImageUtil.getImage(
                context.getString(
                    R.string.weather_icon_src,
                    InternetLinks.WEATHER_IMAGE_URL.link,
                    item.weatherIcons[3]
                ),
                itemView.findViewById<AppCompatImageView>(R.id.ivNightWeather)
            )
        }

        private fun bindOtherParameters(item: WeatherForecastForDayByDayPartsModel.OtherWeatherParameters) {
            itemView.findViewById<AppCompatTextView>(R.id.tvParameterTitle)?.text =
                item.parameterName
            itemView.findViewById<AppCompatImageView>(R.id.ivParameterIcon)
                .setImageDrawable(ContextCompat.getDrawable(context, item.parameterIcon))

            itemView.findViewById<AppCompatTextView>(R.id.tvMorningValue)?.text =
                item.parameters[0]
            itemView.findViewById<AppCompatTextView>(R.id.tvDayValue)?.text =
                item.parameters[1]
            itemView.findViewById<AppCompatTextView>(R.id.tvEveningValue)?.text =
                item.parameters[2]
            itemView.findViewById<AppCompatTextView>(R.id.tvNightValue)?.text =
                item.parameters[3]
        }

        fun bind(dataModel: WeatherForecastForDayByDayPartsModel) {
            when (dataModel) {
                is WeatherForecastForDayByDayPartsModel.TemperatureParameter -> bindTemperature(
                    dataModel
                )
                is WeatherForecastForDayByDayPartsModel.OtherWeatherParameters -> bindOtherParameters(
                    dataModel
                )
            }
        }
    }
}
