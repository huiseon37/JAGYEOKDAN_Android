package com.example.happylife

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.happylife.databinding.ActivityMainBinding
import com.example.happylife.navigation.CommunityFragment
import com.example.happylife.navigation.HomeViewFragment
import com.example.happylife.navigation.LicenceInfoFragment
import com.example.happylife.navigation.MyPageFragment
import kotlinx.android.synthetic.main.fragment_home.*
import nl.joery.animatedbottombar.AnimatedBottomBar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        //네비게이션 선택-이거안됨
//        dday_licensebox_third.setOnClickListener {
//            val isNavTab = MyApplication.prefs.getString("isClicked", "false")
//            println(isNavTab+"롤롤로")
//            if(isNavTab == "true"){
//                binding.bottomNavigation.selectTabAt(1)
//                MyApplication.prefs.setString("isClicked", "false")
//            }
//        }

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

        val homeViewFragment = HomeViewFragment()
        supportFragmentManager.beginTransaction().replace(R.id.main_screen_panel, homeViewFragment)
            .commit()
    }
}
