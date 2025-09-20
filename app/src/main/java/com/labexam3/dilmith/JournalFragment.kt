package com.labexam3.dilmith

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.labexam3.dilmith.databinding.FragmentJournalBinding
import java.util.Date

class JournalFragment : Fragment() {

    private var _binding: FragmentJournalBinding? = null
    private val binding get() = _binding!!
    private lateinit var moodAdapter: MoodAdapter
    private val moodEntries = mutableListOf(
        MoodEntry("Feeling great today!", "😀", Date())
    )
    private var selectedEmoji: String = "😀"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJournalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        binding.buttonSelectEmoji.setOnClickListener {
            showEmojiPickerDialog()
        }

        // *** THIS IS THE FIX ***
        // The click listener is now on the correct button
        binding.buttonAddMood.setOnClickListener {
            val moodText = binding.editTextMood.text.toString()
            if (moodText.isNotBlank()) {
                addMoodEntry(moodText, selectedEmoji)
            }
        }
    }

    private fun setupRecyclerView() {
        moodAdapter = MoodAdapter(moodEntries)
        binding.moodRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.moodRecyclerView.adapter = moodAdapter
    }

    private fun showEmojiPickerDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_emoji_picker, null)
        val emojiRecyclerView = dialogView.findViewById<RecyclerView>(R.id.emoji_recycler_view)

        val emojis = listOf("😀", "😍", "😊", "🥳", "😴", "😢", "😠", "🤔")

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        val emojiAdapter = EmojiAdapter(emojis) { emoji ->
            selectedEmoji = emoji
            binding.buttonSelectEmoji.text = emoji // Update the emoji button text
            dialog.dismiss()
        }

        emojiRecyclerView.layoutManager = GridLayoutManager(context, 4)
        emojiRecyclerView.adapter = emojiAdapter

        dialog.show()
    }

    private fun addMoodEntry(text: String, emoji: String) {
        moodEntries.add(0, MoodEntry(text, emoji, Date()))
        moodAdapter.notifyItemInserted(0)
        binding.moodRecyclerView.scrollToPosition(0)

        // Clear input field and reset emoji
        binding.editTextMood.text.clear()
        selectedEmoji = "😀"
        binding.buttonSelectEmoji.text = selectedEmoji
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}