package com.example.happylife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.happylife.databinding.ActivityLicenseListBinding

class LicenseListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLicenseListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_license_list)

        binding = ActivityLicenseListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.licenseListCategoryName.text = intent.getStringExtra("name")

        // 1위 자격증 선택
        binding.linearlayoutNameOfRank1License.setOnClickListener {
            val intent = Intent(this, LicenseDetailInfo::class.java)
            intent.putExtra("name", binding.tvNameOfRank1License.text.toString())
            startActivity(intent)
        }

        // 필터 선택
        binding.filterBtnListLicense.setOnClickListener {
            val bottomSheet = BottomFilterSheetDialog()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        // 뒤로가기 버튼 클릭
        binding.btnBackwardToLicenseInfo.setOnClickListener {
            finish()
        }
    }
}
