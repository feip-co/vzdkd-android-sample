package co.feip.vezdecode.data.datasource

import android.content.Context
import co.feip.vezdecode.data.StreamReader
import co.feip.vezdecode.data.models.Product
import co.feip.vezdecode.data.models.Tag
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class JsonTagsDataSource(
    private val context: Context,
    private val streamReader: StreamReader
) : TagsDataSource {

    private val gson = Gson()

    override suspend fun getTags(): List<Tag> {
        val json = context.resources.assets.open("Tags.json").use {
            streamReader.readTextFromStream(it)
        }
        val type = object : TypeToken<List<Product>>() {}.type
        return gson.fromJson(json, type)
    }

}