package co.feip.vezdecode.data.models

import com.google.gson.annotations.SerializedName

data class Tag(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
