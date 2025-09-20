package com.labexam3.dilmith

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.labexam3.dilmith.databinding.ItemMoodBinding
import java.text.SimpleDateFormat
import java.util.Locale

class MoodAdapter(private val moodEntries: List<MoodEntry>) : RecyclerView.Adapter<MoodAdapter.MoodViewHolder>() {

    inner class MoodViewHolder(val binding: ItemMoodBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodViewHolder {
        val binding = ItemMoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoodViewHolder, position: Int) {
        val moodEntry = moodEntries[position]
        holder.binding.moodText.text = moodEntry.text
        holder.binding.moodEmoji.setImageResource(moodEntry.emojiResId)

        val sdf = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
        holder.binding.moodTimestamp.text = sdf.format(moodEntry.timestamp)
    }

    override fun getItemCount() = moodEntries.size
}