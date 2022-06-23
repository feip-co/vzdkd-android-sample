package co.feip.vezdecode.presentation.product.details.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import co.feip.vezdecode.databinding.ListItemEnergyValueBinding
import co.feip.vezdecode.databinding.ListItemProductDetailsHeaderBinding
import co.feip.vezdecode.presentation.models.ProductDetailsModel

sealed class ProductDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    class Header(itemView: View) : ProductDetailsViewHolder(itemView) {

        private val binding by lazy { ListItemProductDetailsHeaderBinding.bind(itemView) }

        fun bind(product: ProductDetailsModel.Header) {
            with(binding) {
                tvProductName.text = product.name
                tvProductDescription.text = product.description
            }
        }

    }

    class EnergyValue(itemView: View) : ProductDetailsViewHolder(itemView) {

        private val binding by lazy { ListItemEnergyValueBinding.bind(itemView) }

        fun bind(value: ProductDetailsModel.EnergyValue) {
            with(binding) {
                tvTitle.text = value.title
                tvValue.text = value.value
            }
        }

    }

}