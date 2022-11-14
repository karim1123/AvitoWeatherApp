package com.karim.gabbasov.avitoweatherapp.todayweather.presentation.recycler

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.karim.gabbasov.avitoweatherapp.R
import com.karim.gabbasov.avitoweatherapp.todayweather.data.util.DownloadImageUtil
import com.karim.gabbasov.avitoweatherapp.todayweather.data.util.InternetLinks
import com.karim.gabbasov.avitoweatherapp.todayweather.domain.model.WeekWeatherRecyclerModel

/**
 * Adapter for use displaying the weather forecast by days.
 */
class WeatherForWeekAdapter(
    private val forecastForWeek: List<WeekWeatherRecyclerModel>,
    private val downloadImageUtil: DownloadImageUtil,
    private val onClick: (Int) -> Unit
) :
    RecyclerView.Adapter<WeatherForWeekAdapter.WeatherForWeekViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherForWeekViewHolder {
        val layout = when (viewType) {
            TYPE_TITLE_ITEM -> R.layout.item_weather_for_day_with_title
            TYPE_WEEKEND_ITEM -> R.layout.item_weather_for_day
            TYPE_WEEKDAY_ITEM -> R.layout.item_weather_for_day
            else -> throw IllegalArgumentException(INVALID_VIEW)
        }
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layout, parent, false)
        return WeatherForWeekViewHolder(parent.context, view)
    }

    override fun onBindViewHolder(holder: WeatherForWeekViewHolder, position: Int) {
        holder.bind(forecastForWeek[position])
    }

    override fun getItemCount(): Int = forecastForWeek.size

    override fun getItemViewType(position: Int): Int {
        return when (forecastForWeek[position]) {
            is WeekWeatherRecyclerModel.TitleItem -> TYPE_TITLE_ITEM
            is WeekWeatherRecyclerModel.WeekendItem -> TYPE_WEEKEND_ITEM
            else -> TYPE_WEEKDAY_ITEM
        }
    }

    companion object {
        private const val TYPE_TITLE_ITEM = 0
        private const val TYPE_WEEKEND_ITEM = 1
        private const val TYPE_WEEKDAY_ITEM = 2
    }

    inner class WeatherForWeekViewHolder(
        val context: Context,
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private var currentIndex: Int? = null

        init {
            itemView.setOnClickListener {
                currentIndex?.let {
                    onClick(it)
                }
            }
        }

        private fun bindTitleItem(item: WeekWeatherRecyclerModel.TitleItem) {
            currentIndex = item.index
            itemView.findViewById<AppCompatTextView>(R.id.tvDate)?.text = item.date
            itemView.findViewById<AppCompatTextView>(R.id.tvDayOfWeek)
                ?.setTextColor(Color.parseColor(item.textColor))
            itemView.findViewById<AppCompatTextView>(R.id.tvDayOfWeek)?.text = item.dayOfWeek
            downloadImageUtil.getImage(
                context.getString(
                    R.string.weather_icon_src,
                    InternetLinks.WEATHER_IMAGE_URL.link,
                    item.weatherIcon
                ),
                itemView.findViewById<AppCompatImageView>(R.id.ivWeather)
            )
            itemView.findViewById<AppCompatTextView>(R.id.tvMaxTemp)?.text =
                context.getString(
                    R.string.temperature_in_Celsius,
                    item.maxTemperature
                )
            itemView.findViewById<AppCompatTextView>(R.id.tvMinTemp)?.text =
                context.getString(
                    R.string.temperature_in_Celsius,
                    item.maxTemperature
                )
        }

        private fun bindWeekendItem(item: WeekWeatherRecyclerModel.WeekendItem) {
            currentIndex = item.index
            itemView.findViewById<AppCompatTextView>(R.id.tvDate)?.text = item.date
            itemView.findViewById<AppCompatTextView>(R.id.tvDayOfWeek)
                ?.setTextColor(Color.parseColor(item.textColor))
            itemView.findViewById<AppCompatTextView>(R.id.tvDayOfWeek)?.text = item.dayOfWeek
            downloadImageUtil.getImage(
                context.getString(
                    R.string.weather_icon_src,
                    InternetLinks.WEATHER_IMAGE_URL.link,
                    item.weatherIcon
                ),
                itemView.findViewById<AppCompatImageView>(R.id.ivWeather)
            )
            itemView.findViewById<AppCompatTextView>(R.id.tvMaxTemp)?.text =
                context.getString(
                    R.string.temperature_in_Celsius,
                    item.maxTemperature
                )
            itemView.findViewById<AppCompatTextView>(R.id.tvMinTemp)?.text =
                context.getString(
                    R.string.temperature_in_Celsius,
                    item.maxTemperature
                )
        }

        private fun bindWeekdayItem(item: WeekWeatherRecyclerModel.WeekdayItem) {
            currentIndex = item.index
            itemView.findViewById<AppCompatTextView>(R.id.tvDate)?.text = item.date
            itemView.findViewById<AppCompatTextView>(R.id.tvDayOfWeek)?.text = item.dayOfWeek
            downloadImageUtil.getImage(
                context.getString(
                    R.string.weather_icon_src,
                    InternetLinks.WEATHER_IMAGE_URL.link,
                    item.weatherIcon
                ),
                itemView.findViewById<AppCompatImageView>(R.id.ivWeather)
            )
            itemView.findViewById<AppCompatTextView>(R.id.tvMaxTemp)?.text =
                context.getString(
                    R.string.temperature_in_Celsius,
                    item.maxTemperature
                )
            itemView.findViewById<AppCompatTextView>(R.id.tvMinTemp)?.text =
                context.getString(
                    R.string.temperature_in_Celsius,
                    item.maxTemperature
                )
        }

        fun bind(dataModel: WeekWeatherRecyclerModel) {
            when (dataModel) {
                is WeekWeatherRecyclerModel.TitleItem -> bindTitleItem(
                    dataModel
                )
                is WeekWeatherRecyclerModel.WeekendItem -> bindWeekendItem(
                    dataModel
                )
                is WeekWeatherRecyclerModel.WeekdayItem -> bindWeekdayItem(
                    dataModel
                )
            }
        }
    }
}
