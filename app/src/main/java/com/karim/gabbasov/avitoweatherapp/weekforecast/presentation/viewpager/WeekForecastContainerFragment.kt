package com.karim.gabbasov.avitoweatherapp.weekforecast.presentation.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.karim.gabbasov.avitoweatherapp.databinding.FragmentWeekForecastContainerBinding
import com.karim.gabbasov.avitoweatherapp.domain.weather.WeatherInfoModel
import com.karim.gabbasov.avitoweatherapp.presentation.TodayWeatherViewModel
import com.karim.gabbasov.avitoweatherapp.presentation.WeatherState
import kotlinx.coroutines.launch

/**
 * Fragment used as a container for ViewPager.
 */
class WeekForecastContainerFragment : Fragment() {
    private var _binding: FragmentWeekForecastContainerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TodayWeatherViewModel by activityViewModels()
    private val args: WeekForecastContainerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeekForecastContainerBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.todayWeatherStatus.collect { state ->
                    weatherStateChanged(state)
                }
            }
        }
        val weekForecastViewPagerAdapter = WeekForecastViewPagerAdapter(this)
        binding.WeekForecastViewPager.adapter = weekForecastViewPagerAdapter
        binding.WeekForecastViewPager.setCurrentItem(args.dayIndex, false)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun weatherStateChanged(state: WeatherState) {
        when (state) {
            is WeatherState.Success -> {
                if (state.weatherInfoModel != null) {
                    val tabsTitles = createTabsTitles(state.weatherInfoModel)
                    TabLayoutMediator(
                        binding.tabLayout,
                        binding.WeekForecastViewPager
                    ) { tab, position ->
                        tab.text = tabsTitles[position]
                    }.attach()
                }
            }
            else -> {}
        }
    }

    /**
     * Create Tabs titles using the data stored in [weatherInfoModel]
     */
    private fun createTabsTitles(weatherInfoModel: WeatherInfoModel): List<String> {
        val tabsTitles = mutableListOf<String>()
        weatherInfoModel.weatherForWeekModel.morningWeather.forEach {
            tabsTitles.add("${it.dayOfWeek.substring(0, 3)} ${it.date}")
        }
        return tabsTitles
    }
}
