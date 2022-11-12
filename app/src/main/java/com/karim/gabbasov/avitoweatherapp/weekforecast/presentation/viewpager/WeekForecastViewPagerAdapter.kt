package com.karim.gabbasov.avitoweatherapp.weekforecast.presentation.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.karim.gabbasov.avitoweatherapp.weekforecast.presentation.WeekForecastFragment

const val DAYS_iN_WEEK = 7

/**
 * View pager adapter for use displaying the view pager pages.
 */
class WeekForecastViewPagerAdapter(
    advertisementListContainerFragment: WeekForecastContainerFragment
) : FragmentStateAdapter(advertisementListContainerFragment) {

    override fun getItemCount(): Int {
        return DAYS_iN_WEEK
    }

    override fun createFragment(position: Int): Fragment {
        return WeekForecastFragment.getInstance(position)
    }
}
