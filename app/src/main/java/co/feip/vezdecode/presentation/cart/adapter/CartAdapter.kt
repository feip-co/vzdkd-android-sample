package co.feip.vezdecode.presentation.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import co.feip.vezdecode.R
import co.feip.vezdecode.data.models.CartItem

class CartAdapter : ListAdapter<CartItem, CartItemViewHolder>(ItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_cart, parent, false)
        return CartItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}

private class ItemCallback : DiffUtil.ItemCallback<CartItem>() {
    override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean =
        oldItem.product.id == newItem.product.id

    override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean =
        oldItem == newItem
}