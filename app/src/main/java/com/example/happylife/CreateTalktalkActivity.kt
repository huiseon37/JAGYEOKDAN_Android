package com.example.happylife

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.happylife.databinding.ActivityCreateTalktalkBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class CreateTalktalkActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateTalktalkBinding
    var PICK_IMAGE_FROM_ALBUM = 0
    var storage : FirebaseStorage? = null
    var photoUri : Uri? = null
    var auth : FirebaseAuth? = null
    var database : FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_talktalk)
        //binding 지정
        binding = ActivityCreateTalktalkBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //initial auth,firebase
        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        database = FirebaseDatabase.getInstance()


        //앨범 열기
        binding.btnAddPhoto.setOnClickListener {

            //앨범열기
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent,PICK_IMAGE_FROM_ALBUM)
        }

        //이미지 업로드
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
        if(requestCode == PICK_IMAGE_FROM_ALBUM){
            if(resultCode == Activity.RESULT_OK) {
                //이미지 경로 불러오기
                photoUri = data?.data
                binding.addphotoImage.setImageURI(photoUri)
            }else{
                //취소하기
                finish()
            }
        }
    }
    private fun contentUpload() {
        val timestamp = SimpleDateFormat("yyyyMMddmmss").format(Date())
        val imageFileName = "IMAGE_" + timestamp + "_.png"
        val storageRef = storage?.reference?.child("images/")?.child(imageFileName)
        val DatabaseRef = database?.reference?.child("TalkTalk")

        val TalkTalkDTO = TalkTalkDTO()

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

        TalkTalkDTO.tag = "1"

        storageRef?.putFile(photoUri!!)?.addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                //Insert downloadUri of image
                TalkTalkDTO.ImageUri = uri.toString()
            }
        }

        DatabaseRef?.push()?.setValue(TalkTalkDTO)

        setResult(Activity.RESULT_OK)

        finish()
    }
}