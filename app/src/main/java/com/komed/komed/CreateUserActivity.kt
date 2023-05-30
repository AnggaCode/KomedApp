package com.komed.komed

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.komed.komed.databinding.ActivityCreateUserBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException

class CreateUserActivity : AppCompatActivity() {

    lateinit var binding: ActivityCreateUserBinding
    private lateinit var sharePreferences: SharedPreferences
    private lateinit var selectedImageBitmap: Bitmap
    private lateinit var selectImageFile: File

    companion object {
        private const val REQ_PICK_IMAGE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val input_username = binding.inputUsername
        binding.ivProfile.setOnClickListener {
            openImage()
        }
        binding.submit.setOnClickListener {
            val dataUsername = input_username.text.toString()
            startActivity(Intent(this@CreateUserActivity, Dashboard::class.java))
            saveBiodata(dataUsername)
        }
    }

    private fun openImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQ_PICK_IMAGE)
    }

    private fun saveBiodata(dataUsername: String) {

        //simpan data kesharedpreferences
        val save = sharePreferences.edit()
        save.putString("username", dataUsername)
        save.apply()

        //konversi gamber ke byte array
        val byteArrayOutputStream = ByteArrayOutputStream()
        selectedImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val imageByteArray = byteArrayOutputStream.toByteArray()

        //kirim data ke database
        val client = OkHttpClient()
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("username", dataUsername)
            .addFormDataPart(
                "image", selectImageFile.name,
                selectImageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
            )
            .build()

        val request = Request.Builder()
            .url("") // -> Masukkan Url Api
            .post(requestBody)
            .build()

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = client.newCall(request).execute()

                if (!response.isSuccessful) {
                    //pesan jika permintaan gagal
                    Toast.makeText(this@CreateUserActivity, "Permintaan Gagal", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    runOnUiThread {
                        //mengarahkan user ke dashboard setelah berhasil mengirim data ke database
                        startActivity(Intent(this@CreateUserActivity, Dashboard::class.java))
                        finish()
                    }
                }

            } catch (e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(
                        this@CreateUserActivity,
                        "Terjadi Kesalahan Pada Jaringan",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}