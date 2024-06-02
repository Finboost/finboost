package com.wafie.finboost_frontend.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wafie.finboost_frontend.data.model.Expert
import com.wafie.finboost_frontend.databinding.ItemExpertBinding

class ExpertAdapter(private val experts: List<Expert>):
    RecyclerView.Adapter<ExpertAdapter.ExpertViewHolder>() {

    inner class ExpertViewHolder(private val binding: ItemExpertBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(expert: Expert) {
            with(binding) {
                ivExpertProfile.setImageResource(expert.expertPhoto)
                tvExpertName.text = expert.expertName
            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExpertAdapter.ExpertViewHolder {
        val binding = ItemExpertBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExpertViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpertAdapter.ExpertViewHolder, position: Int) {
        holder.bind(experts[position])
    }

    override fun getItemCount(): Int = experts.size
}