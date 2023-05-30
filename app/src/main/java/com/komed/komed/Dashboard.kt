package com.komed.komed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.komed.komed.MenuDashboard.bangunan
import com.komed.komed.MenuDashboard.cafe
import com.komed.komed.MenuDashboard.taman
import com.komed.komed.databinding.ActivityDasbordBinding


class Dashboard : AppCompatActivity() {
    lateinit var binding : ActivityDasbordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDasbordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user1 = findViewById<ImageButton>(R.id.user1)
        user1.setOnClickListener {
            val intent = Intent(this, profil1::class.java)
            startActivity(intent)
        }


        binding.tambahkan.setOnClickListener(){
            val intent = Intent(this@Dashboard, tambah::class.java)
            startActivity(intent)
        }

        val bangunan1 = findViewById<ImageButton>(R.id.bangunan)
        bangunan1.setOnClickListener {
            val intent = Intent(this, bangunan::class.java)
            startActivity(intent)
        }
        val cafe1 = findViewById<ImageButton>(R.id.cafe)
        cafe1.setOnClickListener {
            val intent = Intent(this, cafe::class.java)
            startActivity(intent)
        }
        val taman1 = findViewById<ImageButton>(R.id.taman)
        taman1.setOnClickListener {
            val intent = Intent(this, taman::class.java)
            startActivity(intent)
        }


    }


}
