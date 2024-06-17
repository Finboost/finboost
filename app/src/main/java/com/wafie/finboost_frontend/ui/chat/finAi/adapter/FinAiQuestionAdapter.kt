package com.wafie.finboost_frontend.ui.chat.finAi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wafie.finboost_frontend.R

class FinAiQuestionAdapter(private var suggestions: List<String>) : RecyclerView.Adapter<FinAiQuestionAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val suggestionTextView: TextView = view.findViewById(R.id.tv_ai_suggestion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ai_suggestion, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.suggestionTextView.text = suggestions[position]
    }

    override fun getItemCount(): Int {
        return suggestions.size
    }

    fun updateSuggestions(newSuggestions: List<String>) {
        suggestions = newSuggestions
        notifyDataSetChanged()
    }
}
