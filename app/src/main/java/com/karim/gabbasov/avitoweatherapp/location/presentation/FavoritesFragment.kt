package com.karim.gabbasov.avitoweatherapp.location.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.karim.gabbasov.avitoweatherapp.R
import com.karim.gabbasov.avitoweatherapp.databinding.FragmentFavoritiesBinding
import com.karim.gabbasov.avitoweatherapp.location.domain.models.AddressHintsModel
import com.karim.gabbasov.avitoweatherapp.location.domain.models.AddressModel
import com.karim.gabbasov.avitoweatherapp.location.presentation.util.onQueryTextChanged
import com.karim.gabbasov.avitoweatherapp.todayweather.domain.model.CoordinatesModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Fragment to aid user to chose weather location.
 */
@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritiesBinding? = null
    private val binding get() = _binding!!
    private val favoriteViewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                favoriteViewModel.locationStatus.collect { state ->
                    locationStateChanged(state)
                }
            }
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_favorites, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.onQueryTextChanged {
            favoriteViewModel.loadAddressHints(it)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun locationStateChanged(state: LocationState) {
        when (state) {
            is LocationState.Success -> {
                if (state.addressHintsModel != null) displaySearchResult(state.addressHintsModel)
            }
            is LocationState.Error -> {
                Log.d("location_error", state.exception.toString())
            }
        }
    }

    /**
     * Drawing the received data on the screen using the data stored in [addressHintsModel]
     */
    private fun displaySearchResult(addressHintsModel: AddressHintsModel) {
        val locationAdapter = LocationAdapter(
            addressHintsModel.addressHints
        ) { location ->
            adapterOnClick(location)
        }
        val addressesRecyclerView = binding.addressesRecycler
        addressesRecyclerView.adapter = locationAdapter
    }

    /**
     * Transition to TodayWeatherFragment fragment with [location] data.
     */
    private fun adapterOnClick(location: AddressModel) {
        val location = CoordinatesModel(
            latitude = location.latitude,
            longitude = location.longitude
        )
        val action = FavoritesFragmentDirections.actionFavoritesFragmentToFirstFragment()
        action.coordinates = location
        findNavController().navigate(action)
    }
}
