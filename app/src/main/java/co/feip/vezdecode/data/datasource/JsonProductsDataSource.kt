package co.feip.vezdecode.data.datasource

import android.content.Context
import co.feip.vezdecode.data.StreamReader
import co.feip.vezdecode.data.models.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class JsonProductsDataSource(
    private val context: Context,
    private val streamReader: StreamReader
) : ProductsDataSource {

    private val gson = Gson()

    override suspend fun getProducts(): List<Product> {
        val json = context.resources.assets.open("Products.json").use {
            streamReader.readTextFromStream(it)
        }
        val type = object : TypeToken<List<Product>>() {}.type
        return gson.fromJson(json, type)
    }

}