package com.example.happylife.navigation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.happylife.*
import com.example.happylife.R
import com.example.happylife.model.CertificateInfoData
import com.example.happylife.model.RecCertificateData
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_recommend_certificate.view.*
import java.text.SimpleDateFormat
import java.util.*

class HomeViewFragment : Fragment() {

    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var databaseReference: DatabaseReference = firebaseDatabase.reference
    private val nickname = MyApplication.prefs.getString("nickname", "")
    private val userCertificateList = arrayListOf<String>()
    private val userInterests = arrayListOf<String>()
    private val recommendList = arrayListOf<RecCertificateData>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_recommend_certificate?.adapter = RecCertificateAdapter()
        rv_recommend_certificate?.layoutManager = LinearLayoutManager(context)
        rv_recommend_certificate?.setHasFixedSize(true) // recyclerview 크기 고정

        // 필터 클릭
        imv_filter_home.setOnClickListener {
            val filterSheet = BottomFilterSheetDialog()
            filterSheet.show(this.parentFragmentManager, filterSheet.tag)
        }

        // 더보기 버튼 클릭
        look_more.setOnClickListener {
            val params: ViewGroup.LayoutParams = rv_recommend_certificate.layoutParams
            params.height = rv_recommend_certificate.height + 280
            rv_recommend_certificate.layoutParams = params
        }

        // 알람 아이콘 클릭
        watch_alarm_button_home.setOnClickListener {
            val intent = Intent(context, AlarmActivity::class.java)
            startActivity(intent)
        }

        // 검색 버튼 클릭
        top_search_button_home.setOnClickListener {
            val intent = Intent(context, LicenseDetailInfo::class.java)
            intent.putExtra("name", "정보처리기사")
            startActivity(intent)
        }

        // 12시 지난 후 다이얼로그
        dday_licensebox_third.setOnClickListener {
            val dlg = ReviewDialog(requireContext())

            dlg.setOnOKClickedListener {
                val intent = Intent(context, WriteReviewActivity::class.java)
                intent.putExtra("name", "정보처리기사")
                startActivity(intent)
            }

            dlg.start()
        }

        // 메인 화면 카드 선택 시 자격증 상세 정보 화면으로 이동
        dday_licensebox_yellow.setOnClickListener {
            val intent = Intent(context, LicenseDetailInfo::class.java)
            intent.putExtra("name", "정보처리기사")
            startActivity(intent)
        }
        dday_licensebox_second.setOnClickListener {
            val intent = Intent(context, LicenseDetailInfo::class.java)
            intent.putExtra("name", "정보처리기사")
            startActivity(intent)
        }

        imv_plus_home3.setOnClickListener {
            val licenseInfoFragment = LicenceInfoFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_screen_panel, licenseInfoFragment).commit()
            //binding.bottomNavigation.selectTabAt(1)
            MyApplication.prefs.setString("isClicked", "true")
            println(MyApplication.prefs.getString("isClicked", "none"))
        }

        postCertificateCards()
    }

    inner class RecCertificateAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        init {
            // ArrayList 비워줌
            recommendList.clear()

            // 사용자의 관심 분야 목록 받아오기
            // DB 테이블 연결(관심 분야)
            databaseReference = firebaseDatabase.getReference("users/$nickname/jobs")

            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    // userInterests 비워줌
                    userInterests.clear()

                    for (postSnapshot in dataSnapshot.children) {
                        userInterests.add(postSnapshot.value.toString())

                        // 관심 분야에 해당하는 자격증 리스트 받아오기
                        val category = postSnapshot.value.toString()

                        // DB 테이블 연결
                        databaseReference = firebaseDatabase.getReference("certificate/$category")

                        // 신청 기간 임박한 순으로 정렬
                        databaseReference.orderByChild("examStartRegDate")
                            .addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    // 자격증 이름 & 신청 날짜 받아서 리스트에 추가
                                    for (postSnapshot in dataSnapshot.children) {
                                        postSnapshot.children.forEachIndexed { index, dataSnapshot ->
                                            if (postSnapshot.key.toString() != "정보처리기사") {
                                                // 각 자격증에서 신청 기간이 제일 임박한 회차 정보 1개만 가져오기
                                                if (index == 0) {
                                                    dataSnapshot.getValue(CertificateInfoData::class.java)
                                                        ?.let {
                                                            RecCertificateData(
                                                                postSnapshot.key.toString(),
                                                                getDDay(it.examStartRegDate)
                                                            )
                                                        }?.let {
                                                            recommendList.add(
                                                                it
                                                            )
                                                        }
                                                }
                                            }
                                        }
                                    }
                                    // 신청 날짜 임박한 순으로 정렬
                                    recommendList.sortBy { it.d_day }

                                    notifyDataSetChanged()
                                }

                                override fun onCancelled(error: DatabaseError) {
                                }
                            })
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })

            // 사용자가 보유한 자격증 목록 받아오기
            getUserCertificates()
        }

        // xml파일을 inflate하여 ViewHolder를 생성
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recommend_certificate, parent, false)
            return ViewHolder(view)
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        }

        // onCreateViewHolder에서 만든 view와 실제 데이터를 연결
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = (holder as ViewHolder).itemView
            viewHolder.tv_certificate_rec_item?.text = recommendList[position].name
            viewHolder.tv_d_day_item?.text = recommendList[position].d_day.toString()

            // recyclerview item click listener
            viewHolder.setOnClickListener {
            }
        }

        // 리사이클러뷰의 아이템 총 개수 반환
        override fun getItemCount(): Int {
            return recommendList.size
        }
    }

    // 사용자가 보유한 자격증 리스트
    private fun getUserCertificates() {
        // DB 테이블 연결
        databaseReference = firebaseDatabase.getReference("users/$nickname/ownCertificate")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // userCertificateList 비워줌
                userCertificateList.clear()

                for (postSnapshot in dataSnapshot.children) {
                    userCertificateList.add(postSnapshot.value.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    // d-day 계산
    @SuppressLint("SimpleDateFormat")
    private fun getDDay(date: String): Long {
        val dateFormat = SimpleDateFormat("yyyy.MM.dd")

        val endDate = dateFormat.parse(date).time
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time.time

        return (endDate - today) / (24 * 60 * 60 * 1000)
    }

    // 메인 화면 자격증 카드 목록에 찜한 자격증 띄우기
    private fun postCertificateCards() {

        // DB 테이블 연결(사용자가 찜한 자격증 목록)
        databaseReference = firebaseDatabase.getReference("users/$nickname")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // 찜한 자격증 있는 경우 정보 띄우기
                if (dataSnapshot.hasChild("dibs")) {
                    // 첫번째 카드
                    first_box_license_text?.visibility = View.VISIBLE
                    D_date_left_first?.visibility = View.VISIBLE
                    text_Dnext_to_date_first?.visibility = View.VISIBLE
                    imv_plus_home1?.visibility = View.INVISIBLE
                    btn_test_home?.visibility = View.VISIBLE

                    // 두번째 카드
                    second_box_license_text?.visibility = View.VISIBLE
                    imv_plus_home2?.visibility = View.INVISIBLE
                    text_Dnext_to_date_second?.visibility = View.VISIBLE
                    D_date_left_second?.visibility = View.VISIBLE
                    btn_apply_home?.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}