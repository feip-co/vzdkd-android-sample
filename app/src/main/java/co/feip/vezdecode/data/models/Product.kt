package co.feip.vezdecode.data.models

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("price_current")
    val currentPrice: Long,
    @SerializedName("price_old")
    val priceOld: Long?,
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("measure")
    val measure: Long,
    @SerializedName("measure_unit")
    val measureUnit: String,
    @SerializedName("energy_per_100_grams")
    val energyPer100Grams: Double,
    @SerializedName("proteins_per_100_grams")
    val proteinsPer100Grams: Double,
    @SerializedName("fats_per_100_grams")
    val fatsPer100Grams: Double,
    @SerializedName("carbohydrates_per_100_grams")
    val carbohydratesPer100Grams: Double,
    @SerializedName("tag_ids")
    val tagIds: List<Int>
)
