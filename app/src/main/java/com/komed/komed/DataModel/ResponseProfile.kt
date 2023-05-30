package com.komed.komed.DataModel

import com.google.gson.annotations.SerializedName

data class ResponseProfile(

	@field:SerializedName("ResponseProfile")
	val responseProfile: List<ResponseProfileItem?>? = null
)

data class ResponseProfileItem(

	@field:SerializedName("albumId")
	val albumId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("thumbnailUrl")
	val thumbnailUrl: String? = null
)
