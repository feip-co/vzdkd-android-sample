package co.feip.vezdecode.data.datasource

import android.content.Context
import co.feip.vezdecode.data.StreamReader
import co.feip.vezdecode.data.models.Category
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class JsonCategoriesDataSource(
    private val context: Context,
    private val streamReader: StreamReader
) : CategoriesDataSource {

    private val gson = Gson()

    override suspend fun getCategories(): List<Category> {
        val json = context.resources.assets.open("Categories.json").use {
            streamReader.readTextFromStream(it)
        }
        val type = object : TypeToken<List<Category>>() {}.type
        return gson.fromJson(json, type)
    }
}