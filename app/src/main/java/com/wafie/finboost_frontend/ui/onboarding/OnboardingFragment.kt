package com.wafie.finboost_frontend.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator
import com.wafie.finboost_frontend.R
import com.wafie.finboost_frontend.ui.onboarding.screens.FirstScreen
import com.wafie.finboost_frontend.ui.onboarding.screens.SeccondScreen
import com.wafie.finboost_frontend.ui.onboarding.screens.ThirdScreen

class OnboardingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         val view  = inflater.inflate(R.layout.fragment_onboarding, container, false)

        val fragmentList = arrayListOf(
            FirstScreen(),
            SeccondScreen(),
            ThirdScreen()
        )

        val adapter = OnboardingAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )


        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        viewPager.adapter = adapter
        val springDotsIndicator = view.findViewById<DotsIndicator>(R.id.dots_indicator)
        springDotsIndicator.attachTo(viewPager)

        return view


}