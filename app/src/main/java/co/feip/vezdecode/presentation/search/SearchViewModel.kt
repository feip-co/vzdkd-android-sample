package co.feip.vezdecode.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.feip.vezdecode.data.CartRepository
import co.feip.vezdecode.data.CatalogRepository
import co.feip.vezdecode.presentation.models.ProductModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModel(
    private val cartRepository: CartRepository,
    private val catalogRepository: CatalogRepository
) : ViewModel() {

    private val _queryFlow = MutableStateFlow("")
    private val _openDetailsEvent = Channel<Int>()

    val products = flow { emit(catalogRepository.getProducts()) }
        .flatMapLatest { products ->
            _queryFlow.map { it.trim() }.map { query ->
                if (query.isBlank()) emptyList()
                else products.filter {
                    it.name.contains(query, true) ||
                        it.description.contains(query, true)
                }
            }.flatMapLatest { filtered ->
                cartRepository.cartFlow.map { it.items }.map { cartItems ->
                    filtered.map { product ->
                        ProductModel(
                            product,
                            cartItems.firstOrNull { it.product.id == product.id }?.quantity ?: 0
                        )
                    }
                }
            }
        }
    val openDetailsEvent = _openDetailsEvent.receiveAsFlow()

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

    fun onQueryChanged(query: String) {
        _queryFlow.value = query
    }

}