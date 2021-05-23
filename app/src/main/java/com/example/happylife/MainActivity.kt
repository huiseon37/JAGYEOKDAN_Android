package com.example.happylife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.happylife.databinding.ActivityMainBinding
import com.example.happylife.navigation.CommunityFragment
import com.example.happylife.navigation.HomeViewFragment
import com.example.happylife.navigation.LicenceInfoFragment
import com.example.happylife.navigation.MyPageFragment
import nl.joery.animatedbottombar.AnimatedBottomBar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Bottom Navigation
        binding.bottomNavigation.setOnTabSelectListener(object :
            AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                when (newIndex) {
                    0 -> {
                        val homeViewFragment = HomeViewFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_screen_panel, homeViewFragment).commit()
                    }
                    1 -> {
                        val licenseInfoFragment = LicenceInfoFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_screen_panel, licenseInfoFragment).commit()
                    }

                    2 -> {
                        val communitiyFragment = CommunityFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_screen_panel, communitiyFragment).commit()
                    }
                    3 -> {
                        val bundle = Bundle()
                        val myPageFragment = MyPageFragment()
                        // 마이페이지로 닉네임 전달
                        bundle.putString("nickname", intent.getStringExtra("nickname"))
                        myPageFragment.arguments = bundle
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_screen_panel, myPageFragment).commit()
                    }
                }
            }
        })

//        val dlg = ReviewDialog(this)
//
//        dlg.setOnOKClickedListener {
//        }
//        dlg.start(this)

        // 알람 아이콘 클릭
        binding.watchAlarmButton.setOnClickListener {
            val intent = Intent(this, AlarmActivity::class.java)
            startActivity(intent)
        }

        val homeViewFragment = HomeViewFragment()
        supportFragmentManager.beginTransaction().replace(R.id.main_screen_panel, homeViewFragment)
            .commit()
//        bottom_navigation.setOnNavigationItemSelectedListener(this)
//        bottom_navigation.menu.getItem(0).isChecked = true
    }
}
