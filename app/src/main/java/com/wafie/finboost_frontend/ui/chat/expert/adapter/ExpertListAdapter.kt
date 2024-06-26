package com.wafie.finboost_frontend.ui.chat.expert.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wafie.finboost_frontend.R
import com.wafie.finboost_frontend.data.api.response.users.UsersItem
import com.wafie.finboost_frontend.databinding.ItemExpertListChatBinding
import com.wafie.finboost_frontend.ui.chat.expert.ChatRoom
import com.wafie.finboost_frontend.utils.Utils.generateChatRoomId


class ExpertListAdapter(private val userId: String): ListAdapter<UsersItem, ExpertListAdapter.ExpertViewHolder>(DIFF_CALLBACK) {
    inner class ExpertViewHolder(private val binding: ItemExpertListChatBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(expert: UsersItem) {
            with(binding) {
                if (expert.profile?.avatar != null) {
                    Glide.with(itemView.context)
                        .load(expert.profile.avatar)
                        .into(binding.ivExpertProfile)
                } else {
                    ivExpertProfile.setImageResource(R.drawable.place_holder)
                }

                tvExpertName.text = expert.fullName
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpertViewHolder {
        val binding = ItemExpertListChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExpertViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpertViewHolder, position: Int) {
        val expert = getItem(position)
        holder.bind(expert)

        holder.itemView.setOnClickListener {

            val chatRoomId = generateChatRoomId(userId, "${expert.id}")
            val intent = Intent(holder.itemView.context, ChatRoom::class.java)
            intent.putExtra("CHAT_ROOM_ID", chatRoomId)
            intent.putExtra("EXPERT_NAME", expert.fullName)
            holder.itemView.context.startActivity(intent)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UsersItem>() {
            override fun areItemsTheSame(oldItem: UsersItem, newItem: UsersItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: UsersItem, newItem: UsersItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}