package com.wafie.finboost_frontend.ui.chat.finAi.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.wafie.finboost_frontend.R

class FinAiQuestionAdapter(
    private var suggestions: List<String>,
    private val listener: (String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SHIMMER) {
            val shimmerView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_ai_suggestion_shimmer, parent, false)
            ShimmerViewHolder(shimmerView)
        } else {
            val dataView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_ai_suggestion, parent, false)
            DataViewHolder(dataView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == VIEW_TYPE_DATA) {
            (holder as DataViewHolder).bind(suggestions[position])
        } else {
            // Show shimmer animation
            (holder as ShimmerViewHolder).showShimmer()
        }
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < suggestions.size) VIEW_TYPE_DATA else VIEW_TYPE_SHIMMER
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSuggestions(newSuggestions: List<String>) {
        suggestions = newSuggestions
        notifyDataSetChanged()
    }

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val suggestionTextView: TextView = itemView.findViewById(R.id.tv_ai_suggestion)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener(suggestions[position])
                }
            }
        }

        fun bind(suggestion: String) {
            suggestionTextView.text = suggestion
        }
    }

    inner class ShimmerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val shimmerLayout: ShimmerFrameLayout = itemView.findViewById(R.id.shimmer_layout)

        fun showShimmer() {
            shimmerLayout.startShimmer()
        }
    }
    companion object {
        private const val  VIEW_TYPE_SHIMMER = 0
        private const  val VIEW_TYPE_DATA = 1
    }
}
