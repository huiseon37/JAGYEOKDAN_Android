package com.example.happylife

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import com.example.happylife.navigation.CommunityFragment
import com.example.happylife.navigation.DetailViewFragment
import com.example.happylife.navigation.LicenceInfoFragment
import com.example.happylife.navigation.MyPageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.setOnNavigationItemSelectedListener(this)
    }
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when(p0.itemId) {
            R.id.action_home ->{
                var mainViewFragment = DetailViewFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_screen_panel,mainViewFragment).commit()
                return true
            }
            R.id.action_license_info ->{
                var licenseInfoFragment = LicenceInfoFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_screen_panel,licenseInfoFragment).commit()
                return true
            }

            R.id.action_community ->{
                var communitiyFragment = CommunityFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_screen_panel,communitiyFragment).commit()
            return true
        }
            R.id.action_mypage ->{
                var myPageFragment = MyPageFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_screen_panel,myPageFragment).commit()
                return true
            }
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView((R.layout.activity_main))
        bottom_navigation.setOnNavigationItemSelectedListener(this)
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
    }
}