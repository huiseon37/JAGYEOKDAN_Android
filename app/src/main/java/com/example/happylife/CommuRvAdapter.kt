package com.example.happylife

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CommuRvAdapter(
    private val talktalkList: ArrayList<TalkTalk>
) :
    RecyclerView.Adapter<CommuRvAdapter.Holder>() {
    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val commuTag = itemView?.findViewById<TextView>(R.id.commu_tag)
        val commuTime = itemView?.findViewById<TextView>(R.id.commu_contents_time)
        val commuTitle = itemView?.findViewById<TextView>(R.id.commu_title)
        val commuContent = itemView?.findViewById<TextView>(R.id.commu_content)
        //val commuCommentCnt = itemView?.findViewById<TextView>(R.id.commu_comment_cnt)
        val commuNickname = itemView?.findViewById<TextView>(R.id.commu_nickname)

        fun bind (talk:TalkTalk ) {
            commuTag?.text = talk.tag
            commuTime?.text = talk.timestamp.toString()
            commuTitle?.text = talk.title
            commuContent?.text = talk.contents
            commuNickname?.text = talk.uid
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.commu_talktalk_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(talktalkList[position])
    }

    override fun getItemCount(): Int {
        return talktalkList.size
    }
}