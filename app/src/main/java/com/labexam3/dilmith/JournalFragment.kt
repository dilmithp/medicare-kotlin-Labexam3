package com.labexam3.dilmith

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.labexam3.dilmith.databinding.FragmentJournalBinding
import java.util.Date

class JournalFragment : Fragment() {

    private var _binding: FragmentJournalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJournalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.moodRecyclerView.layoutManager = LinearLayoutManager(context)

        // Dummy data for the mood list
        val moodEntries = listOf(
            MoodEntry("Feeling great today!", R.drawable.ic_journal, Date()),
            MoodEntry("A bit tired, but productive.", R.drawable.ic_journal, Date()),
            MoodEntry("Ready for the weekend!", R.drawable.ic_journal, Date())
        )

        val adapter = MoodAdapter(moodEntries)
        binding.moodRecyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}