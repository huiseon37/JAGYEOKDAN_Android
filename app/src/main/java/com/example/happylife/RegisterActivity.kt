package com.example.happylife

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.happylife.databinding.ActivityRegisterBinding
import com.example.happylife.model.CertificateRegData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mancj.materialsearchbar.MaterialSearchBar

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private var certificateList = arrayListOf<CertificateRegData>()

    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var databaseReference: DatabaseReference = firebaseDatabase.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Adapter 연결
        val certificateAdapter = CertificateRegAdapter(this, certificateList)
        binding.rvCertificateReg.adapter = certificateAdapter

        val nickname = MyApplication.prefs.getString("nickname", "")

        binding.tvNicknameRegister.text = nickname

        // DB 테이블 연결
        databaseReference = firebaseDatabase.getReference("users/$nickname/ownCertificate")

        // 자격증 리스트
        val jobList = arrayOf(
            "건축구조기술사",
            "경영지도사",
            "공인중개사",
            "리눅스마스터 1급",
            "변리사",
            "복어조리기능사",
            "빅데이터분석기사",
            "실내건축기능사",
            "유통관리사 2급",
            "인터넷 정보관리사 1급",
            "일식조리기능사",
            "전산세무회계 1급",
            "전자상거래관리사 1급",
            "전자상거래운용사 1급",
            "정보보안기사",
            "정보처리기사",
            "SQL 개발자(SQLD)"
        )

        // 검색 결과 리스트뷰 초기에 안보이게 설정
        binding.lvResultRegister.visibility = View.INVISIBLE

        // 나중에 하기 클릭
        binding.linearlayoutLaterRegister.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, jobList)

        // 리스트뷰 초기에 안보이도록
        binding.lvResultRegister.visibility = View.INVISIBLE

        // SearchBar와 ListView 연동
        binding.lvResultRegister.adapter = adapter
        binding.msbCertificateRegister.setOnSearchActionListener(object :
            MaterialSearchBar.OnSearchActionListener {
            override fun onButtonClicked(buttonCode: Int) {
            }

            // 검색창 누른 상태 여부 확인
            override fun onSearchStateChanged(enabled: Boolean) {
                // 맞음 -> 리스트뷰 보이도록
                if (enabled) {
                    binding.lvResultRegister.visibility = View.VISIBLE
                } else { // 아님 -> 리스트뷰 숨김
                    binding.lvResultRegister.visibility = View.INVISIBLE
                }
            }

            override fun onSearchConfirmed(text: CharSequence?) {
            }
        })

        binding.msbCertificateRegister.addTextChangeListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            // 검색어 변경시 ListView 내용 변경
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s)
            }

        })

        // list 아이템 클릭
        binding.lvResultRegister.setOnItemClickListener { parent, view, position, id ->
            binding.lvResultRegister.visibility = View.INVISIBLE
            binding.msbCertificateRegister.disableSearch()

            // 보유 자격증 리스트에 선택한 자격증 추가
            certificateList.add(
                CertificateRegData(adapter.getItem(position)!!.toString())
            )
            binding.rvCertificateReg.adapter?.notifyDataSetChanged()

            // db에 보유한 자격증 정보 저장
            databaseReference.child(position.toString())
                .setValue(adapter.getItem(position)!!.toString())
        }

        // 시작하기 버튼 클릭
        binding.btnStartRegister.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("nickname", binding.tvNicknameRegister.text)
            startActivity(intent)
        }
    }
}