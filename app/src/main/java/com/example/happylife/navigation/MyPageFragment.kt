package com.example.happylife.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.happylife.R
import com.example.happylife.databinding.FragmentMypageBinding
import android.content.Intent

class MyPageFragment : Fragment() {

    private lateinit var binding: FragmentMypageBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(activity).inflate(R.layout.fragment_mypage,container,false)
        binding = FragmentMypageBinding.inflate(layoutInflater)

        //binding.mypageNickname.text = intent.getStringExtra("nickname")
        return view
    }
}