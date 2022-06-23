package co.feip.vezdecode.presentation.product.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import co.feip.vezdecode.R
import co.feip.vezdecode.presentation.models.ProductDetailsModel

class ProductDetailsAdapter :
    ListAdapter<ProductDetailsModel, ProductDetailsViewHolder>(DiffCallback()) {

    companion object {
        private const val VIEW_TYPE_HEADER = 1
        private const val VIEW_TYPE_ENERGY_VALUE = 2
    }

    override fun getItemViewType(position: Int): Int =
        when (currentList[position]) {
            is ProductDetailsModel.EnergyValue -> VIEW_TYPE_ENERGY_VALUE
            is ProductDetailsModel.Header -> VIEW_TYPE_HEADER
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductDetailsViewHolder =
        when (viewType) {
            VIEW_TYPE_HEADER -> {
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_product_details_header, parent, false)
                    .let { ProductDetailsViewHolder.Header(it) }
            }
            else -> {
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_energy_value, parent, false)
                    .let { ProductDetailsViewHolder.EnergyValue(it) }
            }
        }

    override fun onBindViewHolder(holder: ProductDetailsViewHolder, position: Int) {
        when (holder) {
            is ProductDetailsViewHolder.EnergyValue -> {
                (currentList.getOrNull(position) as? ProductDetailsModel.EnergyValue)?.let {
                    holder.bind(it)
                }
            }
            is ProductDetailsViewHolder.Header -> {
                (currentList.getOrNull(position) as? ProductDetailsModel.Header)?.let {
                    holder.bind(it)
                }
            }
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<ProductDetailsModel>() {
    override fun areItemsTheSame(
        oldItem: ProductDetailsModel,
        newItem: ProductDetailsModel
    ): Boolean = oldItem is ProductDetailsModel.Header && newItem is ProductDetailsModel.Header ||
        oldItem is ProductDetailsModel.EnergyValue && newItem is ProductDetailsModel.EnergyValue &&
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: ProductDetailsModel,
        newItem: ProductDetailsModel
    ): Boolean = oldItem == newItem
}