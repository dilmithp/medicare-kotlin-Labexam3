package com.labexam3.dilmith

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.labexam3.dilmith.databinding.FragmentHabitsBinding

class HabitsFragment : Fragment() {

    private var _binding: FragmentHabitsBinding? = null
    private val binding get() = _binding!!
    private lateinit var habitAdapter: HabitAdapter
    private val habitsList = mutableListOf<Habit>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHabitsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadHabits() // Load saved habits when the view is created
        setupRecyclerView()
        updateProgress()

        binding.fabAddHabit.setOnClickListener {
            showAddOrEditHabitDialog()
        }
    }

    private fun setupRecyclerView() {
        habitAdapter = HabitAdapter(
            habitsList,
            onHabitChanged = {
                saveHabitsAndUpdateWidget()
                updateProgress()
            },
            onHabitEdited = { habit ->
                showAddOrEditHabitDialog(habit)
            },
            onHabitDeleted = { habit ->
                deleteHabit(habit)
            }
        )
        binding.habitsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.habitsRecyclerView.adapter = habitAdapter
    }

    private fun showAddOrEditHabitDialog(habitToEdit: Habit? = null) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_habit, null)
        val editText = dialogView.findViewById<EditText>(R.id.edit_text_habit_name)

        val title = if (habitToEdit == null) "Add New Habit" else "Edit Habit"
        val positiveButtonText = if (habitToEdit == null) "Add" else "Save"

        habitToEdit?.let {
            editText.setText(it.name)
        }

        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setView(dialogView)
            .setPositiveButton(positiveButtonText) { dialog, _ ->
                val habitName = editText.text.toString()
                if (habitName.isNotBlank()) {
                    if (habitToEdit == null) {
                        addHabit(Habit(habitName, false))
                    } else {
                        updateHabit(habitToEdit, habitName)
                    }
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
        saveHabitsAndUpdateWidget()
        updateProgress()
    }

    private fun updateHabit(habit: Habit, newName: String) {
        val position = habitsList.indexOf(habit)
        if (position != -1) {
            habitsList[position].name = newName
            habitAdapter.notifyItemChanged(position)
            saveHabitsAndUpdateWidget()
        }
    }

    private fun deleteHabit(habit: Habit) {
        val position = habitsList.indexOf(habit)
        if (position != -1) {
            habitsList.removeAt(position)
            habitAdapter.notifyItemRemoved(position)
            saveHabitsAndUpdateWidget()
            updateProgress()
        }
    }

    private fun updateProgress() {
        val completedCount = habitsList.count { it.isCompleted }
        val totalCount = habitsList.size
        val progress = if (totalCount > 0) (completedCount * 100 / totalCount) else 0
        binding.habitProgressBar.progress = progress
    }

    private fun saveHabitsAndUpdateWidget() {
        // Save the current list of habits to SharedPreferences
        val prefs = requireActivity().getSharedPreferences("habit_prefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(habitsList)
        editor.putString("habits_list", json)
        editor.apply()

        // Send a broadcast to update the widget
        val intent = Intent(requireContext(), HabitWidgetProvider::class.java)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        val ids = AppWidgetManager.getInstance(requireContext())
            .getAppWidgetIds(ComponentName(requireContext(), HabitWidgetProvider::class.java))
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        requireContext().sendBroadcast(intent)
    }

    private fun loadHabits() {
        val prefs = requireActivity().getSharedPreferences("habit_prefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString("habits_list", null)
        if (json != null) {
            val type = object : TypeToken<MutableList<Habit>>() {}.type
            habitsList.clear()
            habitsList.addAll(gson.fromJson(json, type))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}