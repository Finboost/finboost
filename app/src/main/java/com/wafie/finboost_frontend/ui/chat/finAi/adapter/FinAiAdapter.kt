package com.wafie.finboost_frontend.ui.chat.finAi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.wafie.finboost_frontend.databinding.ItemAiAnswerShimmerBinding
import com.wafie.finboost_frontend.databinding.ItemAnswerAiBinding
import com.wafie.finboost_frontend.databinding.ItemUserQuestionBinding

class FinAiAdapter : ListAdapter<ChatItem, RecyclerView.ViewHolder>(FinAiDiffCallback()) {

    companion object {
        private const val VIEW_TYPE_USER_QUESTION = 0
        private const val VIEW_TYPE_AI_ANSWER = 1
        private const val VIEW_TYPE_SHIMMER = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ChatItem.UserQuestion -> VIEW_TYPE_USER_QUESTION
            is ChatItem.AiAnswer -> VIEW_TYPE_AI_ANSWER
            is ChatItem.Shimmer -> VIEW_TYPE_SHIMMER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_USER_QUESTION -> {
                val binding = ItemUserQuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                UserQuestionViewHolder(binding)
            }
            VIEW_TYPE_AI_ANSWER -> {
                val binding = ItemAnswerAiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                AiAnswerViewHolder(binding)
            }
            VIEW_TYPE_SHIMMER -> {
                val binding = ItemAiAnswerShimmerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ShimmerViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserQuestionViewHolder -> holder.bind(getItem(position) as ChatItem.UserQuestion)
            is AiAnswerViewHolder -> holder.bind(getItem(position) as ChatItem.AiAnswer)
            is ShimmerViewHolder -> holder.showShimmer()
        }
    }

    class UserQuestionViewHolder(private val binding: ItemUserQuestionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chatItem: ChatItem.UserQuestion) {
            binding.textViewUserQuestion.text = chatItem.question
        }
    }

    class AiAnswerViewHolder(private val binding: ItemAnswerAiBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chatItem: ChatItem.AiAnswer) {
            binding.tvAiAnswer.text = chatItem.answer
        }
    }

    class ShimmerViewHolder(private val binding: ItemAiAnswerShimmerBinding) : RecyclerView.ViewHolder(binding.root) {
        private val shimmerLayout: ShimmerFrameLayout = binding.shimmerLayout

        fun showShimmer() {
            shimmerLayout.startShimmer()
        }
    }

    class FinAiDiffCallback : DiffUtil.ItemCallback<ChatItem>() {
        override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
            return oldItem == newItem
        }
    }
}

sealed class ChatItem {
    abstract val id: String

    data class UserQuestion(val question: String, override val id: String = question) : ChatItem()
    data class AiAnswer(val answer: String, override val id: String = answer) : ChatItem()
    object Shimmer : ChatItem() {
        override val id: String = "shimmer"
    }
}
