package com.example.happylife

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.happylife.databinding.ActivityLicenseDetailInfoBinding
import com.example.happylife.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_license_detail_info.*
import kotlinx.android.synthetic.main.license_detail_info_filter.*

class TestRoundsFilterDialog : BottomSheetDialogFragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.license_detail_info_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        linearlayout_2020_2_writing.setOnClickListener {
            linearlayout_2020_2_writing.setBackgroundColor(resources.getColor(R.color.light_purple))
            imv_check_license_detail.visibility = View.VISIBLE
        }

        /*이거안됨
        button_apply_sorting_license.setOnClickListener {
            binding.averagePreTime.text = "26"
            binding.avergeDifficulty.text = "4.1"
        }*/
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.findViewById<Button>(R.id.button_apply_sorting_license)?.setOnClickListener {
            dismiss()
        }
    }
}