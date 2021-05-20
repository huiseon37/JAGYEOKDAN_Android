package com.example.happylife.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.happylife.R
import com.example.happylife.databinding.FragmentMypageBinding

class MyPageFragment : Fragment() {

    private lateinit var binding: FragmentMypageBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(activity).inflate(R.layout.fragment_mypage, container, false)
        binding = FragmentMypageBinding.inflate(layoutInflater)

        val nickname = arguments?.getString("nickname").toString()
//        tv_nickname_mypage.text = nickname
        return view
    }
}