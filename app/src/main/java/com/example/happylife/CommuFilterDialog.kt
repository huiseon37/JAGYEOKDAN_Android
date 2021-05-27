package com.example.happylife

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_filter_sheet.*
import kotlinx.android.synthetic.main.commu_board_filter.*
import kotlinx.android.synthetic.main.license_detail_info_filter.*

class CommuFilterDialog : BottomSheetDialogFragment() {
    private var boardTagN = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.commu_board_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //필터버튼
        setFilter()

        //확인버튼
        button_apply_board_filter.setOnClickListener {
            val intent = Intent(context, CreateTalktalkActivity::class.java)
            intent.putExtra("boardTagNum",boardTagN)
            startActivity(intent)
        }

    }
    private fun setFilter() {

        // 필터 중복 선택 방지 변수
        var pressed = 0

        commu_board_filter_tag1.setOnClickListener {
            if(pressed != 0) {
                quitPressed(pressed)
            }
            commu_board_filter_tag1.setBackgroundResource(R.drawable.box_selected_light_purple)
            commu_board_filter_tag1_text.setTextColor(resources.getColor(R.color.main_color))
            pressed = 1
            boardTagN  = 1
        }

        commu_board_filter_tag2.setOnClickListener {
            if(pressed != 0) {
                quitPressed(pressed)
            }
            commu_board_filter_tag2.setBackgroundResource(R.drawable.box_selected_light_purple)
            commu_board_filter_tag2_text.setTextColor(resources.getColor(R.color.main_color))
            pressed = 2
            boardTagN  = 2
        }
        commu_board_filter_tag3.setOnClickListener {
            if(pressed != 0) {
                quitPressed(pressed)
            }
            commu_board_filter_tag3.setBackgroundResource(R.drawable.box_selected_light_purple)
            commu_board_filter_tag3_text.setTextColor(resources.getColor(R.color.main_color))
            pressed = 3
            boardTagN  = 3
        }
        commu_board_filter_tag4.setOnClickListener {
            if(pressed != 0) {
                quitPressed(pressed)
            }
            commu_board_filter_tag4.setBackgroundResource(R.drawable.box_selected_light_purple)
            commu_board_filter_tag4_text.setTextColor(resources.getColor(R.color.main_color))
            pressed = 4
            boardTagN = 4
        }
        commu_board_filter_tag5.setOnClickListener {
            if(pressed != 0) {
                quitPressed(pressed)
            }
            commu_board_filter_tag5.setBackgroundResource(R.drawable.box_selected_light_purple)
            commu_board_filter_tag5_text.setTextColor(resources.getColor(R.color.main_color))
            pressed = 5
            boardTagN = 5
        }
        commu_board_filter_tag6.setOnClickListener {
            if(pressed != 0) {
                quitPressed(pressed)
            }
            commu_board_filter_tag6.setBackgroundResource(R.drawable.box_selected_light_purple)
            commu_board_filter_tag6_text.setTextColor(resources.getColor(R.color.main_color))
            pressed = 6
            boardTagN  = 6
        }
    }

    // 선택 해제
    private fun quitPressed(num: Int) {
        when(num) {
            1 -> {
                commu_board_filter_tag1.setBackgroundResource(R.drawable.review_contents_box)
                commu_board_filter_tag1_text.setTextColor(resources.getColor(R.color.black))
            }
            2 -> {
                commu_board_filter_tag2.setBackgroundResource(R.drawable.review_contents_box)
                commu_board_filter_tag2_text.setTextColor(resources.getColor(R.color.black))
            }
            3 -> {
                commu_board_filter_tag3.setBackgroundResource(R.drawable.review_contents_box)
                commu_board_filter_tag3_text.setTextColor(resources.getColor(R.color.black))
            }
            4 -> {
                commu_board_filter_tag4.setBackgroundResource(R.drawable.review_contents_box)
                commu_board_filter_tag4_text.setTextColor(resources.getColor(R.color.black))
            }
            5 -> {
                commu_board_filter_tag5.setBackgroundResource(R.drawable.review_contents_box)
                commu_board_filter_tag5_text.setTextColor(resources.getColor(R.color.black))
            }
            6 -> {
                commu_board_filter_tag6.setBackgroundResource(R.drawable.review_contents_box)
                commu_board_filter_tag6_text.setTextColor(resources.getColor(R.color.black))
            }
        }
    }

}