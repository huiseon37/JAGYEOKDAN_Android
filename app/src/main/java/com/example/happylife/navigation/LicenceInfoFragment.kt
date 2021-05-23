package com.example.happylife.navigation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.happylife.LicenseListActivity
import com.example.happylife.R
import kotlinx.android.synthetic.main.fragment_licence_info.*

class LicenceInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(activity)
            .inflate(R.layout.fragment_licence_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // IT·인터넷
        license_button_3.setOnClickListener {
            val intent = Intent(context, LicenseListActivity::class.java)
            intent.putExtra("name", "IT·인터넷")
            startActivity(intent)
        }
    }
}
