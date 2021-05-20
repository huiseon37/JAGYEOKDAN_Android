package com.example.happylife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.happylife.databinding.ActivityMainBinding
import com.example.happylife.navigation.CommunityFragment
import com.example.happylife.navigation.HomeViewFragment
import com.example.happylife.navigation.LicenceInfoFragment
import com.example.happylife.navigation.MyPageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.action_home -> {
                val homeViewFragment = HomeViewFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_screen_panel, homeViewFragment).commit()
                return true
            }
            R.id.action_license_info -> {
                val licenseInfoFragment = LicenceInfoFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_screen_panel, licenseInfoFragment).commit()
                return true
            }

            R.id.action_community -> {
                val communitiyFragment = CommunityFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_screen_panel, communitiyFragment).commit()
                return true
            }
            R.id.action_mypage -> {
                val myPageFragment = MyPageFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_screen_panel, myPageFragment).commit()
                return true
            }
        }
        return false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.watchAlarmButton.setOnClickListener {
            //val intent = Intent(this, AlarmActivity::class.java)
            //startActivity(intent)
        }

        val homeViewFragment = HomeViewFragment()
        supportFragmentManager.beginTransaction().replace(R.id.main_screen_panel, homeViewFragment)
            .commit()
        bottom_navigation.setOnNavigationItemSelectedListener(this)
        bottom_navigation.menu.getItem(0).isChecked = true
    }
}
