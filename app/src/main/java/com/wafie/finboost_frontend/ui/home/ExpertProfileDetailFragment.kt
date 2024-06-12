package com.wafie.finboost_frontend.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wafie.finboost_frontend.R
import com.wafie.finboost_frontend.data.preferences.UserPreference
import com.wafie.finboost_frontend.data.preferences.dataStore
import com.wafie.finboost_frontend.databinding.FragmentDetailExpertProfileBinding
import com.wafie.finboost_frontend.ui.chat.expert.ChatRoom
import com.wafie.finboost_frontend.utils.Utils
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ExpertProfileDetailFragment : Fragment() {

    private var _binding: FragmentDetailExpertProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var userPreference: UserPreference
    private lateinit var expertId: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailExpertProfileBinding.inflate(inflater, container, false)

        userPreference = UserPreference(requireContext().dataStore)
        expertId = activity?.intent?.getStringExtra("EXTRA_EXPERT") ?: ""


        Log.d("ExpertDetail", "expertId: $expertId")
        navigateToChatRoom()
        return binding.root
    }

    private fun navigateToChatRoom() {
        binding.btnConsult.setOnClickListener {
            val token = runBlocking { userPreference.getSession().first().accessToken }
            val decodedJwt = Utils.jwtDecoder(token)
            val userId = decodedJwt?.getString("id").toString()

            val chatRoomId = Utils.generateChatRoomId(userId, expertId)
            val intent = Intent(activity, ChatRoom::class.java)

            intent.putExtra("CHAT_ROOM_ID", chatRoomId)
            startActivity(intent)

            Log.d("UserId", "userId: $userId")
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fullName = arguments?.getString("EXTRA_FULLNAME")

    }
}