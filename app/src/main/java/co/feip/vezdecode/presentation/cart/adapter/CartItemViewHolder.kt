package co.feip.vezdecode.presentation.cart.adapter

import android.graphics.Paint
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import co.feip.vezdecode.data.models.CartItem
import co.feip.vezdecode.databinding.ListItemCartBinding
import co.feip.vezdecode.kopecksToRoublesString
import co.feip.vezdecode.presentation.models.CartItemModel

class CartItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding by lazy { ListItemCartBinding.bind(itemView) }

    fun bind(cartItem: CartItem) {
        with(binding) {
            tvProductName.text = cartItem.product.name
            tvCurrentPrice.text = cartItem.product.currentPrice.kopecksToRoublesString()
            tvOldPrice.text = cartItem.product.priceOld?.kopecksToRoublesString()
            tvOldPrice.isVisible = cartItem.product.priceOld != null
            tvOldPrice.paintFlags = tvOldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            quantityControl.setQuantity(cartItem.quantity)
        }
    }

}
