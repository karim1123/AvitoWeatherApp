package com.karim.gabbasov.avitoweatherapp.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.karim.gabbasov.avitoweatherapp.R
import com.karim.gabbasov.avitoweatherapp.data.util.DownloadImageUtil
import com.karim.gabbasov.avitoweatherapp.data.util.InternetLinks
import com.karim.gabbasov.avitoweatherapp.databinding.FragmentTodayWeatherBinding
import com.karim.gabbasov.avitoweatherapp.domain.weather.WeatherInfoModel
import com.karim.gabbasov.avitoweatherapp.presentation.recycler.HourlyWeatherAdapter
import com.karim.gabbasov.avitoweatherapp.presentation.recycler.WeatherForWeekAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

/**
 * Fragment to allow the user to view the detailed weather forecast for the day
 * and a short forecast for the week.
 */
@AndroidEntryPoint
class TodayWeatherFragment : Fragment() {
    private var _binding: FragmentTodayWeatherBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TodayWeatherViewModel by activityViewModels()

    @Inject
    lateinit var downloadImageUtil: DownloadImageUtil

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                viewModel.loadWeatherInfo()
            }
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                viewModel.loadWeatherInfo()
            }
            else -> {
                Snackbar.make(
                    binding.root,
                    R.string.storage_location_denied,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodayWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.todayWeatherStatus.collect { state ->
                    todayWeatherStateChanged(state)
                }
            }
        }
        checkLocationPreview()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun todayWeatherStateChanged(state: WeatherState) {
        if (state == WeatherState.Loading) {
            setLoading()
            return
        } else {
            hideLoading()
        }

        when (state) {
            is WeatherState.Success -> {
                if (state.weatherInfoModel != null) displayWeather(state.weatherInfoModel)
            }
            is WeatherState.Error -> {
                Toast.makeText(
                    requireContext(),
                    state.exception,
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {}
        }
    }

    /**
     * Drawing the received data on the screen using the data stored in [weatherInfoModel]
     */
    private fun displayWeather(weatherInfoModel: WeatherInfoModel) {
        weatherInfoModel.currentWeatherForHourModel.apply {
            binding.tvTemperature.text = getString(
                R.string.temperature_in_Celsius,
                temperature
            )
            binding.tvFeelsLikeTemperature.text = getString(
                R.string.feels_like_temperature_in_Celsius,
                feelsLikeTemperature
            )
            binding.tvWeatherCondition.text =
                weatherCondition.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                }
            binding.tvWindSpeedAndDirection.text = getString(
                R.string.wind_speed_and_direction,
                windSpeed.toString(),
                windDirection.uppercase()
            )
            binding.tvPressureInMM.text = getString(R.string.pressureInMM, pressureInMM)
            binding.tvHumidityInPercents.text = "$humidity%"

            downloadImageUtil.getImage(
                getString(
                    R.string.weather_icon_src,
                    InternetLinks.WEATHER_IMAGE_URL.link,
                    weatherIcon
                ),
                binding.ivWeather
            )
        }

        binding.tvCity.text = weatherInfoModel.locationModel.city
        binding.tvDistrict.text = weatherInfoModel.locationModel.district

        val hourlyWeatherRecyclerAdapter = HourlyWeatherAdapter(
            weatherInfoModel.hourlyModelWeatherData,
            downloadImageUtil
        )
        val hourlyWeatherRecyclerView = binding.hourlyWeatherRecycler
        hourlyWeatherRecyclerView.adapter = hourlyWeatherRecyclerAdapter

        val weekForecastRecyclerAdapter = WeatherForWeekAdapter(
            weatherInfoModel.weatherForWeekModel.dayWeather,
            downloadImageUtil
        ) { forecastForWeek -> adapterOnClick(forecastForWeek.index) }
        val weekForecastRecyclerView = binding.forecastForWeekRecycler
        weekForecastRecyclerView.adapter = weekForecastRecyclerAdapter
    }

    /**
     * Checks if permission to access the user's location has been received,
     * if there is permission, it requests weather data for the user's current location,
     * if there is no permission, then execute the method for requesting the required permission.
     */
    private fun checkLocationPreview() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.loadWeatherInfo()
        } else requestLocationPermission()
    }

    /**
     * Request permission for the user's fine or coarse location.
     */
    private fun requestLocationPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) ||
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        } else {
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun adapterOnClick(dayIndex: Int?) {
        if (dayIndex != null) {
            val action =
                TodayWeatherFragmentDirections.actionFirstFragmentToWeekForecastContainerFragment(
                    dayIndex
                )
            findNavController().navigate(action)
        }
    }

    private fun setLoading() {
        binding.apply {
            ivWindy.isVisible = false
            ivPressure.isVisible = false
            ivHumidity.isVisible = false
            tvForecastTitle.isVisible = false
            progressBar.isVisible = true
        }
    }

    private fun hideLoading() {
        binding.apply {
            ivWindy.isVisible = true
            ivPressure.isVisible = true
            ivHumidity.isVisible = true
            tvForecastTitle.isVisible = true
            progressBar.isVisible = false
        }
    }
}
