package com.example.happylife.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.happylife.MyApplication
import com.example.happylife.R
import com.example.happylife.model.CertificateInfoData
import com.example.happylife.model.RecCertificateData
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.recommend_certificate_item.view.*
import java.nio.file.attribute.PosixFileAttributeView

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
        return LayoutInflater.from(activity).inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_recommend_certificate?.adapter = RecCertificateAdapter()
        rv_recommend_certificate?.layoutManager = LinearLayoutManager(context)
        rv_recommend_certificate?.setHasFixedSize(true) // recyclerview 크기 고정
    }

    inner class RecCertificateAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        init {
            // 사용자의 관심 분야 목록 받아오기
            getInterestJobs()

            // 사용자가 보유한 자격증 목록 받아오기
            getUserCertificates()

        }

        // xml파일을 inflate하여 ViewHolder를 생성
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recommend_certificate_item, parent, false)
            return ViewHolder(view)
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        }

        // onCreateViewHolder에서 만든 view와 실제 데이터를 연결
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = (holder as ViewHolder).itemView

            viewHolder.tv_certificate_rec_item?.text = recommendList[position].name
            viewHolder.tv_d_day_item?.text = recommendList[position].d_day

            // recyclerview item click listener
            viewHolder.setOnClickListener {
            }
        }

        // 리사이클러뷰의 아이템 총 개수 반환
        override fun getItemCount(): Int {
            return recommendList.size
        }

    }

    // 사용자의 관심 분야 리스트
    private fun getInterestJobs() {
        println("getInterestJobs 실행됨")
        // DB 테이블 연결
        databaseReference = firebaseDatabase.getReference("users/$nickname/jobs")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // userInterests 비워줌
                userInterests.clear()

                for (postSnapshot in dataSnapshot.children) {
                    userInterests.add(postSnapshot.value.toString())
                    println("관심분야 = " + postSnapshot.value.toString())
                    // 관심 분야에 해당하는 자격증 리스트 받아오기
                    getRecommendList(postSnapshot.value.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
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

    // 사용자 관심 분야에 해당하는 자격증 리스트
    private fun getRecommendList(category: String) {
        println("getRecommendList 실행됨")
        // DB 테이블 연결
        databaseReference = firebaseDatabase.getReference("certificate/$category")

        // 신청 기간 임박 순으로 정렬
        databaseReference.orderByChild("examStartRegDate")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // 자격증 이름 & 신청 날짜 받아서 리스트에 추가
                    for (postSnapshot in dataSnapshot.children) {

//                        println("자격증 이름 = " + postSnapshot.key.toString())
                        println("postSnapshot = $postSnapshot")
                        println("회차 이름 = " + postSnapshot.value.toString())

                        val item = postSnapshot.getValue(CertificateInfoData::class.java)
                        println("item = " + item.toString())
                        if (item != null) {
                            println("디데이 = " + postSnapshot.child("examStartRegDate").value.toString())
                        }
                        if (item != null) {
                            recommendList.add(
                                RecCertificateData(
                                    postSnapshot.key.toString(),
                                    item.examStartRegDate
                                )
                            )
                            println("자격증 이름 : " + postSnapshot.key.toString() + ", 디데이: " + item.examStartRegDate)
                        }
                        return
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }
}