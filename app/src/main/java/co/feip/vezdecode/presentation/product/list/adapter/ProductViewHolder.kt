package co.feip.vezdecode.presentation.product.list.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import co.feip.vezdecode.databinding.ListItemProductBinding
import co.feip.vezdecode.presentation.models.ProductModel

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ListItemProductBinding.bind(itemView)

    fun bind(product: ProductModel) {
        with(binding) {
            tvProductName.text = product.product.name
            tvProductWeight.text = product.formattedMeasure
            cartControl.setText(product.getFormattedPrice(itemView.context))
            cartControl.setQuantity(product.quantity)
        }
    }

    fun updateQuantity(quantity: Int) {
        binding.cartControl.setQuantity(quantity)
    }

    fun setCartControlCallbacks(
        addToCartClickListener: () -> Unit,
        increaseClickListener: () -> Unit,
        decreaseClickListener: () -> Unit
    ) {
        binding.cartControl.setupListeners(
            addToCartClickListener,
            increaseClickListener,
            decreaseClickListener
        )
    }
}

