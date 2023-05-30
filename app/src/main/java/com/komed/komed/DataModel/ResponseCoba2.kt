package com.komed.komed.DataModel

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResponseCoba2(

	@field:SerializedName("ResponseCoba2")
	val responseCoba2: List<ResponseCoba2Item>
)

data class ResponseCoba2Item(

	@field:SerializedName("albumId")
	val albumId: Int?,

	@field:SerializedName("id")
	val id: Int?,

	@field:SerializedName("title")
	val title: String?,

	@field:SerializedName("url")
	val url: String?
) : Serializable
