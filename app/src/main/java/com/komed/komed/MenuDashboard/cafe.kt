package com.komed.komed.MenuDashboard

import CafeRestoAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.komed.komed.DataModel.ResponseCobaItem
import com.komed.komed.databinding.ActivityCafeRestoBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException

class cafe : AppCompatActivity() {
    private lateinit var binding: ActivityCafeRestoBinding
    private lateinit var rv: RecyclerView
    private lateinit var adapter: CafeRestoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCafeRestoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rv = binding.rvCafe
        rv.layoutManager = LinearLayoutManager(this)

        GlobalScope.launch(Dispatchers.Main) {
            val response = fetchDataFromApi()
            val dataList = parseData(response)
            adapter = CafeRestoAdapter(dataList)
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

    private suspend fun parseData(response: String): List<ResponseCobaItem> {
        val dataList = mutableListOf<ResponseCobaItem>()
        return withContext(Dispatchers.Default) {
            try {
                val jsonArray = JSONArray(response)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val albumId = jsonObject.getInt("albumId")
                    val id = jsonObject.getInt("id")
                    val title = jsonObject.getString("title")
                    val url = jsonObject.getString("url")
                    val responseCobaItem = ResponseCobaItem(albumId, id, title, url)
                    dataList.add(responseCobaItem)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            dataList
        }
    }
}
