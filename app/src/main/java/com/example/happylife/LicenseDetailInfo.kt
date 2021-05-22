package com.example.happylife

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.happylife.databinding.ActivityLicenseDetailInfoBinding

class LicenseDetailInfo : AppCompatActivity() {

    private lateinit var binding: ActivityLicenseDetailInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLicenseDetailInfoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.usePoint.setOnClickListener {
            val dlg = UsePointDialog(this)

            dlg.setOnOKClickedListener {
            }
            dlg.start()
        }
    }
}