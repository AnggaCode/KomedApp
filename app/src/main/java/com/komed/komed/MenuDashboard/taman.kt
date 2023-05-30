package com.komed.komed.MenuDashboard

import CafeRestoAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.komed.komed.Adapter.TamanAdapter
import com.komed.komed.DataModel.ResponseCoba3Item
import com.komed.komed.DataModel.ResponseCobaItem
import com.komed.komed.databinding.ActivityCafeRestoBinding
import com.komed.komed.databinding.ActivityTamanHiburanBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException

class taman : AppCompatActivity() {
    private lateinit var binding: ActivityTamanHiburanBinding
    private lateinit var rv: RecyclerView
    private lateinit var adapter: TamanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTamanHiburanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rv = binding.rvTaman
        rv.layoutManager = LinearLayoutManager(this)

        GlobalScope.launch(Dispatchers.Main) {
            val response = fetchDataFromApi()
            val dataList = parseData(response)
            adapter = TamanAdapter(dataList)
            rv.adapter = adapter
        }
    }

    private suspend fun fetchDataFromApi(): String {
        return withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://jsonplaceholder.typicode.com/photos")
                .build()
             val response = client.newCall(request).execute()
            response.body?.string() ?: ""
        }
    }

    private suspend fun parseData(response: String): List<ResponseCoba3Item> {
        val dataList = mutableListOf<ResponseCoba3Item>()
        return withContext(Dispatchers.Default) {
            try {
                val jsonArray = JSONArray(response)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val albumId = jsonObject.getInt("albumId")
                    val id = jsonObject.getInt("id")
                    val title = jsonObject.getString("title")
                    val url = jsonObject.getString("url")
                    val responseCobaItem = ResponseCoba3Item(albumId, id, title, url)
                    dataList.add(responseCobaItem)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            dataList
        }
    }
}
