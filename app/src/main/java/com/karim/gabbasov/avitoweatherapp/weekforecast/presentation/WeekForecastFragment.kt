package com.karim.gabbasov.avitoweatherapp.weekforecast.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.karim.gabbasov.avitoweatherapp.todayweather.data.util.DownloadImageUtil
import com.karim.gabbasov.avitoweatherapp.databinding.FragmentWeekForecastBinding
import com.karim.gabbasov.avitoweatherapp.todayweather.domain.model.WeatherInfoModel
import com.karim.gabbasov.avitoweatherapp.todayweather.presentation.TodayWeatherViewModel
import com.karim.gabbasov.avitoweatherapp.todayweather.presentation.WeatherState
import com.karim.gabbasov.avitoweatherapp.weekforecast.presentation.recycler.WeekForecastAdapter
import com.karim.gabbasov.avitoweatherapp.weekforecast.util.WeekForecastMapper.toWeatherForecastByDayParts
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Fragment to allow the user to view the detailed weather forecast fot seven days.
 */
@AndroidEntryPoint
class WeekForecastFragment : Fragment() {
    private var _binding: FragmentWeekForecastBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TodayWeatherViewModel by activityViewModels()
    private lateinit var weekForecastAdapter: WeekForecastAdapter

    @Inject
    lateinit var downloadImageUtil: DownloadImageUtil

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeekForecastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weekForecastAdapter = WeekForecastAdapter(downloadImageUtil)
        val weekForecastRecycler = binding.weekForecastRecycler
        weekForecastRecycler.adapter = weekForecastAdapter
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.todayWeatherStatus.collect { state ->
                    weatherStateChanged(state)
                }
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun weatherStateChanged(state: WeatherState) {
        when (state) {
            is WeatherState.Success -> {
                if (state.weatherInfoModel != null) {
                    val tabPosition = requireArguments().getInt(ARG_POSITION)
                    displayWeather(tabPosition, state.weatherInfoModel)
                }
            }
            else -> {}
        }
    }

    /**
     * Drawing the received data on the screen using the data stored in [weatherInfoModel]
     */
    private fun displayWeather(tabPosition: Int, weatherInfoModel: WeatherInfoModel) {
        weekForecastAdapter.setData(
            weatherInfoModel.weatherForWeekModel.toWeatherForecastByDayParts(
                tabPosition
            )
        )
    }

    companion object {
        const val ARG_POSITION = "position"
        fun getInstance(position: Int): Fragment {
            val weekForecastFragment = WeekForecastFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_POSITION, position)
            weekForecastFragment.arguments = bundle
            return weekForecastFragment
        }
    }
}
