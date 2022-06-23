package co.feip.vezdecode.data

import android.content.Context
import co.feip.vezdecode.data.datasource.CategoriesDataSource
import co.feip.vezdecode.data.datasource.JsonCategoriesDataSource
import co.feip.vezdecode.data.datasource.JsonProductsDataSource
import co.feip.vezdecode.data.datasource.JsonTagsDataSource
import co.feip.vezdecode.data.datasource.ProductsDataSource
import co.feip.vezdecode.data.datasource.TagsDataSource
import co.feip.vezdecode.data.models.Tag
import co.feip.vezdecode.data.models.Category
import co.feip.vezdecode.data.models.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class CatalogRepository(
    private val categoriesDataSource: CategoriesDataSource,
    private val productsDataSource: ProductsDataSource,
    private val tagsDataSource: TagsDataSource
) {

    companion object {
        private var instance: CatalogRepository? = null

        fun getInstance(context: Context): CatalogRepository {
            if (instance == null) {
                val reader = StreamReader()
                instance = CatalogRepository(
                    JsonCategoriesDataSource(context, reader),
                    JsonProductsDataSource(context, reader),
                    JsonTagsDataSource(context, reader)
                )
            }
            return requireNotNull(instance)
        }
    }

    private val _selectedFilters = MutableStateFlow<List<Int>>(emptyList())

    val selectedFilters = _selectedFilters.asStateFlow()

    suspend fun getCategories(): List<Category> =
        withContext(Dispatchers.IO) { categoriesDataSource.getCategories() }

    suspend fun getProducts(): List<Product> =
        withContext(Dispatchers.IO) { productsDataSource.getProducts() }

    suspend fun getProductDetails(productId: Int): Product =
        withContext(Dispatchers.IO) { productsDataSource.getProducts().first { it.id == productId } }

    suspend fun getTags(): List<Tag> =
        withContext(Dispatchers.IO) { tagsDataSource.getTags() }

    fun setSelectedFilters(filters: List<Int>) {
        _selectedFilters.value = filters
    }
}