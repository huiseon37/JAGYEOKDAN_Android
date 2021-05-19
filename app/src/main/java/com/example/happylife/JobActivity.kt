package com.example.happylife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.happylife.databinding.ActivityJobBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class JobActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJobBinding

    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var databaseReference: DatabaseReference = firebaseDatabase.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val nickname = intent.getStringExtra("nickname")

        databaseReference = firebaseDatabase.getReference("users/$nickname/jobs") // DB 테이블 연결

        binding.tvNicknameJob.text = nickname

        // 다음 버튼 클릭
        binding.btnNextJob.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // 경영, 사무
        binding.btnManagement.setOnClickListener {
            binding.btnManagement.setBackgroundResource(R.drawable.btn_jobs_selected)
            binding.btnManagement.setTextColor(ContextCompat.getColor(this, R.color.white))
            databaseReference.child("0").setValue("management")
        }

        // 마케팅, 광고, 홍보
        binding.btnMarketing.setOnClickListener {
            binding.btnMarketing.setBackgroundResource(R.drawable.btn_jobs_selected)
            binding.btnMarketing.setTextColor(ContextCompat.getColor(this, R.color.white))
            databaseReference.child("1").setValue("marketing")
        }

        // IT, 인터넷
        binding.btnIt.setOnClickListener {
            binding.btnIt.setBackgroundResource(R.drawable.btn_jobs_selected)
            binding.btnIt.setTextColor(ContextCompat.getColor(this, R.color.white))
            databaseReference.child("2").setValue("it")
        }

        // 디자인
        binding.btnDesign.setOnClickListener {
            binding.btnDesign.setBackgroundResource(R.drawable.btn_jobs_selected)
            binding.btnDesign.setTextColor(ContextCompat.getColor(this, R.color.white))
            databaseReference.child("3").setValue("design")
        }

        // 무역, 유통
        binding.btnTrade.setOnClickListener {
            binding.btnTrade.setBackgroundResource(R.drawable.btn_jobs_selected)
            binding.btnTrade.setTextColor(ContextCompat.getColor(this, R.color.white))
            databaseReference.child("4").setValue("trade")
        }

        // 영업, 고객상담
        binding.btnSales.setOnClickListener {
            binding.btnSales.setBackgroundResource(R.drawable.btn_jobs_selected)
            binding.btnSales.setTextColor(ContextCompat.getColor(this, R.color.white))
            databaseReference.child("5").setValue("sales")
        }

        // 서비스
        binding.btnService.setOnClickListener {
            binding.btnService.setBackgroundResource(R.drawable.btn_jobs_selected)
            binding.btnService.setTextColor(ContextCompat.getColor(this, R.color.white))
            databaseReference.child("6").setValue("service")
        }

        // 연구개발, 설계
        binding.btnResearch.setOnClickListener {
            binding.btnResearch.setBackgroundResource(R.drawable.btn_jobs_selected)
            binding.btnResearch.setTextColor(ContextCompat.getColor(this, R.color.white))
            databaseReference.child("7").setValue("research")
        }

        // 생산, 제조
        binding.btnProduce.setOnClickListener {
            binding.btnProduce.setBackgroundResource(R.drawable.btn_jobs_selected)
            binding.btnProduce.setTextColor(ContextCompat.getColor(this, R.color.white))
            databaseReference.child("8").setValue("produce")
        }

        // 교육
        binding.btnTeaching.setOnClickListener {
            binding.btnTeaching.setBackgroundResource(R.drawable.btn_jobs_selected)
            binding.btnTeaching.setTextColor(ContextCompat.getColor(this, R.color.white))
            databaseReference.child("9").setValue("teaching")
        }

        // 건설
        binding.btnConstruction.setOnClickListener {
            binding.btnConstruction.setBackgroundResource(R.drawable.btn_jobs_selected)
            binding.btnConstruction.setTextColor(ContextCompat.getColor(this, R.color.white))
            databaseReference.child("10").setValue("construction")
        }

        // 의료
        binding.btnMedical.setOnClickListener {
            binding.btnMedical.setBackgroundResource(R.drawable.btn_jobs_selected)
            binding.btnMedical.setTextColor(ContextCompat.getColor(this, R.color.white))
            databaseReference.child("11").setValue("medical")
        }

        // 미디어
        binding.btnMedia.setOnClickListener {
            binding.btnMedia.setBackgroundResource(R.drawable.btn_jobs_selected)
            binding.btnMedia.setTextColor(ContextCompat.getColor(this, R.color.white))
            databaseReference.child("12").setValue("media")
        }

        // 전문, 특수직
        binding.btnSpecial.setOnClickListener {
            binding.btnSpecial.setBackgroundResource(R.drawable.btn_jobs_selected)
            binding.btnSpecial.setTextColor(ContextCompat.getColor(this, R.color.white))
            databaseReference.child("13").setValue("special")
        }
    }
}