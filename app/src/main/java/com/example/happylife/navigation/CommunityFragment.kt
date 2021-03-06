package com.example.happylife.navigation

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
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.commu_talktalk_item.view.*
import kotlinx.android.synthetic.main.fragment_commu.*
import java.text.SimpleDateFormat

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
    private var talktalkList = arrayListOf<TalkTalkDTO>()
    private lateinit var keyV: String

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

        commu_write_btn.setOnClickListener {
            val filterSheet = CommuFilterDialog()
            filterSheet.show(this.parentFragmentManager, filterSheet.tag)
        }

        commu_filter_btn.setOnClickListener {
            val filterSheet = CommuFilterDialog()
            filterSheet.show(this.parentFragmentManager, filterSheet.tag)
        }
    }

    inner class CommuAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        init {
            databaseReference = firebaseDatabase.getReference("TalkTalk")

            databaseReference.orderByChild("timestamp").addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    // ArrayList 비워줌
                    talktalkList.clear()

                    for (postSnapshot in dataSnapshot.children) {
                        val item = postSnapshot.getValue(TalkTalkDTO::class.java)
                        keyV = postSnapshot.key.toString()
                        if (item != null) {
                            talktalkList.add(0, item)
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
            val time = talktalkList[position].timestamp
            viewHolder.commu_title?.text = talktalkList[position].title
            viewHolder.commu_content?.text = talktalkList[position].contents
            viewHolder.commu_nickname?.text = talktalkList[position].nickname
            viewHolder.commu_contents_time?.text = convertTimestampToDate(time)
            viewHolder.commu_tag?.text = talktalkList[position].boardTag
            viewHolder.commu_comment_cnt?.text = talktalkList[position].comments_cnt.toString()
            // recyclerview item click listener
            viewHolder.setOnClickListener {
                val intent = Intent(context, TalkTalkActivity::class.java)
                intent.putExtra("title", viewHolder.commu_title.text.toString())
                intent.putExtra("context", viewHolder.commu_content.text.toString())
                intent.putExtra("nickname", viewHolder.commu_nickname.text.toString())
                intent.putExtra("timestamp", viewHolder.commu_contents_time.text.toString())
                intent.putExtra("commutag", viewHolder.commu_tag.text.toString())
                intent.putExtra("jobtag", talktalkList[position].tag.toString())
                intent.putExtra("keyValue", keyV)
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return talktalkList.size
        }
    }

    private fun convertTimestampToDate(timestamp: Long?): String? {
        val sdf = SimpleDateFormat("MM/dd hh:mm")
        val date = sdf.format(timestamp)

        return date
    }

}