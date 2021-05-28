package com.example.happylife

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.happylife.databinding.ActivityAlarmBinding

class AlarmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlarmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        binding = ActivityAlarmBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnBackwardAtAlarm.setOnClickListener {
            finish()
        }
    }
}