package com.example.happylife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.happylife.databinding.ActivityMembershipBinding

class MembershipActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMembershipBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMembershipBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnSigninMembership.setOnClickListener {
            val intent = Intent(this, NicknameActivity::class.java)
            startActivity(intent)
        }
    }
}