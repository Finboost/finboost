package com.wafie.finboost_frontend.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.wafie.finboost_frontend.R
import com.wafie.finboost_frontend.data.preferences.UserPreference
import com.wafie.finboost_frontend.data.preferences.dataStore
import com.wafie.finboost_frontend.databinding.ActivityExpertDetailBinding
import com.wafie.finboost_frontend.ui.home.adapter.FragmentPageAdapter
import com.wafie.finboost_frontend.ui.home.viewmodel.ExpertViewModel
import com.wafie.finboost_frontend.ui.home.viewmodel.ExpertViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ExpertDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExpertDetailBinding
    private lateinit var expertViewModel: ExpertViewModel

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2

    private lateinit var adapter: FragmentPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpertDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getStringExtra("EXTRA_USER")

        tabLayout()

        userId?.let {
            expertViewModel = ViewModelProvider(this,
                ExpertViewModelFactory(UserPreference
                    .getInstance(this.dataStore)))[ExpertViewModel::class.java]
            expertViewModel.getExpertById(it)
            expertViewModel.selectedExpertById.observe(this, Observer {expert ->
                if(expert != null) {
                    Log.d("ExpertDetail", "Full Name: ${expert.fullName}, Profile: ${expert.profile?.avatar}")
                    binding.tvExpertName.text = expert.fullName
                    binding.tvExpertName.text = expert.fullName
                    Glide.with(this)
                        .load(expert.profile?.avatar)
                        .into(binding.ivDetailPhoto)

                    binding.topAppBar.title = "Expert ${expert.fullName}"
                }
            })
            Log.d("ExpertDetail", "id: $userId")
        }

        onBackPress()
    }

    private fun tabLayout() {
        tabLayout = binding.tabLayout
        viewPager2 = binding.viewPager2

        adapter = FragmentPageAdapter(supportFragmentManager, lifecycle)
        viewPager2.adapter = adapter

        tabLayout.addTab(tabLayout.newTab().setText("Profile Expert"))
        tabLayout.addTab(tabLayout.newTab().setText("Jadwal"))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewPager2.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Do nothing
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Do nothing
            }
        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }

    private fun onBackPress() {
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed() // Kembali ke halaman sebelumnya
        }
    }


}
