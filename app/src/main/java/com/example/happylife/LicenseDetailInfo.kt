package com.example.happylife

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.happylife.databinding.ActivityLicenseDetailInfoBinding

class LicenseDetailInfo : AppCompatActivity() {

    private lateinit var binding: ActivityLicenseDetailInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLicenseDetailInfoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 포인트 사용 버튼 클릭
        binding.usePoint.setOnClickListener {
            val dlg = UsePointDialog(this)

            dlg.setOnOKClickedListener {
                openReview()
            }
            dlg.start()
        }

        // 신청 사이트 버튼 클릭
        binding.btnRegisterSite.setOnClickListener {
            val url = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.q-net.or.kr/crf005.do?id=crf00505&jmCd=1320"))
            startActivity(url)
        }

        // 기출 사이트 버튼 클릭
        binding.btnQuestionSite.setOnClickListener {
            val url = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.comcbt.com/xe/j4"))
            startActivity(url)
        }
    }

    // 리뷰 1개 오픈
    private fun openReview() {
        binding.shadeView.visibility = View.INVISIBLE
        binding.tvCommentLicenseDetail.visibility = View.INVISIBLE
        binding.tvComment2LicenseDetail.visibility = View.INVISIBLE
        binding.usePoint.visibility = View.INVISIBLE
    }
}