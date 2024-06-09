package com.wafie.finboost_frontend.ui.onboarding.screens

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
            Handler(Looper.getMainLooper()).post {
                val intent = Intent(requireContext(), WelcomeActivity::class.java)
                startActivity(intent)
                activity?.finish() // Finish the current activity if needed
            }
        }
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}