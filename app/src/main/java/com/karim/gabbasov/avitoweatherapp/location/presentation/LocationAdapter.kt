package com.karim.gabbasov.avitoweatherapp.location.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.karim.gabbasov.avitoweatherapp.databinding.ItemLocationAddressBinding
import com.karim.gabbasov.avitoweatherapp.location.domain.models.AddressModel

/**
 * Adapter for use displaying the address hints.
 */
class LocationAdapter(
    private val locations: List<AddressModel>,
    private val onClick: (AddressModel) -> Unit
) :
    RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    inner class LocationViewHolder(
        binding: ItemLocationAddressBinding,
        val context: Context,
        val onClick: (AddressModel) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private val cityName = binding.tvCityName
        private var currentAddressModel: AddressModel? = null

        init {
            itemView.setOnClickListener {
                currentAddressModel?.let {
                    onClick(it)
                }
            }
        }

        fun bind(city: AddressModel) {
            currentAddressModel = city
            cityName.text = city.cityName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLocationAddressBinding.inflate(
            inflater,
            parent,
            false
        )
        return LocationViewHolder(binding, parent.context, onClick)
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(locations[position])
    }
}
