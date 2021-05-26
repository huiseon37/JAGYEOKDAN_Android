package com.example.happylife

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.happylife.databinding.ActivityWriteReviewBinding
import com.example.happylife.model.ReviewData
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.util.*

class WriteReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWriteReviewBinding

    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var databaseReference: DatabaseReference = firebaseDatabase.reference

    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteReviewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        var difficulty = 0
        val certificateName = intent.getStringExtra("name")

        binding.licenseTestName.text = certificateName.toString()

        // X 버튼 클릭
        binding.imbXWriteReview.setOnClickListener {
            finish()
        }

        binding.star1.setOnClickListener {
            binding.star1.setBackgroundResource(R.drawable.star_purple)
            difficulty++
        }

        binding.star2.setOnClickListener {
            binding.star2.setBackgroundResource(R.drawable.star_purple)
            difficulty++
        }

        binding.star3.setOnClickListener {
            binding.star3.setBackgroundResource(R.drawable.star_purple)
            difficulty++
        }

        binding.star4.setOnClickListener {
            binding.star4.setBackgroundResource(R.drawable.star_purple)
            difficulty++
        }

        binding.star5.setOnClickListener {
            binding.star5.setBackgroundResource(R.drawable.star_purple)
            difficulty++
        }

        // DB 테이블 연결
        databaseReference = firebaseDatabase.getReference("review/$certificateName")

        // 수험표 업로드 클릭
        binding.addmissionTicketUpload.setOnClickListener {
            // 앨범 접근 허용 여부 묻기
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )

            // 앨범 오픈
            launchGallery()
        }

        // 완료 버튼 클릭
        // Todo: 포인트 지급
        binding.btnFinishWriteReview.setOnClickListener {
            val reviewInfo = ReviewData(
                difficulty,
                binding.userInputAboutQuestion2.text.toString(),
                binding.userInputAboutQuestion3.text.toString(),
                binding.etMoodWriteReview.text.toString(),
                binding.etTipWriteReview.text.toString(),
                binding.etCompanyWriteReview.text.toString()
            )

            databaseReference.setValue(reviewInfo)

            // 수험표 사진 Firebase 업로드
            uploadImage()

            // 메인 화면으로 이동
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // 시험장 위치 선택
        binding.btnSearchLocation.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivityForResult(intent, 100)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                100 -> {
                    binding.userInputAboutQuestion3.text =
                        data?.getStringExtra("address").toString()
                }
            }
        }

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }

            filePath = data.data

            binding.userInputAboutQuestion7.text = "업로드가 완료되었습니다."
        }
    }

    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "사진을 선택해주세요"), PICK_IMAGE_REQUEST)
    }

    private fun uploadImage() {
        if (filePath != null) {
            val ref = storageReference?.child("uploads/" + UUID.randomUUID().toString())
            val uploadTask = ref?.putFile(filePath!!)

            uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation ref.downloadUrl
            })?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    addUploadRecordToDb(downloadUri.toString())
                }
            }?.addOnFailureListener {

            }
        } else {
            Toast.makeText(this, "사진을 업로드해주세요", Toast.LENGTH_SHORT).show()
        }
    }

    // Firestore에 이미지 업로드
    private fun addUploadRecordToDb(uri: String) {
        val db = FirebaseFirestore.getInstance()

        val data = HashMap<String, Any>()
        data["imageUrl"] = uri

        db.collection("posts")
            .add(data)
            .addOnSuccessListener {
                Toast.makeText(this, "사진 선택이 완료되었습니다.", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { e ->
            }
    }
}