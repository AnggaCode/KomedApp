@file:Suppress("DEPRECATION")

package com.komed.komed.Detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.komed.komed.DataModel.ResponseCoba2Item
import com.komed.komed.R

class DetailBangunanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_bangunan)

        val data = intent.getSerializableExtra("ITEM") as ResponseCoba2Item
        val nama = data.title
        val id = data.id
        val url = data.url
    }
}