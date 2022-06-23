package co.feip.vezdecode.data.datasource

import co.feip.vezdecode.data.models.Product

interface ProductsDataSource {

    suspend fun getProducts(): List<Product>

}