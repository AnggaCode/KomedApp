package com.komed.komed.DataModel

import com.google.gson.annotations.SerializedName

data class ResponseCoba(

	@field:SerializedName("ResponseCoba")
	val responseCoba: List<ResponseCobaItem>
)

data class ResponseCobaItem(

	@field:SerializedName("albumId")
	val albumId: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("url")
	val url: String,
)
