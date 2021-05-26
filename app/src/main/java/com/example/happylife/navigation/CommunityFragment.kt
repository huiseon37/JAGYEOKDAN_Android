package com.example.happylife.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.happylife.CommuRvAdapter
import com.example.happylife.R
import com.example.happylife.TalkTalk
import com.example.happylife.databinding.FragmentCommuBinding
import kotlinx.android.synthetic.main.fragment_commu.*

class CommunityFragment : Fragment() {

    //private lateinit var binding: FragmentCommuBinding
    private var talktalkList = arrayListOf<TalkTalk>(
        TalkTalk("질문","이거 답 아는사람?","나는 못풀겠던데","육회",1265110426299),
        TalkTalk("고민상담/수다","이거 답 아는사람?","나는 못풀겠던데","사시미",1265110426299),
        TalkTalk("중고장터","이거 답 아는사람?","나는 못풀겠던데","연어",1265110426299),
        TalkTalk("중고장터","이거 답 아는사람?","나는 못풀겠던데","연어",1265110426299),
        TalkTalk("중고장터","이거 답 아는사람?","나는 못풀겠던데","연어",1265110426299)
    )

    //private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    //private var databaseReference: DatabaseReference = firebaseDatabase.reference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_commu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_commu?.adapter = CommuRvAdapter(talktalkList)
        rv_commu?.layoutManager = LinearLayoutManager(context)
        rv_commu?.setHasFixedSize(true)

    }

}