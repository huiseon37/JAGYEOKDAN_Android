package com.example.happylife

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.happylife.model.CertificateRegData

class CertificateRegAdapter(
    private val context: Context,
    private val deadlineList: ArrayList<CertificateRegData>
) :
    RecyclerView.Adapter<CertificateRegAdapter.Holder>() {

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        private val name = itemView?.findViewById<TextView>(R.id.tv_certificate_reg_item)

        fun bind(list: CertificateRegData) {
            // TextView와 String 데이터 연결
            name?.text = list.name
        }
    }

    // 화면을 최초 로딩하여 만들어진 View가 없는 경우, xml파일을 inflate하여 ViewHolder를 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_certificate_register, parent, false)
        return Holder(view)
    }

    // RecyclerView로 만들어지는 item의 총 개수를 반환
    override fun getItemCount(): Int {
        return deadlineList.size
    }

    // 위의 onCreateViewHolder에서 만든 view와 실제 입력되는 각각의 데이터를 연결
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(deadlineList[position])
    }


}