package co.feip.vezdecode.presentation.cart

import androidx.lifecycle.ViewModel
import co.feip.vezdecode.data.CartRepository
import co.feip.vezdecode.data.CatalogRepository
import co.feip.vezdecode.kopecksToRoublesString
import kotlinx.coroutines.flow.map

class CartViewModel(
    private val cartRepository: CartRepository,
    private val catalogRepository: CatalogRepository
) : ViewModel() {

    val cartItems = cartRepository.cartFlow.map { it.items }

    val totalPrice = cartRepository.cartFlow.map {
        it.totalPriceKopecks.kopecksToRoublesString()
    }

    val showEmptyState = cartRepository.cartFlow.map { it.items.isEmpty() }

}