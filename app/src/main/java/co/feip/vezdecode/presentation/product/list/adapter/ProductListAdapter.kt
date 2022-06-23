package co.feip.vezdecode.presentation.product.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import co.feip.vezdecode.R
import co.feip.vezdecode.presentation.models.ProductModel

class ProductListAdapter(
    private val productClickListener: (ProductModel) -> Unit,
    private val addToCartClickListener: (ProductModel) -> Unit,
    private val increaseClickListener: (ProductModel) -> Unit,
    private val decreaseClickListener: (ProductModel) -> Unit
) : ListAdapter<ProductModel, ProductViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_product, parent, false)
        return ProductViewHolder(view).also { holder ->
            holder.itemView.setOnClickListener { _ ->
                getItemAtPosition(holder.adapterPosition)?.let {
                    productClickListener.invoke(it)
                }
            }
            holder.setCartControlCallbacks(
                { getItemAtPosition(holder.adapterPosition)?.let { addToCartClickListener.invoke(it) } },
                { getItemAtPosition(holder.adapterPosition)?.let { increaseClickListener.invoke(it) } },
                { getItemAtPosition(holder.adapterPosition)?.let { decreaseClickListener.invoke(it) } }
            )
        }
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    private fun getItemAtPosition(position: Int): ProductModel? =
        currentList.getOrNull(position)
}

private class DiffCallback : DiffUtil.ItemCallback<ProductModel>() {
    override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean =
        oldItem.product.id == newItem.product.id

    override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean =
        oldItem.quantity == newItem.quantity
}