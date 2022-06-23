package co.feip.vezdecode.presentation.product.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.feip.vezdecode.data.CartRepository
import co.feip.vezdecode.data.CatalogRepository
import co.feip.vezdecode.data.models.Product
import co.feip.vezdecode.presentation.models.ProductModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val catalogRepository: CatalogRepository,
    private val cartRepository: CartRepository,
    private val categoryId: Int
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    private val _openDetailsEvent = Channel<Int>()

    val products = _products.flatMapLatest { products ->
        cartRepository.cartFlow.map { cart ->
            products.map { product ->
                ProductModel(
                    product,
                    cart.items.firstOrNull { it.product.id == product.id }?.quantity ?: 0
                )
            }
        }
    }
    val openDetailsEvent = _openDetailsEvent.receiveAsFlow()

    init {
        viewModelScope.launch { loadProducts() }
    }

    fun onProductClicked(product: ProductModel) {
        viewModelScope.launch { _openDetailsEvent.send(product.product.id) }
    }

    fun onAddToCartClicked(product: ProductModel) {
        cartRepository.addItem(product.product)
    }

    fun onIncreaseClicked(product: ProductModel) {
        cartRepository.increaseQuantity(product.product)
    }

    fun onDecreaseClicked(product: ProductModel) {
        cartRepository.decreaseQuantity(product.product)
    }

    private suspend fun loadProducts() {
        _products.value = catalogRepository.getProducts().filter { it.categoryId == categoryId }
    }
}