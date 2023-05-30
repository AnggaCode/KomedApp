package com.komed.komed.MenuDashboard


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.komed.komed.Adapter.BangunanAdapter
import com.komed.komed.DataModel.ResponseCoba2Item
import com.komed.komed.Detail.DetailBangunanActivity
import com.komed.komed.databinding.ActivityBangunanBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONException


class bangunan : AppCompatActivity() {
    private lateinit var binding: ActivityBangunanBinding
    private lateinit var rv_bangunan: RecyclerView
    private lateinit var adapter: BangunanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBangunanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rv_bangunan = binding.rvBangunan
        rv_bangunan.layoutManager = LinearLayoutManager(this)

        GlobalScope.launch(Dispatchers.Main) {
            val response = fetchDataFromApi()
            val dataList = parseData(response)
            adapter = BangunanAdapter(dataList)
            adapter.setOnItemClicListener(object : BangunanAdapter.onItemClickedListener {
                override fun onItemClickListener(position: Int) {
                    val item =dataList[position]
                    val intent = Intent(this@bangunan,DetailBangunanActivity::class.java)
                    intent.putExtra("selected item",item)
                    startActivity(intent)
                }
            })
            rv_bangunan.adapter = adapter
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

    private suspend fun parseData(response: String): List<ResponseCoba2Item> {
        val dataList = mutableListOf<ResponseCoba2Item>()
        return withContext(Dispatchers.Default) {
            try {
                val jsonArray = JSONArray(response)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val albumId = jsonObject.getInt("albumId")
                    val id = jsonObject.getInt("id")
                    val title = jsonObject.getString("title")
                    val url = jsonObject.getString("url")
                    val responseCoba2Item = ResponseCoba2Item(albumId, id, title, url)
                    dataList.add(responseCoba2Item)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            dataList
        }
    }
}

