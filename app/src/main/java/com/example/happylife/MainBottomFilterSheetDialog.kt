package com.example.happylife

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_filter_sheet.*
import kotlinx.android.synthetic.main.main_bottom_commu_filter_sheet.*

class MainBottomFilterSheetDialog : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.main_bottom_commu_filter_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFilter()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.findViewById<Button>(R.id.button_apply_sorting)?.setOnClickListener {
            dismiss()
        }
    }

    // 필터 정렬
    private fun setFilter() {

        // 필터 중복 선택 방지 변수
        var pressed = 0

        // 가나다순
        click_button_sort_watch.setOnClickListener {
            if(pressed != 0) {
                quitPressed(pressed)
            }
            click_button_sort_watch.setBackgroundResource(R.drawable.btn_jobs_selected)
            click_button_sort_watch.setTextColor(resources.getColor(R.color.white))
            pressed = 1
        }

        // 찜많은순
        click_button_sort_comment.setOnClickListener {
            if(pressed != 0) {
                quitPressed(pressed)
            }
            click_button_sort_comment.setBackgroundResource(R.drawable.btn_jobs_selected)
            click_button_sort_comment.setTextColor(resources.getColor(R.color.white))
            pressed = 2
        }

        // 신청날짜순
        click_button_sort_emotion.setOnClickListener {
            if(pressed != 0) {
                quitPressed(pressed)
            }
            click_button_sort_emotion.setBackgroundResource(R.drawable.btn_jobs_selected)
            click_button_sort_emotion.setTextColor(resources.getColor(R.color.white))
            pressed = 3
        }

    }

    // 선택 해제
    private fun quitPressed(num: Int) {
        when(num) {
            1 -> {
                click_button_sort_watch.setBackgroundResource(R.drawable.license_detail_info_round_box)
                click_button_sort_watch.setTextColor(resources.getColor(R.color.main_color))
            }
            2 -> {
                click_button_sort_comment.setBackgroundResource(R.drawable.license_detail_info_round_box)
                click_button_sort_comment.setTextColor(resources.getColor(R.color.main_color))
            }
            3 -> {
                click_button_sort_emotion.setBackgroundResource(R.drawable.license_detail_info_round_box)
                click_button_sort_emotion.setTextColor(resources.getColor(R.color.main_color))
            }
        }
    }
}
