package com.komed.komed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.komed.komed.databinding.ActivityTambahDestinasiBinding
import okhttp3.*
import java.io.IOException

class tambah : AppCompatActivity() {
    lateinit var binding: ActivityTambahDestinasiBinding

    companion object{
        const val PICK_IMAGE_REQUEST_CODE = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDestinasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val namaTempat = binding.inputNama.toString()
        val desc = binding.inputDesc.toString()
        val lokasi = binding.inputLokasi.toString()
        val gambar = binding.inputGambar
        val kategori = binding.kategori
        val harga = binding.harga.toString()
        val jam = binding.jam.toString()

        val client = OkHttpClient()

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("namaWisata",namaTempat)
            .addFormDataPart("namaWisata",lokasi)
            .addFormDataPart("deskripsiWisata",desc)
            .addFormDataPart("jamOperasional",jam)
            .addFormDataPart("jamOperasional",harga)
            .build()

        val request = Request.Builder()
            .url("http://localhost:2000/wisata")
            .post(requestBody)
            .build()

        client.newCall(request).execute()
    }
}
