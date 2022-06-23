package co.feip.vezdecode.data

import co.feip.vezdecode.data.models.Cart
import co.feip.vezdecode.data.models.CartItem
import co.feip.vezdecode.data.models.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartRepository {

    companion object {
        val INSTANCE = CartRepository()
    }

    private val _cart = MutableStateFlow(Cart())

    val cartFlow: Flow<Cart> = _cart.asStateFlow()

    fun addItem(product: Product) {
        var quantityIncreased = false
        val cartItems = _cart.value.items.map {
            if (it.product.id == product.id) {
                quantityIncreased = true
                it.copy(quantity = it.quantity + 1)
            } else {
                it
            }
        }
        _cart.value = if (quantityIncreased) {
            cartItems
        } else {
            cartItems.plus(CartItem(product, 1))
        }.let { Cart(it) }
    }

    fun increaseQuantity(product: Product) {
        increaseQuantity(_cart.value.items.indexOfFirst { it.product.id == product.id })
    }

    fun decreaseQuantity(product: Product) {
        decreaseQuantity(_cart.value.items.indexOfFirst { it.product.id == product.id })
    }

    fun increaseQuantity(position: Int) {
        val cartItems = _cart.value.items.mapIndexed { index, cartItem ->
            if (index == position) cartItem.copy(quantity = cartItem.quantity + 1)
            else cartItem
        }
        _cart.value = Cart(cartItems)
    }

    fun decreaseQuantity(position: Int) {
        val cartItems = _cart.value.items.mapIndexed { index, cartItem ->
            if (index == position) cartItem.copy(quantity = cartItem.quantity - 1)
            else cartItem
        }.filter { it.quantity > 0 }
        _cart.value = Cart(cartItems)
    }

}