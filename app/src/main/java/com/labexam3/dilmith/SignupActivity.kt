package com.labexam3.dilmith

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.labexam3.dilmith.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSignup.setOnClickListener {
            // In a real app, you would register the user here
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.textLogin.setOnClickListener {
            finish() // Go back to the LoginActivity
        }
    }
}