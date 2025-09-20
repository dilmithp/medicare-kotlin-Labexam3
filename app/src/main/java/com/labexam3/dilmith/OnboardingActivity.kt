package com.labexam3.dilmith

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.labexam3.dilmith.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val onboardingItems = listOf(
            OnboardingItem(
                R.drawable.img_welcome_banner,
                "Track Your Habits",
                "Create and manage your daily habits to build a better routine."
            ),
            OnboardingItem(
                R.drawable.ic_conversation,
                "Log Your Mood",
                "Keep a journal of your moods to understand your emotional patterns."
            ),
            OnboardingItem(
                R.drawable.ic_notifications,
                "Stay Hydrated",
                "Get timely reminders to drink water throughout the day."
            )
        )

        val adapter = OnboardingAdapter(onboardingItems)
        binding.viewPager.adapter = adapter

        binding.buttonNext.setOnClickListener {
            if (binding.viewPager.currentItem < adapter.itemCount - 1) {
                binding.viewPager.currentItem += 1
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }
}