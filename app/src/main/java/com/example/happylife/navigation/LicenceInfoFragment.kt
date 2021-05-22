package com.example.happylife.navigation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.happylife.AlarmActivity
import com.example.happylife.LicenseListActivity
import com.example.happylife.MainActivity
import com.example.happylife.R
import com.example.happylife.databinding.ActivityAlarmBinding
import com.example.happylife.databinding.ActivityLicenseDetailInfoBinding
import com.example.happylife.databinding.ActivityLicenseListBinding
import com.example.happylife.databinding.FragmentLicenceInfoBinding
import kotlinx.android.synthetic.main.fragment_licence_info.*

class LicenceInfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_licence_info,container,false)
        return view

        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        license_button_3.setOnClickListener {
            val intent = Intent(context, LicenseListActivity::class.java)
            startActivity(intent)
        }
    }
 }
