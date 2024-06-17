package com.wafie.finboost_frontend.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.wafie.finboost_frontend.data.preferences.UserPreference
import com.wafie.finboost_frontend.data.preferences.dataStore
import com.wafie.finboost_frontend.databinding.FragmentHomeBinding
import com.wafie.finboost_frontend.ui.chat.finAi.FinAiChat
import com.wafie.finboost_frontend.ui.home.adapter.ExpertAdapter
import com.wafie.finboost_frontend.ui.home.viewmodel.ExpertViewModel
import com.wafie.finboost_frontend.ui.home.viewmodel.ExpertViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var expertViewModel: ExpertViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var userPreference: UserPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userPreference = UserPreference.getInstance(requireContext().dataStore)

        expertViewModel = ViewModelProvider(this, ExpertViewModelFactory(userPreference))[ExpertViewModel::class.java]

        val expertAdapter = ExpertAdapter()
        binding.rvExpert.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = expertAdapter
        }
        expertViewModel.getListUser("Expert")

        expertViewModel.expertList.observe(viewLifecycleOwner, Observer { expertList ->
            expertList?.let { expertAdapter.submitList(it) }
        })

        setupToolbar()

        binding.ibFinAi.setOnClickListener {
            startActivity(Intent(requireContext().applicationContext, FinAiChat::class.java))
        }

        binding.ibAnalytics.setOnClickListener {
         toast("Coming Soon!")
        }

        binding.ibArticle.setOnClickListener {
            toast("Coming Soon!")
        }

        binding.ibTrackFinance.setOnClickListener {
            toast("Coming Soon!")
        }
    }

    private fun setupToolbar() {
        viewLifecycleOwner.lifecycleScope.launch {
            val user = userPreference.getSession().first()
            val userName = user.fullName
            binding.topAppBar.title = "Hi, $userName"
        }
    }

    private fun toast(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}