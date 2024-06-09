package com.wafie.finboost_frontend.ui.chat.finAi.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wafie.finboost_frontend.databinding.ItemChatAiBinding

class FinAiAdapter: ListAdapter<String, FinAiAdapter.ViewHolder>(FinAiDiffCallback()) {


    class ViewHolder(private val binding: ItemChatAiBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(question: String) {
            binding.tvMessage.text = question
        }
    }
    class FinAiDiffCallback: DiffUtil.ItemCallback<String> () {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChatAiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}