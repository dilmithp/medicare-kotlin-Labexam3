package com.labexam3.dilmith

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.labexam3.dilmith.databinding.FragmentHabitsBinding

class HabitsFragment : Fragment() {

    private var _binding: FragmentHabitsBinding? = null
    private val binding get() = _binding!!
    private lateinit var habitAdapter: HabitAdapter
    private val habitsList = mutableListOf(
        Habit("Drink 8 glasses of water", true),
        Habit("Walk 10,000 steps", false),
        Habit("Meditate for 15 minutes", false)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHabitsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        updateProgress()

        binding.fabAddHabit.setOnClickListener {
            showAddHabitDialog()
        }
    }

    private fun setupRecyclerView() {
        habitAdapter = HabitAdapter(
            habitsList,
            onHabitChanged = { habit ->
                updateProgress()
            },
            onHabitDeleted = { habit ->
                deleteHabit(habit)
            }
        )
        binding.habitsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.habitsRecyclerView.adapter = habitAdapter
    }

    private fun showAddHabitDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_habit, null)
        val editText = dialogView.findViewById<EditText>(R.id.edit_text_habit_name)

        AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton("Add") { dialog, _ ->
                val habitName = editText.text.toString()
                if (habitName.isNotBlank()) {
                    addHabit(Habit(habitName, false))
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    private fun addHabit(habit: Habit) {
        habitsList.add(habit)
        habitAdapter.notifyItemInserted(habitsList.size - 1)
        updateProgress()
    }

    private fun deleteHabit(habit: Habit) {
        val position = habitsList.indexOf(habit)
        if (position != -1) {
            habitsList.removeAt(position)
            habitAdapter.notifyItemRemoved(position)
            updateProgress()
        }
    }

    private fun updateProgress() {
        val completedCount = habitsList.count { it.isCompleted }
        val totalCount = habitsList.size
        val progress = if (totalCount > 0) {
            (completedCount * 100 / totalCount)
        } else {
            0
        }
        binding.habitProgressBar.progress = progress
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}