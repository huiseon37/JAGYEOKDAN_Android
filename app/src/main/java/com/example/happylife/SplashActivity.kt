package com.example.happylife

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // status bar 색상 변경
        val window = this.window
        window.statusBarColor = ContextCompat.getColor(this, R.color.main_color)
        // statue bar 아이콘 색상 변경
        window.decorView.systemUiVisibility = 0

        val anim_we = AnimationUtils.loadAnimation(this, R.anim.alpha_we)
        val anim_go = AnimationUtils.loadAnimation(this, R.anim.alpha_got)
        val anim_certificate = AnimationUtils.loadAnimation(this, R.anim.alpha_certificate)
        var anim_main = AnimationUtils.loadAnimation(this, R.anim.scale)
        splash_we.startAnimation(anim_we)
        splash_got.startAnimation(anim_go)
        splash_certificate.startAnimation(anim_certificate)
        //splash_main.startAnimation(anim_main)
        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        },2000)
    }
}