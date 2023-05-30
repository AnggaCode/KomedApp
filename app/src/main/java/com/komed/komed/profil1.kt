package com.komed.komed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.widget.ImageButton
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.gson.Gson
import com.komed.komed.DataModel.ResponseProfile
import com.komed.komed.DataModel.ResponseProfileItem
import com.komed.komed.databinding.ActivityProfilBinding
import okhttp3.*
import java.io.IOException

class profil1 : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var sharedPreferences: SharedPreferences
    private val client = OkHttpClient()
    lateinit var binding : ActivityProfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

//         Inisialisasi objek mGoogleSignInClient dan sharedPreferences
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val account = GoogleSignIn.getLastSignedInAccount(this)
        val photoUrl = account?.photoUrl

        // Mendapatkan nama pengguna dari objek GoogleSignInAccount
        val name = account?.displayName


        val request = Request.Builder()
            .url("http://localhost:2000/profile")
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Pesan",e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val data = response.body?.string()
                val datalist = parseData(data)

                runOnUiThread{
                  datalist
                }
            }
        })



        sharedPreferences = getSharedPreferences("KOMED", Context.MODE_PRIVATE)

        // Inisialisasi ImageButton dan tambahkan listener untuk logout
        val logoutButton: ImageButton = findViewById(R.id.buttonOut)
        logoutButton.setOnClickListener {
            mGoogleSignInClient.signOut().addOnCompleteListener {
                val editor = sharedPreferences.edit()
                editor.clear()
                editor.apply()

                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }
        val backButton: ImageButton = findViewById(R.id.buttonBack)
        backButton.setOnClickListener {
            val intent = Intent(this@profil1, Dashboard::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loadImage(gambar: String?) {
        Glide.with(binding.imageAkun)
            .load(gambar)
            .into(binding.imageAkun)
    }

    private fun parseData(data: String?): ResponseProfileItem? {
        return try {
            val gson = Gson()
            gson.fromJson(data,ResponseProfileItem::class.java)
        }catch (e : java.lang.Exception){
            e.printStackTrace()
            null
        }
    }
}