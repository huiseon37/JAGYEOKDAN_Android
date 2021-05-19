package com.example.happylife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.happylife.databinding.ActivityMembershipBinding

class MembershipActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMembershipBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMembershipBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnSigninMembership.setOnClickListener {
            if (binding.etId.text.toString().trim().isNotEmpty()) {
                if (binding.etPwd.text.toString().trim().isNotEmpty()) {
                    if (binding.etPwdCheck.text.toString().trim().isNotEmpty()) {
                        if (binding.etName.text.toString().trim().isNotEmpty()) {
                            if (binding.etPhone.text.toString().trim().isNotEmpty()) {

                                val intent = Intent(this, NicknameActivity::class.java)
                                intent.putExtra("id", binding.etId.text.toString())
                                intent.putExtra("pwd", binding.etPwd.text.toString())
                                intent.putExtra("name", binding.etName.text.toString())
                                intent.putExtra("phone", binding.etPhone.text.toString())
                                startActivity(intent)

                            } else {
                                Toast.makeText(
                                    applicationContext,
                                    "핸드폰 번호를 입력해주세요",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        } else {
                            Toast.makeText(applicationContext, "이름을 입력해주세요", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        Toast.makeText(applicationContext, "비밀번호를 확인해주세요", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(applicationContext, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(applicationContext, "아이디를 입력해주세요", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}