package com.wafie.finboost_frontend.ui.onboarding.screens

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.wafie.finboost_frontend.R
import com.wafie.finboost_frontend.databinding.FragmentFirstScreenBinding
import com.wafie.finboost_frontend.databinding.FragmentSeccondScreenBinding
import com.wafie.finboost_frontend.ui.WelcomeActivity

class SeccondScreen : Fragment() {

    private var _binding: FragmentSeccondScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =   FragmentSeccondScreenBinding.inflate(inflater, container, false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        binding.ibNext.setOnClickListener {
            viewPager?.currentItem = 2
        }

        binding.btnSkip.setOnClickListener {
            val intent = Intent(requireContext(), WelcomeActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }
}