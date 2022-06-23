package co.feip.vezdecode.presentation.models

import co.feip.vezdecode.data.models.CartItem

sealed class CartItemModel {

    data class Entry(
        val cartItem: CartItem
    ) : CartItemModel()

    object EmptyState : CartItemModel()

}
