package com.labexam3.dilmith

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.labexam3.dilmith.databinding.FragmentHabitsBinding

class HabitsFragment : Fragment() {

    private var _binding: FragmentHabitsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHabitsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView
        binding.habitsRecyclerView.layoutManager = LinearLayoutManager(context)

        // Dummy data for the habit list
        val habits = listOf(
            Habit("Drink 8 glasses of water", true),
            Habit("Walk 10,000 steps", false),
            Habit("Meditate for 15 minutes", false),
            Habit("Read a chapter of a book", true)
        )

        val adapter = HabitAdapter(habits)
        binding.habitsRecyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}