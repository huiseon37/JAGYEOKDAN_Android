package com.example.happylife

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.happylife.databinding.ActivityLicenseDetailInfoBinding
import com.example.happylife.model.CertificateInfoData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.delay

class LicenseDetailInfo : AppCompatActivity() {

    private lateinit var binding: ActivityLicenseDetailInfoBinding
    private val nickname = MyApplication.prefs.getString("nickname", "")

    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var databaseReference: DatabaseReference = firebaseDatabase.reference

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLicenseDetailInfoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.tvCertificateNameDetail.text = intent.getStringExtra("name").toString()

        // 포인트 사용 버튼 클릭
        binding.usePoint.setOnClickListener {
            val dlg = UsePointDialog(this)

            dlg.setOnOKClickedListener {
                openReview()
                // Todo: 포인트 차감(-5)
            }
            dlg.start()
        }

        // 후기 작성하기 버튼 클릭
        binding.btnWriteReview.setOnClickListener {
            val intent = Intent(this, WriteReviewActivity::class.java)
            intent.putExtra("name", binding.tvCertificateNameDetail.text.toString())
            startActivity(intent)
        }

        // 신청 사이트 버튼 클릭
        binding.btnRegisterSite.setOnClickListener {
            val url = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.q-net.or.kr/crf005.do?id=crf00505&jmCd=1320")
            )
            startActivity(url)
        }

        // 기출 사이트 버튼 클릭
        binding.btnQuestionSite.setOnClickListener {
            val url = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.comcbt.com/xe/j4"))
            startActivity(url)
        }

        // 1회 실기 버튼 클릭
        binding.roundsSecond.setOnClickListener {
            changeInfo()
        }

        // 필터 선택
        binding.licenseDataClassification.setOnClickListener {
            val bottomSheet = TestRoundsFilterDialog()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
            Handler().postDelayed({
                binding.averagePreTime.text = "26"
                binding.avergeDifficulty.text = "4.1"
                binding.licenseDataClassificationText.text = "2020년 2회 필기"
            }, 3000)
        }

        // 찜하기
        binding.imgStarLicenseDetail.setOnClickListener {
            binding.imgStarLicenseDetail.setBackgroundResource(R.drawable.start_deep_purple)

            // DB 테이블 연결
            databaseReference = firebaseDatabase.getReference("users/$nickname/dibs")

            databaseReference.push().setValue(intent.getStringExtra("name").toString())

            Toast.makeText(applicationContext, "찜한 목록에 추가되었습니다.", Toast.LENGTH_SHORT).show()
        }

        // 뒤로가기 버튼 클릭
        binding.imvBackLicenseDetail.setOnClickListener {
            finish()
        }
    }

    // 리뷰 1개 오픈
    private fun openReview() {
        binding.shadeView.visibility = View.INVISIBLE
        binding.tvCommentLicenseDetail.visibility = View.INVISIBLE
        binding.tvComment2LicenseDetail.visibility = View.INVISIBLE
        binding.usePoint.visibility = View.INVISIBLE
    }

    // 자격증 정보
    private fun changeInfo() {

        binding.viewUnderbarTab2.visibility = View.VISIBLE

        val aniSlideOutRight: Animation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.slide_out_left)
        val aniSlideOutLeft: Animation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.slide_out_right)
        binding.viewUnderbarTab1.startAnimation(aniSlideOutRight)
        binding.viewUnderbarTab1.visibility = View.INVISIBLE
        binding.viewUnderbarTab2.startAnimation(aniSlideOutLeft)

        // DB 테이블 연결
        databaseReference = firebaseDatabase.getReference("certificate/it/정보처리기사")

        var applyPeriod: String

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    if (postSnapshot.key == "1회 실기") {
                        val item = postSnapshot.getValue(CertificateInfoData::class.java)
                        if (item != null) {
                            val startDate = item.examStartRegDate
                            val endDate = item.examEndRegDate
                            val cost = "${item.cost} 원"
                            applyPeriod = "$startDate ~ $endDate"

                            binding.applyDate.text = applyPeriod
                            binding.testDate.text = item.examDate
                            binding.resultDate.text = item.passStartDate
                            binding.applyCost.text = cost
                            binding.applyQualification.text = item.qualification.replace("bb", "\n")
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun changeContents(){

    }
}