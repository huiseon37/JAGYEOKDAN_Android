package com.example.happylife

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.happylife.databinding.ActivityWriteReviewBinding
import com.example.happylife.model.ReviewData
import com.example.happylife.model.UserInfo
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_write_review.*

class WriteReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWriteReviewBinding

    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var databaseReference: DatabaseReference = firebaseDatabase.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteReviewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var difficulty = 0
        val certificateName = intent.getStringExtra("name")

        binding.licenseTestName.text = certificateName.toString()

        binding.star1.setOnClickListener {
            binding.star1.setBackgroundResource(R.drawable.star_purple)
            difficulty++
        }

        binding.star2.setOnClickListener {
            binding.star2.setBackgroundResource(R.drawable.star_purple)
            difficulty++
        }

        binding.star3.setOnClickListener {
            binding.star3.setBackgroundResource(R.drawable.star_purple)
            difficulty++
        }

        binding.star4.setOnClickListener {
            binding.star4.setBackgroundResource(R.drawable.star_purple)
            difficulty++
        }

        binding.star5.setOnClickListener {
            binding.star5.setBackgroundResource(R.drawable.star_purple)
            difficulty++
        }

        // DB 테이블 연결
        databaseReference = firebaseDatabase.getReference("review/$certificateName")

        // 완료 버튼 클릭
        // Todo: 포인트 지급
        binding.btnFinishWriteReview.setOnClickListener {
            val reviewInfo = ReviewData(
                difficulty,
                binding.userInputAboutQuestion2.text.toString(),
                binding.userInputAboutQuestion3.text.toString(),
                binding.etMoodWriteReview.text.toString(),
                binding.etTipWriteReview.text.toString(),
                binding.etCompanyWriteReview.text.toString()
            )

            databaseReference.setValue(reviewInfo)
        }
    }
}