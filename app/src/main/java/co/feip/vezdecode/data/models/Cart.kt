package co.feip.vezdecode.data.models

data class Cart(
    val items: List<CartItem> = emptyList()
) {

    val totalPriceKopecks: Long
        get() = items.sumOf { it.product.currentPrice * it.quantity }

}
