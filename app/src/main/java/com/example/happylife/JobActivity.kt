package com.example.happylife

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.happylife.databinding.ActivityJobBinding

class JobActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJobBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.tvNicknameJob.text = intent.getStringExtra("nickname")

        // 경영, 사무
        binding.btnManagement.setOnClickListener {
            binding.btnManagement.setBackgroundResource(R.drawable.btn_jobs_selected)
            binding.btnManagement.setTextColor(ContextCompat.getColor(this, R.color.white))
        }

        // 마케팅, 광고, 홍보
        binding.btnMarketing.setOnClickListener {
            binding.btnMarketing.setBackgroundResource(R.drawable.btn_jobs_selected)
            binding.btnMarketing.setTextColor(ContextCompat.getColor(this, R.color.white))
        }

        // IT, 인터넷
        binding.btnIt.setOnClickListener {
            binding.btnIt.setBackgroundResource(R.drawable.btn_jobs_selected)
            binding.btnIt.setTextColor(ContextCompat.getColor(this, R.color.white))
        }

        // 디자인
        binding.btnDesign.setOnClickListener {
            binding.btnDesign.setBackgroundResource(R.drawable.btn_jobs_selected)
            binding.btnDesign.setTextColor(ContextCompat.getColor(this, R.color.white))
        }

        // 무역, 유통
        binding.btnTrade.setOnClickListener {
            binding.btnTrade.setBackgroundResource(R.drawable.btn_jobs_selected)
            binding.btnTrade.setTextColor(ContextCompat.getColor(this, R.color.white))
        }

        // 영업, 고객상담
        binding.btnSales.setOnClickListener {
            binding.btnSales.setBackgroundResource(R.drawable.btn_jobs_selected)
            binding.btnSales.setTextColor(ContextCompat.getColor(this, R.color.white))
        }

        // 서비스
        binding.btnService.setOnClickListener {
            binding.btnService.setBackgroundResource(R.drawable.btn_jobs_selected)
            binding.btnService.setTextColor(ContextCompat.getColor(this, R.color.white))
        }

        // 연구개발, 설계
        binding.btnResearch.setOnClickListener {
            binding.btnResearch.setBackgroundResource(R.drawable.btn_jobs_selected)
            binding.btnResearch.setTextColor(ContextCompat.getColor(this, R.color.white))
        }

        // 생산, 제조
        binding.btnProduce.setOnClickListener {
            binding.btnProduce.setBackgroundResource(R.drawable.btn_jobs_selected)
            binding.btnProduce.setTextColor(ContextCompat.getColor(this, R.color.white))
        }

        // 교육
        binding.btnTeaching.setOnClickListener {
            binding.btnTeaching.setBackgroundResource(R.drawable.btn_jobs_selected)
            binding.btnTeaching.setTextColor(ContextCompat.getColor(this, R.color.white))
        }

        // 건설
        binding.btnConstruction.setOnClickListener {
            binding.btnConstruction.setBackgroundResource(R.drawable.btn_jobs_selected)
            binding.btnConstruction.setTextColor(ContextCompat.getColor(this, R.color.white))
        }

        // 의료
        binding.btnMedical.setOnClickListener {
            binding.btnMedical.setBackgroundResource(R.drawable.btn_jobs_selected)
            binding.btnMedical.setTextColor(ContextCompat.getColor(this, R.color.white))
        }

        // 미디어
        binding.btnMedia.setOnClickListener {
            binding.btnMedia.setBackgroundResource(R.drawable.btn_jobs_selected)
            binding.btnMedia.setTextColor(ContextCompat.getColor(this, R.color.white))
        }

        // 전문, 특수직
        binding.btnSpecial.setOnClickListener {
            binding.btnSpecial.setBackgroundResource(R.drawable.btn_jobs_selected)
            binding.btnSpecial.setTextColor(ContextCompat.getColor(this, R.color.white))
        }
    }
}