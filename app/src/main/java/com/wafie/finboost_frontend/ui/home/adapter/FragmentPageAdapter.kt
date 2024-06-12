package com.wafie.finboost_frontend.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.wafie.finboost_frontend.ui.home.ExpertProfileDetailFragment
import com.wafie.finboost_frontend.ui.home.ExpertScheduleFragment

class FragmentPageAdapter(
    fm: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if( position ==0 )
            ExpertProfileDetailFragment()
        else
            ExpertScheduleFragment()
    }
}