 package com.example.happylife

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.happylife.databinding.ActivityCreateTalktalkBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.commu_board_filter.*
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class CreateTalktalkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateTalktalkBinding
    var PICK_IMAGE_FROM_ALBUM = 0
    var storage: FirebaseStorage? = null
    var photoUri: Uri? = null
    var auth: FirebaseAuth? = null
    var database: FirebaseDatabase? = null

    private lateinit var tagList: ArrayList<String>
    val TalkTalkDTO = TalkTalkDTO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_talktalk)
        //binding 지정
        binding = ActivityCreateTalktalkBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //board tag 변수
        var boardTagNum = intent.getIntExtra("boardTagNum",1)
        println(boardTagNum)
        createBoardTag(boardTagNum)

        //initial auth,firebase
        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        database = FirebaseDatabase.getInstance()

        tagList = arrayListOf(
            "태그 선택",
            "경영·사무",
            "마케팅·광고·홍보",
            "IT·인터넷",
            "디자인",
            "무역·유통",
            "영업·고객상담",
            "서비스",
            "연구개발·설계",
            "생산·제조",
            "교육",
            "건설",
            "의료",
            "미디어",
            "전문·특수직"
        )

        tagsAdapter()


        //앨범 열기
        binding.btnAddPhoto.setOnClickListener {

            //앨범열기
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, PICK_IMAGE_FROM_ALBUM)
        }

        // 완료 버튼
        binding.buttonCommitMyTalk.setOnClickListener {
            contentUpload()
        }

        // 뒤로가기 버튼
        binding.returnToCommu.setOnClickListener {
            finish()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_FROM_ALBUM) {
            if (resultCode == Activity.RESULT_OK) {
                //이미지 경로 불러오기
                photoUri = data?.data
                binding.addphotoImage.setImageURI(photoUri)
                binding.imvXTalktalk.visibility = View.VISIBLE
                binding.viewImageUnderlineTalktalk.visibility = View.VISIBLE
                binding.etAddCommentTalktalk.visibility = View.VISIBLE
            } else {
                //취소하기
                finish()
            }
        }
    }

    private fun createBoardTag(boardTagNum: Int){

        when(boardTagNum){
            1 -> {
                TalkTalkDTO.boardTag = "질문"
                binding.boardTag.text = "질문 >"
            }
            2 -> {
                TalkTalkDTO.boardTag = "시험복기/후기"
                binding.boardTag.text = "시험복기/후기 >"
            }
            3 -> {
                TalkTalkDTO.boardTag = "고민상담/수다"
                binding.boardTag.text = "고민상담/수다 >"
            }
            4 -> {
                TalkTalkDTO.boardTag = "정보"
                binding.boardTag.text = "정보 >"
            }
            5 -> {
                TalkTalkDTO.boardTag = "스터디모집"
                binding.boardTag.text = "스터디모집 >"
            }
            6 -> {
                TalkTalkDTO.boardTag = "중고장터"
                binding.boardTag.text = "중고장터 >"
            }
        }

    }

    private fun contentUpload() {
        val timestamp = SimpleDateFormat("yyyyMMddmmss").format(Date())
        val imageFileName = "IMAGE_" + timestamp + "_.png"
        val storageRef = storage?.reference?.child("images/")?.child(imageFileName)
        val DatabaseRef = database?.reference?.child("TalkTalk")

        //Insert uid of user
        TalkTalkDTO.uid = auth?.currentUser?.uid

        //userID
        TalkTalkDTO.userID = auth?.currentUser?.email

        //talkTitle
        TalkTalkDTO.title = binding.talktalkTitle.text.toString()

        //talkcontent
        TalkTalkDTO.contents = binding.talktalkContent.text.toString()

        //talk timstamp
        TalkTalkDTO.timestamp = System.currentTimeMillis()

        TalkTalkDTO.nickname = MyApplication.prefs.getString("nickname", "")

        if (storageRef != null) {
            photoUri?.let {
                storageRef.putFile(it).addOnSuccessListener {
                    storageRef.downloadUrl.addOnSuccessListener { uri ->
                        //Insert downloadUri of image
                        TalkTalkDTO.ImageUri = uri.toString()
                    }
                }
            }
        }

        DatabaseRef?.push()?.setValue(TalkTalkDTO)

        setResult(Activity.RESULT_OK)

        finish()
    }

    private fun tagsAdapter() {
        val tagsAdapter = ArrayAdapter(this, R.layout.item_tags_spinner, tagList)

        binding.constraintlayoutLabelsSpinner.visibility = View.VISIBLE
        binding.spinnerLabels.adapter = tagsAdapter
        binding.spinnerLabels.setSelection(0)

        binding.spinnerLabels.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                binding.spinnerLabels.setSelection(position)
                TalkTalkDTO.tag = position.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }
}