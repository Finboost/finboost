package com.wafie.finboost_frontend.ui.home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import com.wafie.finboost_frontend.R
import com.wafie.finboost_frontend.data.api.response.users.UsersItem
import com.wafie.finboost_frontend.databinding.ItemExpertBinding
import com.wafie.finboost_frontend.ui.home.ExpertDetailActivity

class ExpertAdapter : ListAdapter<UsersItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    inner class ExpertViewHolder(private val binding: ItemExpertBinding) : RecyclerView.ViewHolder(binding.root) {
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

    inner class ShimmerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val shimmerLayout: ShimmerFrameLayout = itemView.findViewById(R.id.shimmer_layout)

        fun showShimmer() {
            shimmerLayout.startShimmer()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) == null) VIEW_TYPE_SHIMMER else VIEW_TYPE_DATA
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SHIMMER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_expert_shimmer, parent, false)
            ShimmerViewHolder(view)
        } else {
            val binding = ItemExpertBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ExpertViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return if (currentList.isEmpty()) 5 else super.getItemCount()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ShimmerViewHolder) {
            holder.showShimmer()
        } else if (holder is ExpertViewHolder) {
            val expert = getItem(position)
            holder.bind(expert)

            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, ExpertDetailActivity::class.java)
                intent.putExtra("EXTRA_EXPERT", expert.id)
                holder.itemView.context.startActivity(intent)
            }
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

        private const val VIEW_TYPE_SHIMMER = 0
        private const val VIEW_TYPE_DATA = 1
    }
}
