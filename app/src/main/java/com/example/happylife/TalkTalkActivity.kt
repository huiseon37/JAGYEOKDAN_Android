package com.example.happylife

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import com.example.happylife.databinding.ActivityTalkTalkBinding

class TalkTalkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTalkTalkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTalkTalkBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.talktalkTitle.text = intent.getStringExtra("title").toString()
        binding.talktalkTimestamp.text = intent.getStringExtra("timestamp").toString()
        binding.talktalkUserName.text = intent.getStringExtra("nickname").toString()
        binding.talktalkContext.text = intent.getStringExtra("context").toString()
        binding.talktalkBoardTag.text = intent.getStringExtra("commutag").toString()
        jobTagIntToString(intent.getStringExtra("jobtag").toString())

        // 공감 버튼 클릭
        binding.btnLikeContext.setOnClickListener {
            binding.btnLikeContext.setBackgroundResource(R.drawable.heart_purple)
            binding.tvHeartCountTalkTalk.text = 3.toString()
            binding.tvHeartCountTalkTalk.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.main_color
                )
            )
            binding.relativelayoutHeartTalkTalk.setBackgroundResource(R.drawable.purple_circle)
        }

        // 뒤로가기 버튼 클릭
        binding.btnBackTalkTalk.setOnClickListener {
            finish()
        }

        // 댓글 작성 완료버튼 클릭
        binding.btnFinishTalkTalk.setOnClickListener {
            binding.talktalkReplyContainer.visibility = View.VISIBLE
            binding.talktalkReplyText.text = binding.userCommentText.text.toString()
            binding.userCommentText.text = null

            // 키보드 내려가게
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.userCommentText.windowToken, 0)
        }
    }

    private fun jobTagIntToString(tagNum: String) {
        when (tagNum) {
            "1" -> {
                binding.talktalkTag1.text = "경영·사무"
            }
            "2" -> {
                binding.talktalkTag1.text = "마케팅·광고·홍보"
            }
            "3" -> {
                binding.talktalkTag1.text = "IT·인터넷"
            }
            "4" -> {
                binding.talktalkTag1.text = "디자인"
            }
            "5" -> {
                binding.talktalkTag1.text = "무역·유통"
            }
            "6" -> {
                binding.talktalkTag1.text = "영업·고객상담"
            }
        }
    }
}