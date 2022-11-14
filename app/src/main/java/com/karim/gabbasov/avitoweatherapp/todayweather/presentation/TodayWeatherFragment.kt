package com.karim.gabbasov.avitoweatherapp.todayweather.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.karim.gabbasov.avitoweatherapp.R
import com.karim.gabbasov.avitoweatherapp.databinding.FragmentTodayWeatherBinding
import com.karim.gabbasov.avitoweatherapp.todayweather.data.mappers.HourlyWeatherRecyclerMapper.getTodayWeatherModel
import com.karim.gabbasov.avitoweatherapp.todayweather.data.mappers.WeekWeatherRecyclerMapper.getWeekWeatherModel
import com.karim.gabbasov.avitoweatherapp.todayweather.data.util.DownloadImageUtil
import com.karim.gabbasov.avitoweatherapp.todayweather.data.util.InternetLinks
import com.karim.gabbasov.avitoweatherapp.todayweather.domain.model.WeatherInfoModel
import com.karim.gabbasov.avitoweatherapp.todayweather.presentation.recycler.HourlyWeatherAdapter
import com.karim.gabbasov.avitoweatherapp.todayweather.presentation.recycler.WeatherForWeekAdapter
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
    private val args: TodayWeatherFragmentArgs by navArgs()
    lateinit var swipeRefresh: SwipeRefreshLayout

    @Inject
    lateinit var downloadImageUtil: DownloadImageUtil

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                viewModel.loadWeatherInfo(args.coordinates)
            }
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                viewModel.loadWeatherInfo(args.coordinates)
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

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_today_weather, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.action_favorite -> {
                            findNavController().navigate(R.id.action_FirstFragment_to_favoritesFragment)
                            true
                        }
                        else -> false
                    }
                }
            },
            viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )
        swipeRefresh = binding.swipeRefresh
        swipeRefresh.setOnRefreshListener {
            checkLocationPreview()
            swipeRefresh.isRefreshing = false
        }
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
        if (!weatherInfoModel.locationModel.district.isNullOrBlank())
            binding.tvDistrict.text = weatherInfoModel.locationModel.district

        val hourlyWeatherRecyclerAdapter = HourlyWeatherAdapter(
            getTodayWeatherModel(weatherInfoModel.hourlyModelWeatherData),
            downloadImageUtil
        )
        val hourlyWeatherRecyclerView = binding.hourlyWeatherRecycler
        hourlyWeatherRecyclerView.adapter = hourlyWeatherRecyclerAdapter

        val weekForecastRecyclerAdapter = WeatherForWeekAdapter(
            getWeekWeatherModel(weatherInfoModel.weatherForWeekModel.dayWeather),
            downloadImageUtil
        ) { dayIndex -> adapterOnClick(dayIndex) }
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
            viewModel.loadWeatherInfo(args.coordinates)
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
            tvForecastTitle.isVisible = false
            progressBar.isVisible = true
        }
    }

    private fun hideLoading() {
        binding.apply {
            tvForecastTitle.isVisible = true
            progressBar.isVisible = false
        }
    }
}
