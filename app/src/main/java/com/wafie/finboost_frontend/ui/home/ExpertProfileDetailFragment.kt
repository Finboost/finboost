package com.wafie.finboost_frontend.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.wafie.finboost_frontend.data.preferences.UserPreference
import com.wafie.finboost_frontend.data.preferences.dataStore
import com.wafie.finboost_frontend.databinding.FragmentDetailExpertProfileBinding
import com.wafie.finboost_frontend.ui.chat.expert.ChatRoom
import com.wafie.finboost_frontend.ui.home.viewmodel.ExpertViewModel
import com.wafie.finboost_frontend.ui.home.viewmodel.ExpertViewModelFactory
import com.wafie.finboost_frontend.utils.Utils
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ExpertProfileDetailFragment : Fragment() {

    private lateinit var expertViewModel: ExpertViewModel
    private var _binding: FragmentDetailExpertProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var userPreference: UserPreference
    private lateinit var expertId: String
    private lateinit var expertName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailExpertProfileBinding.inflate(inflater, container, false)

        userPreference = UserPreference(requireContext().dataStore)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        expertViewModel = ViewModelProvider(this,
            ExpertViewModelFactory(userPreference))[ExpertViewModel::class.java]

        expertId = activity?.intent?.getStringExtra("EXTRA_EXPERT") ?: ""

        expertName = activity?.intent?.getStringExtra("EXPERT_NAME") ?: ""
        navigateToChatRoom(expertName)

        expertId.let {
            expertViewModel.getExpertById(it)
            expertViewModel.selectedExpertById.observe(viewLifecycleOwner, Observer { expert ->
                if (expert != null) {
                    Log.d("ExpertDetail", "Full Name: ${expert.fullName}, Profile: ${expert.profile?.avatar}")
                    binding.tvDescExpert.text = expert.profile?.about
                    binding.tvAbout.text = "Tentang ${expert.fullName}"
                    binding.tvEduHistory.text = expert.profile?.education?.name
                }
            })
        }
    }

    private fun navigateToChatRoom(expertName: String?) {
        binding.btnConsult.setOnClickListener {
            val token = runBlocking { userPreference.getSession().first().accessToken }
            val decodedJwt = Utils.jwtDecoder(token)
            val userId = decodedJwt?.getString("id").toString()

            val chatRoomId = Utils.generateChatRoomId(userId, expertId)
            val intent = Intent(activity, ChatRoom::class.java)

            intent.putExtra("CHAT_ROOM_ID", chatRoomId)
            intent.putExtra("EXPERT_NAME", expertName)
            startActivity(intent)

            Log.d("NavigateToChatRoom", "Navigating to ChatRoom with Expert Name: $expertName")
        }
    }
}
