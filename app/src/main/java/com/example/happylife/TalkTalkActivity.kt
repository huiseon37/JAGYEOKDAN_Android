package com.example.happylife

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.happylife.databinding.ActivityMembershipBinding
import com.example.happylife.databinding.ActivityTalkTalkBinding

class TalkTalkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTalkTalkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTalkTalkBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 공감 버튼 클릭
        binding.btnLikeContext.setOnClickListener {
            binding.btnLikeContext.setBackgroundResource(R.drawable.heart_purple)
            binding.tvHeartCountTalkTalk.text = 12.toString()
            binding.tvHeartCountTalkTalk.setTextColor(ContextCompat.getColor(this, R.color.main_color))
            binding.relativelayoutHeartTalkTalk.setBackgroundResource(R.drawable.purple_circle)
        }

        // 뒤로가기 버튼 클릭
        binding.btnBackTalkTalk.setOnClickListener {
            finish()
        }
    }
}