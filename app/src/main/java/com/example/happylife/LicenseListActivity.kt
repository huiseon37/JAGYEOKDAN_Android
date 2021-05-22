package com.example.happylife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.happylife.databinding.ActivityLicenseListBinding

class LicenseListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLicenseListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_license_list)

        binding = ActivityLicenseListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.filterBtnListLicense.setOnClickListener {
            val bottomSheet = BottomFilterSheetDialog()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        binding.btnBackwardToLicenseInfo.setOnClickListener {
            finish()
        }

    }
}
