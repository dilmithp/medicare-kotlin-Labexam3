package com.labexam3.dilmith

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.labexam3.dilmith.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener {
            // In a real app, you would validate credentials here
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.textSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
}