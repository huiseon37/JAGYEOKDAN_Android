package com.example.happylife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.happylife.databinding.ActivityNicknameBinding

class NicknameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNicknameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNicknameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnSigninNickname.setOnClickListener {
            val intent = Intent(this, JobActivity::class.java)
            startActivity(intent)
        }
    }
}