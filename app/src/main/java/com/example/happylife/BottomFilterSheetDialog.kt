package com.example.happylife

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_filter_sheet.*

class BottomFilterSheetDialog : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.bottom_filter_sheet, container, false)
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
        click_button_sort_hangul.setOnClickListener {
            if(pressed != 0) {
                quitPressed(pressed)
            }
            click_button_sort_hangul.setBackgroundResource(R.drawable.btn_jobs_selected)
            click_button_sort_hangul.setTextColor(resources.getColor(R.color.white))
            pressed = 1
        }

        // 찜많은순
        click_button_sort_zimm.setOnClickListener {
            if(pressed != 0) {
                quitPressed(pressed)
            }
            click_button_sort_zimm.setBackgroundResource(R.drawable.btn_jobs_selected)
            click_button_sort_zimm.setTextColor(resources.getColor(R.color.white))
            pressed = 2
        }

        // 신청날짜순
        click_button_sort_applydate.setOnClickListener {
            if(pressed != 0) {
                quitPressed(pressed)
            }
            click_button_sort_applydate.setBackgroundResource(R.drawable.btn_jobs_selected)
            click_button_sort_applydate.setTextColor(resources.getColor(R.color.white))
            pressed = 3
        }

        // 시험 순
        click_button_sort_testdate.setOnClickListener {
            if(pressed != 0) {
                quitPressed(pressed)
            }
            click_button_sort_testdate.setBackgroundResource(R.drawable.btn_jobs_selected)
            click_button_sort_testdate.setTextColor(resources.getColor(R.color.white))
            pressed = 4
        }
    }

    // 선택 해제
    private fun quitPressed(num: Int) {
        when(num) {
            1 -> {
                click_button_sort_hangul.setBackgroundResource(R.drawable.license_detail_info_round_box)
                click_button_sort_hangul.setTextColor(resources.getColor(R.color.main_color))
            }
            2 -> {
                click_button_sort_zimm.setBackgroundResource(R.drawable.license_detail_info_round_box)
                click_button_sort_zimm.setTextColor(resources.getColor(R.color.main_color))
            }
            3 -> {
                click_button_sort_applydate.setBackgroundResource(R.drawable.license_detail_info_round_box)
                click_button_sort_applydate.setTextColor(resources.getColor(R.color.main_color))
            }
            4 -> {
                click_button_sort_testdate.setBackgroundResource(R.drawable.license_detail_info_round_box)
                click_button_sort_testdate.setTextColor(resources.getColor(R.color.main_color))
            }
        }
    }
}
