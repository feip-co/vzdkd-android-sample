package co.feip.vezdecode.presentation.product.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.feip.vezdecode.R
import co.feip.vezdecode.ResourceManager
import co.feip.vezdecode.data.CatalogRepository
import co.feip.vezdecode.data.models.Product
import co.feip.vezdecode.presentation.models.ProductDetailsModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    private val catalogRepository: CatalogRepository,
    private val productDetailsMapper: ProductDetailsMapper,
    private val productId: Int
) : ViewModel() {

    private val _product = MutableSharedFlow<Product>(replay = 1)

    val product = _product.map { productDetailsMapper.map(it) }

    init {
        viewModelScope.launch { _product.emit(getProductInfo()) }
    }

    private suspend fun getProductInfo(): Product =
        catalogRepository.getProductDetails(productId)
}