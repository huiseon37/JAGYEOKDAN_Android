package com.example.happylife.navigation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.happylife.*
import com.example.happylife.R
import com.example.happylife.model.RecCertificateData
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.commu_talktalk_item.view.*
import kotlinx.android.synthetic.main.fragment_commu.*
import kotlinx.android.synthetic.main.item_recommend_certificate.view.*

class CommunityFragment : Fragment() {

    //private lateinit var binding: FragmentCommuBinding
    /*private var talktalkList = arrayListOf<TalkTalk>(
        TalkTalk("질문","이거 답 아는사람?","나는 못풀겠던데","육회",1265110426299),
        TalkTalk("고민상담/수다","이거 답 아는사람?","나는 못풀겠던데","사시미",1265110426299),
        TalkTalk("중고장터","이거 답 아는사람?","나는 못풀겠던데","연어",1265110426299),
        TalkTalk("중고장터","이거 답 아는사람?","나는 못풀겠던데","연어",1265110426299),
        TalkTalk("중고장터","이거 답 아는사람?","나는 못풀겠던데","연어",1265110426299)
    )*/

    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var databaseReference: DatabaseReference = firebaseDatabase.reference
    //private val nickname = MyApplication.prefs.getString("nickname", "")
    private val talktalkList = arrayListOf<TalkTalkDTO>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_commu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_commu?.adapter = CommuAdapter()
        rv_commu?.layoutManager = LinearLayoutManager(context)
        rv_commu?.setHasFixedSize(true)

        commu_write_btn.setOnClickListener{
            val intent = Intent(context, CreateTalktalkActivity::class.java)
            startActivity(intent)
        }

        commu_filter_btn.setOnClickListener{
            val filterSheet = CommuFilterDialog()
            filterSheet.show(this.parentFragmentManager, filterSheet.tag)
        }
    }

    inner class CommuAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        init {
                databaseReference = firebaseDatabase.getReference("TalkTalk")

                databaseReference.orderByChild("timestamp").addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        // ArrayList 비워줌
                        talktalkList.clear()

                        for (postSnapshot in dataSnapshot.children) {
                            val item = postSnapshot.getValue(TalkTalkDTO::class.java)
                            if (item != null) {
                                talktalkList.add(0,item)
                            }
                        }
                        notifyDataSetChanged()

                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.commu_talktalk_item, parent, false)
            return ViewHolder(view)
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = (holder as ViewHolder).itemView
            viewHolder.commu_title?.text = talktalkList[position].title
            viewHolder.commu_content?.text = talktalkList[position].contents
            viewHolder.commu_nickname?.text = talktalkList[position].nickname
            // recyclerview item click listener
            viewHolder.setOnClickListener {
                val intent = Intent(context, TalkTalkActivity::class.java)
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return talktalkList.size
        }
    }

}