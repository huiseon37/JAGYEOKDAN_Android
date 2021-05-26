package com.example.happylife

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button

class ReviewDialog(context: Context) {
    private lateinit var listener: OKBtnClickedListener
    private lateinit var btnOk: Button
    private lateinit var btnNext: Button

    private val dlg = Dialog(context)

    fun start() {

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE) // 타이틀바 제거
        dlg.setContentView(R.layout.dialog_review)
        dlg.setCancelable(false) // 다이얼로그 바깥쪽 눌렀을때 다이얼로그 닫히지 않도록
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dlg.show()

        btnOk = dlg.findViewById(R.id.button_ok_review_dialog)
        btnNext = dlg.findViewById(R.id.button_cancel_review_dialog)

        btnOk.setOnClickListener {
            listener.onOkBtnClicked("OK")
            dlg.dismiss()
        }

        btnNext.setOnClickListener {
            dlg.dismiss()
        }
    }

    // 작성하기 버튼 클릭
    fun setOnOKClickedListener(listener: (String) -> Unit) {
        this.listener = object : OKBtnClickedListener {
            override fun onOkBtnClicked(content: String) {
                listener(content)
            }
        }
    }

    interface OKBtnClickedListener {
        fun onOkBtnClicked(content: String)
    }
}