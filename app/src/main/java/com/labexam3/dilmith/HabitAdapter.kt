package com.labexam3.dilmith

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.labexam3.dilmith.databinding.ItemHabitBinding

class HabitAdapter(
    private val habits: MutableList<Habit>,
    private val onHabitChanged: (Habit) -> Unit,
    private val onHabitEdited: (Habit) -> Unit,
    private val onHabitDeleted: (Habit) -> Unit
) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    inner class HabitViewHolder(val binding: ItemHabitBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = ItemHabitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habits[position]
        holder.binding.habitName.text = habit.name
        holder.binding.habitCheckbox.isChecked = habit.isCompleted

        // Handle checkbox clicks
        holder.binding.habitCheckbox.setOnCheckedChangeListener { _, isChecked ->
            habit.isCompleted = isChecked
            onHabitChanged(habit)
        }

        // Handle click on the text to edit
        holder.binding.habitName.setOnClickListener {
            onHabitEdited(habit)
        }

        // Handle click on the delete button
        holder.binding.buttonDeleteHabit.setOnClickListener {
            onHabitDeleted(habit)
        }
    }

    override fun getItemCount() = habits.size
}