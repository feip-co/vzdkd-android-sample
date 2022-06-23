package co.feip.vezdecode.data.datasource

import co.feip.vezdecode.data.models.Category

interface CategoriesDataSource {

    suspend fun getCategories(): List<Category>

}