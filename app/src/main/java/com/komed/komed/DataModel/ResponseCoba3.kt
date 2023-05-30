package com.komed.komed.DataModel

import com.google.gson.annotations.SerializedName

data class ResponseCoba3(

	@field:SerializedName("ResponseCoba3")
	val responseCoba3: List<ResponseCoba3Item>
)

data class ResponseCoba3Item(

	@field:SerializedName("albumId")
	val albumId: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("url")
	val url: String,

)
