package com.example.happylife

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button

class UsePointDialog(context: Context) {
    private lateinit var listener: OKBtnClickedListener
    private lateinit var btnOk: Button
    private lateinit var btnCancel: Button

    private val dlg = Dialog(context)

    fun start() {

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE) // 타이틀바 제거
        dlg.setContentView(R.layout.dialog_use_point)
        dlg.setCancelable(false) // 다이얼로그 바깥쪽 눌렀을때 다이얼그 닫히지 않도록
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dlg.show()

        btnOk = dlg.findViewById(R.id.btn_ok_point_dialog)
        btnCancel = dlg.findViewById(R.id.btn_cancel_point_dialog)

        btnOk.setOnClickListener {
            // Todo: 포인트 차감
            listener.onOkBtnClicked("OK")
            dlg.dismiss()
        }

        btnCancel.setOnClickListener {
            dlg.dismiss()
        }
    }

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