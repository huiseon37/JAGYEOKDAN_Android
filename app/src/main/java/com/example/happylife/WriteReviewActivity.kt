package com.example.happylife

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.happylife.databinding.ActivityWriteReviewBinding

class WriteReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWriteReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteReviewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.star1.setOnClickListener {
            binding.star1.setBackgroundResource(R.drawable.star_purple)
        }

        binding.star2.setOnClickListener {
            binding.star2.setBackgroundResource(R.drawable.star_purple)
        }

        binding.star3.setOnClickListener {
            binding.star3.setBackgroundResource(R.drawable.star_purple)
        }

        binding.star4.setOnClickListener {
            binding.star4.setBackgroundResource(R.drawable.star_purple)
        }

        binding.star5.setOnClickListener {
            binding.star5.setBackgroundResource(R.drawable.star_purple)
        }
    }
}