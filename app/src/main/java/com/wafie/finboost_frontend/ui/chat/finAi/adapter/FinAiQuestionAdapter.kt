package com.wafie.finboost_frontend.ui.chat.finAi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wafie.finboost_frontend.R

class FinAiQuestionAdapter(private var suggestions: List<String>, private val listener: (String) -> Unit) :
    RecyclerView.Adapter<FinAiQuestionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ai_suggestion, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val suggestion = suggestions[position]
        holder.bind(suggestion)
    }

    override fun getItemCount(): Int {
        return suggestions.size
    }

    fun updateSuggestions(newSuggestions: List<String>) {
        suggestions = newSuggestions
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
}
